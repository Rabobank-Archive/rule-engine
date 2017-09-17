package nl.rabobank.rules.testutils.base

import nl.rabobank.rules.derivations.Derivation
import nl.rabobank.rules.engine.{Context, FactEngine, Step}
import nl.rabobank.rules.facts.Fact
import nl.rabobank.rules.services.{AnalyzedScenario, HeuristicService}
import nl.rabobank.rules.utils.PrettyPrinter

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
