package org.scalarules.engine

import org.scalarules.engine.DerivationTools._

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object FactEngine {

  private var graphCache = new LRUCache[List[Derivation], Levels]()

  // TODO : Move these functions to DerivationGraph
  /**
    * Constructs a Dependency graph for the provided list of Derivations. Each Derivation will yield a Node describing its output and other Nodes requiring its
    * output.
    *
    * @param derivations a List of all possible Derivations
    * @return a Set of Nodes which reference their dependent Nodes
    */
  def constructGraph(derivations: List[Derivation]): Set[Node] = {
    constructNodes(derivations, computeAllInputs(derivations).map( (_, List()) ).toMap + (OriginFact -> List()), staleOutputsForDerivations(derivations).toList)
  }

  private def resolveChildNodes(output: Fact[Any], nodesByInput: Map[Fact[Any], List[Node]]): List[Node] = if (nodesByInput contains output) nodesByInput(output) else List()
  private def constructNode(derivation: Derivation, nodesByInput: Map[Fact[Any], List[Node]]): Node = Node(derivation, resolveChildNodes(derivation.output, nodesByInput))
  private def staleOutputsForDerivations(derivations: List[Derivation]): Set[Fact[Any]] = computeAllOutputs(derivations) -- computeAllInputs(derivations)

  @tailrec
  private def constructNodes(remainingDerivations: List[Derivation], finishedNodesByInput: Map[Fact[Any], List[Node]], readyFacts: List[Fact[Any]]): Set[Node] = {
    readyFacts match {
      case rf :: rfs => {
        val currentDerivation = remainingDerivations find ( _.output eq rf )
        if (currentDerivation.isEmpty) throw new IllegalStateException("Attempting to process a Derivation which is no longer in the remainingDerivations list. This is weird :)")

        val newRemainingDerivations = remainingDerivations filterNot ( _.output eq rf )
        val newNode: Node = constructNode(currentDerivation.get, finishedNodesByInput)
        val newFinishedNodesByInput = finishedNodesByInput map {
            case (OriginFact, nodes) if currentDerivation.get.input.isEmpty => (OriginFact, newNode :: nodes)
            case (fact, nodes) => (fact, if (currentDerivation.get.input contains fact) newNode :: nodes else nodes)
        }
        val newReadyFacts = staleOutputsForDerivations(newRemainingDerivations).toList

        constructNodes( newRemainingDerivations, newFinishedNodesByInput, newReadyFacts )
      }
      case Nil => if (remainingDerivations.isEmpty) {
        finishedNodesByInput.values.foldLeft(Set[Node]())( (acc: Set[Node], v: List[Node]) => acc ++ v )
      } else {
        throw new IllegalStateException("There are no stale outputs, but there are remaining derivations. This means there is a cycle in these derivations: " + remainingDerivations)
      }
    }
  }

  /**
    * Determines the order in which Facts should be calculated, based on their inputs. It takes a Derivation-graph as constructed by FactEngine.constructGraph
    * and orders them in levels. Each level contains Derivation-nodes for which the inputs will have been determined in an earlier level. Following these
    * levels while running a derivation cycle will guarantee correct causality between individual Derivations.
    *
    * @param originalNodes the Set of Nodes describing the dependency graph between Derivations
    * @return a List of levels that produce facts required for subsequent levels. Each level is a list of Nodes that can be evaluated once all previous levels
    *         have been evaluated
    */
  def levelSorter(originalNodes: Set[Node]): Levels = {
    // TODO : Add detection for an empty level 0, which means we cannot calculate anything without input from other derivations

    @tailrec
    def sorter(levelsAcc: Levels,
               currentLevel: Level,
               higherLevelNodes: List[Node],
               completedOutputs: Set[Node],
               remainingNodes: List[Node]): Levels = {
      (remainingNodes, higherLevelNodes) match {
        case ((n @ Node(_, children)) :: ns, _) if (children.toSet -- completedOutputs).isEmpty =>
          sorter(levelsAcc, n :: currentLevel, higherLevelNodes, completedOutputs, ns)
        case (n :: ns, _)  =>
          sorter(levelsAcc, currentLevel, n :: higherLevelNodes, completedOutputs, ns)
        case (Nil, remaining @ n :: ns) =>
          sorter(currentLevel :: levelsAcc, List(), List(), completedOutputs ++ currentLevel, remaining)
        case _ => currentLevel :: levelsAcc
      }
    }

    sorter(List(), List(), List(), Set(), originalNodes.toList)
  }
  // End TODO move to DerivationGraph

  /**
    * Takes a List of Derivations an abstract initial situation and an evaluation function. This then creates an evaluation graph and starts evaluating the
    * nodes. Each actual evaluation is delegated to the evaluator argument.
    *
    * @param state initial state to pass into the evaluator function when visiting the first node. Any subsequent invocations of the evaluator function will be
    *              provided with the result of processing the previous node.
    * @param derivations a List of all Derivations to consider. The function will try to call all derivations for which inputs become available.
    * @param evaluator the function to process a node. This function is provided the current state and the derivation of the current node. It must return a new
    *                  state to be used for the next node.
    * @tparam A type of state used by the evaluator function. The algorithm is oblivious to the structure of the state and will only pass it to the evaluator
    *           function.
    * @return the resulting state of the last invocation to the evaluator function.
    */
  def runDerivations[A](state: A, derivations: List[Derivation], evaluator: (A, Derivation) => A): A = {
    if (!graphCache.get(derivations).isDefined) {
      graphCache.add(derivations, FactEngine.levelSorter(FactEngine.constructGraph(derivations)))
    }

    val graph: Levels = graphCache.get(derivations).get

    def levelRunner(state: A, level: Level): A = level.foldLeft(state)( (b, n) => evaluator(b, n.derivation) )

    graph.foldLeft(state)( levelRunner )
  }

  case class EvaluationException(message: String, derivation: Derivation, cause: Throwable) extends Exception(message, cause)

  def evaluatorExceptionWrapper[A](evaluator: (A, Derivation) => A): (A, Derivation) => A = {
    (a: A, d: Derivation) => {
      Try(evaluator(a, d)) match {
        case Success(newA) => newA
        case Failure(ex) => throw EvaluationException(s"Error while evaluating ${d.output}", d, ex)
      }
    }
  }

  /**
    * Runs the provided Derivations and yields the resulting Context containing all inputs as well as all computed values.
    *
    * @param c initial Context containing all the input values.
    * @param derivations a List of Derivations to consider with all the input values.
    * @return a Context containing all input values and all computed values.
    */
  def runNormalDerivations(c: Context, derivations: List[Derivation]): Context = {
    def evaluator(c: Context, d: Derivation): Context = {
      if (!c.contains(d.output) && d.condition(c)) {
        val operation = d match {
          case der: SubRunDerivation => {
            val options: Seq[Option[Any]] = runSubCalculations(c, der.subRunData)
            Some(options.flatten)
          }
          case der: DefaultDerivation => der.operation(c)
        }

        if (operation.isEmpty) c else c + (d.output -> operation.get)
      } else {
        c
      }
    }

    def runSubCalculations[B](c: Context, subRunData: SubRunData[Any, B]): Seq[Option[Any]] = {
      subRunData.inputList.toEval(c).get.map(input => subRunData.yieldValue(runDerivations(c ++ subRunData.contextAdditions(input), subRunData.derivations, evaluator)))
    }

    runDerivations(c, derivations, evaluatorExceptionWrapper(evaluator))
  }


  /**
    * Runs the provided Derivations and yields the resulting Context as well as a List of all Steps computed.
    *
    * @param c initial Context containing all input values.
    * @param derivations a List of Derivations to consider with all the input values.
    * @return a tuple containing the resulting Context and a List of all Steps taken.
    */
  def runDebugDerivations(c: Context, derivations: List[Derivation]): (Context, List[Step]) = {
    def evaluator(t: (Context, List[Step]), d: Derivation): (Context, List[Step]) = {
      val (c, steps) = t
      if (c.contains(d.output)) {
        (c, Step(c, d, "Output exists in context, skipping", c) :: steps)
      } else if (d.condition(c)) {
        d match {
          case der: SubRunDerivation => {
            val (results, subSteps): (List[Context], List[List[Step]]) = runSubCalculations(c, der.subRunData, d).unzip
            val options = results.map(der.subRunData.yieldValue)
            processStep(d, c, subSteps.flatten ::: steps, Some(options.flatten))
          }
          case der: DefaultDerivation => processStep(d, c, steps, der.operation(c))
        }
      } else {
        (c, Step(c, d, "Condition false", c) :: steps)
      }
    }

    def runSubCalculations[B](c: Context, subRunData: SubRunData[Any, B], d: Derivation): List[(Context, List[Step])] = {
      subRunData.inputList.toEval(c).get.map(input => {
        val newContext: Context = c ++ subRunData.contextAdditions(input)
        runDerivations((newContext, List(Step(c, d, "SubCalculation with new value", newContext))), subRunData.derivations, evaluator)
      })
    }

    def processStep(d: Derivation, c: Context, steps: List[Step], operation: Option[Any]): (Context, List[Step]) = {
      if (operation.isEmpty) {
        (c, Step(c, d, "Empty result", c) :: steps)
      } else {
        val newContext = c + (d.output -> operation.get)
        (newContext, Step(c, d, "Evaluated", newContext) :: steps)
      }
    }

    runDerivations((c, List()), derivations, evaluator)
  }

}
