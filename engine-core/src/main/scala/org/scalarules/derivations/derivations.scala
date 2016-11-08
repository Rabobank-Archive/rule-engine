package org.scalarules.derivations

import org.scalarules.derivations.DerivationTools._
import org.scalarules.engine._
import org.scalarules.facts.Fact
import org.scalarules.utils.{SourcePosition, SourceUnknown}

// TODO : Turn this off and fix it
import scala.language.existentials

/**
  * A `Derivation` models the basic element used inside the `Engine`. One execution step of the `Engine` will always
  * relate to a single `Derivation`. It defines the `Input`s required for it to be 'ready' as well  the `Fact` under
  * which the result should be stored inside the context.
  */
sealed trait Derivation {
  val input: Input
  val output: Output
  val condition: Condition
  val sourcePosition: SourcePosition = SourceUnknown()
  val conditionSourcePosition: SourcePosition = SourceUnknown()
}

/**
  * The default type of `Derivation` is used for normal executions of a single `Evaluation`.
  *
  * @param input all the input `Fact`s on which this `Derivation` depends.
  * @param output the `Fact` under which the result should be stored.
  * @param condition the `Condition` which determines whether this particular `Derviation` should be executed at all.
  * @param operation the actual `Evaluation` to perform when the `condition` parameter resolves to `true`. Its result
  *                  will be stored under the `Fact` declared in the `output` parameter.
  */
case class DefaultDerivation(input: Input,
                             output: Output,
                             condition: Condition,
                             operation: Evaluation[Any],
                             override val sourcePosition: SourcePosition = SourceUnknown(),
                             override val conditionSourcePosition: SourcePosition = SourceUnknown()) extends Derivation

/**
  * Special case for a sub `Derivation`. `Derivation`s of this type cause a nested run of the `Engine` to be performed
  * and some of the results of that run will end up being the result of this `Derivation`.
  *
  * Note: this type of `Derivation` is currently targeted at handling a `Fact` with a `List` as value type. The
  * `Derivation`s specified in this `SubRunDerivation` will be performed for each value inside that `List`.
  *
  * @param inputs all the input `Fact`s on which this `Derivation` depends directly. Dependencies of the inner `Derivations`
  *               will be determined automatically and added to the the value of this parameter to form the complete
  *               set of `Fact`s on which this `Derivation` depends.
  * @param output the `Fact` under which the result should be stored.
  * @param condition the `Condition` which determines whether this particular `Derviation` should be executed at all.
  * @param subRunData describes how to setup the nested execution and how to extract the result from it.
  */
case class SubRunDerivation(inputs: Input, output: Output, condition: Condition, subRunData: SubRunData[Any, _]) extends Derivation {
  private val outsideFactsInSubRun: Set[Fact[Any]] = computeAllInputs(subRunData.derivations) -- computeAllOutputs(subRunData.derivations) - subRunData.yieldFact
  val input = (subRunData.inputList :: inputs ++ outsideFactsInSubRun).distinct
}

/**
  * Specifies the `Derivation`s to run within a `SubRunDerivation` and how to handle the `Context` around its execution.
  *
  * @param derivations a `List` of `Derivation`s which must be executed in a nested run of the `Engine`.
  * @param contextAdditions a function to enhance the `Context` based on the input value for a particular run.
  * @param inputList the `Fact` whose value should be used to iterate over and perform sub runs for.
  * @param yieldFact the `Fact` to extract from the result of the sub run. These values will be collected and end up
  *                  being the overall result of the `SubRunDerivation`.
  * @tparam O type of the resulting output `Fact`.
  * @tparam I element type of the input `Fact`.
  */
case class SubRunData[+O, I](derivations: List[Derivation], contextAdditions: I => Context, inputList: Fact[List[I]], yieldFact: Fact[O]) {
  def yieldValue: Context => Option[O] = c => yieldFact.toEval(c)
}
