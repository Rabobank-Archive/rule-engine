package org.scalarules.engine

import org.scalarules.derivations._
import org.scalarules.facts.{Fact, SynthesizedFact}

import scala.util.{Failure, Success, Try}

object FactEngine {

  private var graphCache = new LRUCache[List[Derivation], Levels]()

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
      graphCache.add(derivations, DerivationTools.levelSorter(DerivationTools.constructGraph(derivations)))
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
        d match {
          case der: SubRunDerivation => {
            val subRunResults: Seq[(Option[Any], Context)] = runSubCalculations(c, der.subRunData)

            val (results, contexts) = subRunResults.unzip

            c + (d.output -> results.flatten) + (synthesizeIterationResultsFact(d.output) -> contexts)
          }
          case der: DefaultDerivation =>
            val result = der.operation(c)
            if (result.isEmpty) c else c + (d.output -> result.get)
        }
      } else {
        c
      }
    }

    def runSubCalculations[B](c: Context, subRunData: SubRunData[Any, B]): Seq[(Option[Any], Context)] = {
      subRunData.inputList.toEval(c).get.map(input => {
        val subRunContext: Context = runDerivations(c ++ subRunData.contextAdditions(input), subRunData.derivations, evaluator)
        (subRunData.yieldValue(subRunContext), subRunContext -- c.keys)
      })
    }

    runDerivations(c, derivations, evaluatorExceptionWrapper(evaluator))
  }

  private def synthesizeIterationResultsFact(fact: Fact[Any]) =
    SynthesizedFact[List[Context]](fact, "iteration_results", s"Synthesized fact to contain intermediate results of an iterated evalution over ${fact.name}")

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
        (c, AlreadyExistsStep(c, d, c) :: steps)
      } else if (d.condition(c)) {
        d match {
          case der: SubRunDerivation => {
            val (results, subSteps): (List[Context], List[List[Step]]) = runSubCalculations(c, der.subRunData, d).unzip
            val iterationResultOptions = results.map(der.subRunData.yieldValue)

            val newContext = c + (d.output -> iterationResultOptions.flatten) + (synthesizeIterationResultsFact(d.output) -> results)
            val newStep = IterationFinishedStep(c, d, newContext)
            (newContext, newStep :: subSteps.flatten ::: steps)
          }
          case der: DefaultDerivation => processStep(d, c, steps, der.operation(c))
        }
      } else {
        (c, ConditionFalseStep(c, d, c) :: steps)
      }
    }

    def runSubCalculations[B](c: Context, subRunData: SubRunData[Any, B], d: Derivation): List[(Context, List[Step])] = {
      subRunData.inputList.toEval(c).get.map(input => {
        val newContext: Context = c ++ subRunData.contextAdditions(input)
        runDerivations((newContext, List(IterationStartedStep(c, d, newContext))), subRunData.derivations, evaluator)
      })
    }

    def processStep(d: Derivation, c: Context, steps: List[Step], operation: Option[Any]): (Context, List[Step]) = {
      if (operation.isEmpty) {
        (c, EmptyResultStep(c, d, c) :: steps)
      } else {
        val newContext = c + (d.output -> operation.get)
        (newContext, EvaluatedStep(c, d, newContext) :: steps)
      }
    }

    runDerivations((c, List()), derivations, evaluator)
  }

}
