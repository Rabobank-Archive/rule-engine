package org.scalarules.services

import org.scalarules.engine._
import org.scalarules.services.HeuristicServices._

import scala.annotation.tailrec

trait HeuristicService {
  def runHeuristics(initialContext: Context, engineRunner: Context => Context): Option[AnalyzedScenario] =
    attemptConditionSatisfaction(initialContext, successCondition, heuristics, scoringFunction, engineRunner)

  /**
    * Implement this function with the selection criteria for when a `Context` should be considered valid. This function is invoked after the
    * complete calculation of a `Context` to determine whether the results in that `Context` are to be returned by the `HeuristicService`.
    *
    * @param context the Context to check for success.
    * @return `true` if the `Context` should be considered for output, `false` to drop the `Context` from being a potential result.
    */
  def successCondition(context: Context): Boolean

  /**
    * Implement this function to gather your Heuristic functions. The result will be used in the evaluations to find a fitting `Context` result.
    *
    * @return a `List` of Heuristic functions which create useful modifications on the `Context`s in this scenario.
    */
  def heuristics: List[Heuristic]

  /**
    * Implement this function to allow the algorithm to find the best scoring situation. It receives two `AnalyzedScenario`s and is tasked with
    * selecting the best one. Whatever the 'best one' is, is up to the implementation.
    *
    * @param as1 first `AnalyzedScenario` to choose from.
    * @param as2 second `AnalyzedScenario` to choose from.
    * @return the `AnalyzedScenario` considered to be the best with respect to the other.
    */
  def scoringFunction(as1: AnalyzedScenario, as2: AnalyzedScenario): AnalyzedScenario

  /**
    * @return the default depth to search for a scenario fitting in this HeuristicService.
    */
  def defaultSearchDepth: Int = 4

  private def selectBest(as1: Option[AnalyzedScenario], as2: Option[AnalyzedScenario]): Option[AnalyzedScenario] = (as1, as2) match {
    case (None, None) => None
    case (None, _) => as2
    case (_, None) => as1
    case (_, _) => Some(scoringFunction(as1.get, as2.get))
  }

  /**
    * Performs an optimization run. It takes the initial `Context` to start from, a condition to test a `Context` for success, a `List` of heuristics to generate possible
    * `ContextModifiers` and a scoring function to determine the ordering of resulting `Context`s.
    *
    * @param initialContext the initial `Context` to start optimizing from.
    * @param condition a function which determines whether a resulting `Context` should be considered a 'success'. Any resulting `Context` which does not fulfill this
    *                  condition, will not be considered for the end result.
    * @param heuristics a `List` of functions which apply a certain heuristic to the given `Context` and return new options to consider. This in turn results in all the
    *                   `Context`s this function will consider for optimization.
    * @param scoring a sorting function over `Context`s. It is used to determine which result is the best.
    * @param engineRunner the function triggering a run in the `FactEngine`.
    * @return `Some(Context)` if the exploration has found at least one result which satisfies the condition. In that case, the result with the highest score will be
    *         returned. `None` if no result satisfied the condition.
    */
  private def attemptConditionSatisfaction(initialContext: Context,
                                           condition: Context => Boolean,
                                           heuristics: List[Heuristic],
                                           scoring: ScoringFunction,
                                           engineRunner: Context => Context,
                                           searchDepth: Int = defaultSearchDepth): Option[AnalyzedScenario] = {
    @tailrec
    def explore(options: List[(ContextModifier, Int)], explored: Set[ContextModifier], currentBest: Option[AnalyzedScenario]): Option[AnalyzedScenario] = options match {
      case (option, depth) :: xs if !explored.contains(option) => {
        val result: AnalyzedScenario = analyzeScenario(initialContext, option, engineRunner)
        val newHeuristics: List[List[ContextModifier]] = heuristics.map( heuristic => heuristic(initialContext, result.result) )

        val newExplorableOptions: List[(ContextModifier, Int)] = if (depth <= searchDepth)
            modifierCombinations(newHeuristics, Set[ContextModifier]()).filter(s => (!explored.contains(s))).toList.map((_, depth + 1))
          else
            List()

        explore(xs ::: newExplorableOptions, explored + option, selectBest(currentBest, if (condition(result.result)) Some(result) else None))
      }
      case (option, depth) :: xs if explored.contains(option) => {
        explore(xs, explored, currentBest)
      }
      case _ => currentBest
    }

    explore(List((Map[Fact[Any], Any](), 0)), Set(), None)
  }

  @tailrec
  private def modifierCombinations(heuristicModifiers: List[List[ContextModifier]], acc: Set[ContextModifier]): Set[ContextModifier] = heuristicModifiers match {
    case x :: xs => modifierCombinations(xs, x.foldLeft(acc)((others, modifier) => (others + modifier) ++ acc.map(_ ++ modifier)))
    case _ => acc
  }



  /**
    * Executes a single set of modifications on a `Context` runs a calculation. An `AnalyzedScenario` will be returned describing the result and the applied modification(s).
    *
    * @param context the initial `Context` on which to apply the modifiers.
    * @param modifier a `Map` of modified `Fact`s to add to the `Context` before running the calculations.
    * @param engineRunner the function triggering a run in the `FactEngine`.
    * @return the result and the applied modifiers are combined in an instance of `AnalyzedScenario`.
    */
  private def analyzeScenario(context: Context, modifier: ContextModifier, engineRunner: Context => Context): AnalyzedScenario = {
    val result = engineRunner( context ++ modifier )

    AnalyzedScenario(modifier, result)
  }
}

object HeuristicServices {
  type ContextModifier = Map[Fact[Any], Any]
  type ScoringFunction = (AnalyzedScenario, AnalyzedScenario) => AnalyzedScenario
  type Heuristic = (Context, Context) => List[ContextModifier]
}

/**
  * Combines a result `Context` and any modifiers applied to the input, describing what happened to get to the result.
  *
  * @param modifiers the `ContextModifier`s applied to the input.
  * @param result the resulting `Context` after all calculcations have been performed.
  */
case class AnalyzedScenario(modifiers: ContextModifier, result: Context)
