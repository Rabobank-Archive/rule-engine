package org.scalarules.utils

import org.scalarules.derivations.DerivationTools._
import org.scalarules.derivations.{DefaultDerivation, Derivation, SubRunDerivation}
import org.scalarules.engine._
import org.scalarules.facts.Fact

import scala.annotation.tailrec

object PrettyPrinter {

  val done = "Done."

  /**
    * Creates a pretty String containing all the proposed calculation levels, which will give you an idea of the order in which Facts will be calculated.
    *
    * @param levels the levels as determined by FactEngine.levelSorter
    * @return a nicely formatted String containing the proposed calculation order
    */
  def printLevels(levels: Levels): String = {
    @tailrec
    def go(levelIndex: Int, levels: Levels, acc: String): String = levels match {
      case l :: ls => {
        val levelLine = l.foldLeft(s"Level $levelIndex\n")((s, n) => s + s"  Fact: ${n.derivation.output}, # children: ${n.children.size}\n")
        go(levelIndex + 1, ls, acc + levelLine)
      }
      case _ => acc + done
    }

    go(0, levels, "\nProposed calculation order:\n")
  }

  def printContext(c: Context): String = {
    @tailrec
    def go(facts: List[(Fact[Any], Any)], acc: String): String = facts match {
      case f :: fs => go(fs, acc + s"  ${f._1} = ${f._2}\n")
      case _ => acc + done
    }

    val listOfFacts: List[(Fact[Any], Any)] = c.toList.sorted(Ordering.by[(Fact[Any], Any), String](_._1.name))

    go(listOfFacts, "\nValues in context:\n")
  }

  def printSteps(steps: List[Step]): String = {
    @tailrec
    def go(remainingSteps: List[Step], acc: String): String = remainingSteps match {
      case Step(initial, derivation, status, result) :: ss =>
        go(ss, s" * Evaluate: ${derivation.output.name}\n   * Result: $status\n   * Change: ${result -- initial.keys}\n" + acc)
      case _ =>
        acc + done
    }

    "\nSteps taken:\n" + go(steps, "")
  }

  def toJson(derivations: List[Derivation]): String = {
    val nodes = (computeAllInputs(derivations) ++ computeAllOutputs(derivations)).map( _.name ).toList
    val edges = derivations.flatMap(d => d.input.map(i => (i.name, d.output.name) ) )

    val inputs = (computeAllInputs(derivations) -- computeAllOutputs(derivations)).map( _.name )
    val outputs = (computeAllOutputs(derivations) -- computeAllInputs(derivations)).map( _.name )

    val nodeToExpressionsMap = derivations.map( d => (d.output.name, d match {case der: DefaultDerivation => der.operation.toString; case der: SubRunDerivation => "SubRun"}) ).toMap
    def nodeToExpressions(nodeName: String) = if (nodeToExpressionsMap contains nodeName) nodeToExpressionsMap(nodeName) else ""
    def nodeToType(nodeName: String): String = if (inputs contains nodeName) "Input"
      else if (outputs contains nodeName) "Output"
      else "Intermediate"

    val nodesWithIndex = nodes.zipWithIndex
    val nodeToIndexMap = nodesWithIndex.toMap

    val nodesJson = nodesWithIndex.map{ case (n, i) => buildNodeJson(i, n, nodeToType(n), nodeToExpressions(n)) }.mkString("\"nodes\": [", ", ", "]")
    val edgesJson = edges.map{ case (source, target) => buildEdgeJson(source, target, nodeToIndexMap) }.mkString("\"links\": [", ", ", "]")

    val json = s"{${nodesJson}, ${edgesJson}}"

    json
  }

  private def buildNodeJson(nodeIndex: Int, nodeName: String, nodeType: String, nodeDescription: String): String = {
    s"""{
        |  "node": "${nodeIndex}",
        |  "name": "${nodeName}",
        |  "type": "${nodeType}",
        |  "description": "${nodeDescription}"
        |  }
      """.stripMargin
  }

  private def buildEdgeJson(source: String, target: String, nodeToIndexMap: Map[String, Int]): String = {
    s"""{
        |  "id": "${source}-${target}",
        |  "source": ${nodeToIndexMap(source)},
        |  "target": ${nodeToIndexMap(target)},
        |  "value": 1
        |}
      """.stripMargin
  }

}
