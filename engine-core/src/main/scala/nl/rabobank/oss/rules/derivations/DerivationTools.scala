package nl.rabobank.oss.rules.derivations

import nl.rabobank.oss.rules.facts.{Fact, OriginFact}
import nl.rabobank.oss.rules.engine._

import scala.annotation.tailrec

object DerivationTools {

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

  /**
    * Computes the Set of all unique Facts used by Derivations.
    *
    * @param derivations all available Derivations
    * @return a Set containing all unique input Facts
    */
  def computeAllInputs(derivations: List[Derivation]): Set[Fact[Any]] = {
    def collectInputs(derivations: List[Derivation], acc: Set[Fact[Any]]) : Set[Fact[Any]] = derivations match {
      case d :: ds => collectInputs(ds, acc ++ d.input)
      case Nil => acc
    }
    collectInputs(derivations, Set())
  }

  /**
    * Computes the Set of all unique Facts produced by Derivations. It also enforces uniqueness between Derivations, since we do not allow multiple Derivations
    * to produce the same Fact.
    *
    * @param derivations all available Derivations
    * @return a Set containing all unique output Facts
    */
  def computeAllOutputs(derivations: List[Derivation]): Set[Fact[Any]] = {
    def collectOutputs(derivations: List[Derivation], acc: Set[Fact[Any]]) : Set[Fact[Any]] = derivations match {
      case d :: ds => if (acc contains d.output) {
        throw new IllegalStateException("Found a second derivation trying to satisfy output " + d.output)
      } else {
        collectOutputs(ds, acc + d.output)
      }
      case Nil => acc
    }
    collectOutputs(derivations, Set())
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

}
