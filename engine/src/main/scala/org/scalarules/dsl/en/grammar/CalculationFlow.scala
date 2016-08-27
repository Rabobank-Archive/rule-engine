package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.DslCondition._
import org.scalarules.dsl.core.grammar.{DslCondition, DslEvaluation}
import org.scalarules.engine._

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

class GivenWord(condition: DslCondition) {
  def determine[A](fact: SingularFact[A]): SingularCalculationStart[A] = new SingularCalculationStart(condition, fact, List())
  def determine[A](fact: ListFact[A]): ListCalculationStart[A] = new ListCalculationStart(condition, fact, List())
}

class SingularCalculationStart[T] private[grammar](condition: DslCondition, output: Fact[T], derivationAcc: List[Derivation]) {
  def is[T1 >: T](operation: DslEvaluation[T1]): CalculationAccumulator = new CalculationAccumulator(condition, Specification(condition, output, operation) :: derivationAcc)
}

class ListCalculationStart[T] private[grammar](condition: DslCondition, output: Fact[List[T]], derivationAcc: List[Derivation]) {
  def is[T1 <: T](operation: DslEvaluation[List[T1]]): CalculationAccumulator = new CalculationAccumulator(condition, Specification(condition, output, operation) :: derivationAcc)

  def is[B](subRunData : SubRunData[T, B]) : CalculationAccumulator = {
    val c = andCombineConditions(condition, factFilledCondition(subRunData.inputList)).condition
    val input = subRunData.inputList :: condition.facts.toList
    val subRunDerivation: SubRunDerivation = SubRunDerivation(input, output, c, subRunData)
    new CalculationAccumulator(condition, subRunDerivation :: derivationAcc)
  }
}

class CalculationAccumulator private[grammar](condition: DslCondition, val derivations: List[Derivation]) {
  def and[T](fact: SingularFact[T]): SingularCalculationStart[T] = new SingularCalculationStart(condition, fact, derivations)
  def and[A](fact: ListFact[A]): ListCalculationStart[A] = new ListCalculationStart(condition, fact, derivations)
}
