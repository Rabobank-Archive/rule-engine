package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.DslCondition._
import org.scalarules.dsl.core.grammar.{DerivationAccumulator, DslCondition, DslEvaluation}
import org.scalarules.dsl.en.grammar.meta.DslMacros
import org.scalarules.engine
import org.scalarules.engine._
import org.scalarules.utils.{FileSourcePosition, SourcePosition, SourceUnknown}

import scala.language.experimental.macros

//scalastyle:off method.name

object Specification {
  def apply[T](dslCondition: DslCondition, output: Fact[T], dslEvaluation: DslEvaluation[T]): Derivation = {
    val condition = andCombineConditions(dslCondition, dslEvaluation.condition).condition
    val input = dslCondition.facts.toList ++ dslEvaluation.condition.facts

    DefaultDerivation(input, output, condition, dslEvaluation.evaluation)
  }
}

/* *********************************************************************************************************************************************************** *
 * The DSL syntax follows these rules:
 *
 * Derivation ::= `Given` `(` Condition `)` `Determine` (SingularDeterminationStart | ListDeterminationStart)
 *
 * Condition  ::= Fact `is` (Value | Fact | Aanwezig) [en | of]
 *
 * SingularBerekenStart ::= Fact `is` DslEvaluation
 * ListBerekenStart     ::= Fact `is` (DslListEvaluation | SubRunData)
 *
 */

class GivenWord(initialCondition: DslCondition, val position: SourcePosition = SourceUnknown()) {
  val condition: DslCondition = position match {
    case SourceUnknown() => initialCondition
    case fsp @ FileSourcePosition(_, _, _, _, _) => {
      new DslCondition(initialCondition.facts, initialCondition.condition, position)
    }
  }

  def determine[A](fact: SingularFact[A]): SingularCalculationStart[A] = macro DslMacros.captureSingularBerekenSourcePositionMacroImpl[A]
  def determine[A](fact: ListFact[A]): ListCalculationStart[A] = macro DslMacros.captureListBerekenSourcePositionMacroImpl[A]
}

class SingularCalculationStart[T] private[grammar](condition: DslCondition, output: Fact[T], derivationAcc: List[Derivation], sourcePosition: SourcePosition) {
  def is[T1 >: T](operation: DslEvaluation[T1]): CalculationAccumulator = new CalculationAccumulator(condition, Specification(condition, output, operation) :: derivationAcc)
}

class ListCalculationStart[T] private[grammar](condition: DslCondition, output: Fact[List[T]], derivationAcc: List[Derivation], sourcePosition: SourcePosition) {
  def is[T1 <: T](operation: DslEvaluation[List[T1]]): CalculationAccumulator = new CalculationAccumulator(condition, Specification(condition, output, operation) :: derivationAcc)

  def is[B](subRunData : SubRunData[T, B]) : CalculationAccumulator = {
    val c = andCombineConditions(condition, factFilledCondition(subRunData.inputList)).condition
    val input = subRunData.inputList :: condition.facts.toList
    val subRunDerivation: SubRunDerivation = SubRunDerivation(input, output, c, subRunData)
    new CalculationAccumulator(condition, subRunDerivation :: derivationAcc)
  }

  /**
    * Specifies how a List is constructed from the results of applying an ElementCalculation to each element of the input List.
    *
    * Syntax: <output> contains results of <ElementCalculation> over <input>
    */
  def contains(r: ResultsWord): ListIterationConstruct[T] = new ListIterationConstruct[T](condition, output, derivationAcc)
}

class ResultsWord

class ListIterationConstruct[Out](condition: DslCondition, output: Fact[List[Out]], berekeningAcc: List[Derivation]) {
  def of[I](elementCalculationToExecute: ElementCalculationReference[I, Out]): ListIterationConstructWithCalculation[I] = new ListIterationConstructWithCalculation[I](elementCalculationToExecute.calculation)

  class ListIterationConstructWithCalculation[In](elementCalculation: ElementCalculation[In, Out]) {

    def over(iterator: ListFact[In]): CalculationAccumulator = {
      val contextAddition: In => engine.Context = (x: In) => Map(elementCalculation.inputFact -> x)

      val subRunData = new SubRunData[Out, In](elementCalculation.derivations, contextAddition, iterator, elementCalculation.outputFact)

      val topLevelCondition = andCombineConditions(condition, factFilledCondition(subRunData.inputList)).condition

      new CalculationAccumulator(condition, SubRunDerivation(subRunData.inputList :: condition.facts.toList, output, topLevelCondition, subRunData) :: berekeningAcc)
    }
  }
}

// --- supports naming the invoer and uitvoer inside an ElementCalculation
class InputWord{
  def is[In](iteratee: Fact[In]): InputSpec[In] = new InputSpec[In](iteratee)
}
class OutputWord{
  def is[Out](iteratee: Fact[Out]): OutputSpec[Out] = new OutputSpec[Out](iteratee)
}


class CalculationAccumulator private[grammar](condition: DslCondition, val derivations: List[Derivation]) extends DerivationAccumulator {
  def and[A](fact: SingularFact[A]): SingularCalculationStart[A] = macro DslMacros.captureSingularBerekenSourcePositionWithAccumulatorMacroImpl[A]
  def and[A](fact: ListFact[A]): ListCalculationStart[A] = macro DslMacros.captureListBerekenSourcePositionWithAccumulatorMacroImpl[A]
}
