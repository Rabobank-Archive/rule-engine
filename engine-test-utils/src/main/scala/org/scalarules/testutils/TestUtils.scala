package org.scalarules.testutils.nl

import org.scalarules.engine.{Derivation, Fact, FactEngine, Step, _}
import org.scalarules.services.{AnalyzedScenario, HeuristicService}
import org.scalarules.utils.PrettyPrinter

object TestUtils {

  def run(initial: Context, derivations: List[Derivation]): Context = FactEngine.runNormalDerivations(initial, derivations)
  def run(initial: Context, derivations: List[Derivation], heuristicService: HeuristicService): Option[AnalyzedScenario] = heuristicService.runHeuristics(initial, run(_, derivations))
  def debug(initial: Context, derivations: List[Derivation]): Context = {
    val result: (Context, List[Step]) = FactEngine.runDebugDerivations(initial, derivations)
    println(PrettyPrinter.printSteps(result._2))
    result._1
  }
  def debug(initial: Context, derivations: List[Derivation], heuristicService: HeuristicService): Option[AnalyzedScenario] = heuristicService.runHeuristics(initial, debug(_, derivations))


  def runAndExtractFact[A](initial: Context, derivations: List[Derivation], extract: Fact[A]): Option[A] = {
    extract.toEval( run(initial, derivations) )
  }

  def debugAndExtractFact[A](initial: Context, derivations: List[Derivation], extract: Fact[A]): Option[A] = {
    extract.toEval( debug(initial, derivations) )
  }

  def runAndExtractFact[A](initial: Context, derivations: List[Derivation], heuristicService: HeuristicService, extract: Fact[A]): Option[A] = {
    extract.toEval( run(initial, derivations, heuristicService).get.result )
  }

  def debugAndExtractFact[A](initial: Context, derivations: List[Derivation], heuristicService: HeuristicService, extract: Fact[A]): Option[A] = {
    extract.toEval( debug(initial, derivations, heuristicService).get.result )
  }

}