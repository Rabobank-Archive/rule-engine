package org.scalarules.testutils.nl

import org.scalarules.dsl.nl.grammar.Berekening
import org.scalarules.engine.{Context, Fact, FactEngine}
import org.scalarules.services._
import org.scalarules.utils.PrettyPrinter

//scalastyle:off null

class HeuristicTester(heuristicService: HeuristicService, verplichteBerekening: Berekening, optioneleBerekeningen: Berekening*)
  extends BerekeningenTester(verplichteBerekening, optioneleBerekeningen:_*) {

  override def runTest(description: String, context: Context, factValues: Seq[FactValues]): Unit = it should description in {
    val heuristicResult: Option[AnalyzedScenario] = heuristicService.runHeuristics(context, FactEngine.runNormalDerivations(_, berekeningen))

    if (factValues == null) {
      withClue("Expecting to find no resulting scenario, but a result was returned: ") {
        heuristicResult should be(None)
      }
    } else {
      withClue("Expecting to find a resulting scenario, but none was found: ") {
        heuristicResult should not be (None)
      }

      val verwachteWaardes: Seq[(Fact[Any], Any)] = factValues flatMap (_.tuples)
      verwachteWaardes foreach { factValue => assert(heuristicResult.get.result, factValue._1, factValue._2) }
    }
  }

  override def debugTest(description: String, context: Context, factValues: Seq[FactValues]): Unit = it should description in {
    val heuristicResult: Option[AnalyzedScenario] = heuristicService.runHeuristics(context, c => {
      val debugResult = FactEngine.runDebugDerivations(c, berekeningen)
      println(PrettyPrinter.printSteps(debugResult._2))
      debugResult._1
    })

    if (factValues == null)
      heuristicResult should be (None)
    else {
      heuristicResult should not be (None)

      val verwachteWaardes: Seq[(Fact[Any], Any)] = factValues flatMap (_.tuples)
      verwachteWaardes foreach { factValue => assert(heuristicResult.get.result, factValue._1, factValue._2) }
    }
  }

  implicit class ExtendedResultOfGegeven(resultOfGegeven: ResultOfGegeven) {
    def verwachtGeenResultaat(): Unit = resultOfGegeven.tester.runTest(resultOfGegeven.description, resultOfGegeven.context, null)
    def debugGeenResultaat(): Unit = resultOfGegeven.tester.debugTest(resultOfGegeven.description, resultOfGegeven.context, null)
  }
}
