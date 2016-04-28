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
  def determine[A](fact: SingularFact[A]): SingularDeterminationStart[A] = new SingularDeterminationStart(condition, fact, List())
  def determine[A](fact: ListFact[A]): ListDeterminationStart[A] = new ListDeterminationStart(condition, fact, List())
}

class SingularDeterminationStart[T] private[grammar](condition: DslCondition, output: Fact[T], derivationAcc: List[Derivation]) {
  def is[T1 >: T](operation: DslEvaluation[T1]): DeterminationAccumulator = new DeterminationAccumulator(condition, Specification(condition, output, operation) :: derivationAcc)
}

class ListDeterminationStart[T] private[grammar](condition: DslCondition, output: Fact[List[T]], derivationAcc: List[Derivation]) {
  def is[T1 <: T](operation: DslEvaluation[List[T1]]): DeterminationAccumulator = new DeterminationAccumulator(condition, Specification(condition, output, operation) :: derivationAcc)

  def is[B](subRunData : SubRunData[T, B]) : DeterminationAccumulator = {
    val c = andCombineConditions(condition, factFilledCondition(subRunData.inputList)).condition
    val input = subRunData.inputList :: condition.facts.toList
    val subRunDerivation: SubRunDerivation = SubRunDerivation(input, output, c, subRunData)
    new DeterminationAccumulator(condition, subRunDerivation :: derivationAcc)
  }
}

class DeterminationAccumulator private[grammar](condition: DslCondition, val derivations: List[Derivation]) {
  def and[T](fact: SingularFact[T]): SingularDeterminationStart[T] = new SingularDeterminationStart(condition, fact, derivations)
  def and[A](fact: ListFact[A]): ListDeterminationStart[A] = new ListDeterminationStart(condition, fact, derivations)
}
