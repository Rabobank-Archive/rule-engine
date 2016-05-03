package org.scalarules.dsl.nl.grammar

import DslCondition.{andCombineConditions, factFilledCondition}
import org.scalarules.finance.core.Quantity
import org.scalarules.engine._

//scalastyle:off method.name

object Specificatie {
  def apply[T](dslCondition: DslCondition, output: Fact[T], dslEvaluation: DslEvaluation[T]): Derivation = {
    val condition = andCombineConditions(dslCondition, dslEvaluation.condition).condition
    val input = dslCondition.facts.toList ++ dslEvaluation.condition.facts

    DefaultDerivation(input, output, condition, dslEvaluation.evaluation)
  }
}

/* *********************************************************************************************************************************************************** *
 * The DSL syntax follows these rules:
 *
 * Derivation ::= `Gegeven` `(` Condition `)` `Bereken` (SingularBerekenStart | ListBerekenStart)
 *
 * Condition  ::= Fact `is` (Value | Fact | Aanwezig) [en | of]
 *
 * SingularBerekenStart ::= Fact `is` DslEvaluation
 * ListBerekenStart     ::= Fact `is` (DslListEvaluation | SubRunData)
 *
 * DslListAggregationOperation ::= (DslNumericalListAggregator) DslListEvaluation
 * DslNumericalListAggregator  ::= (`totaal van` | `gemiddelde van`)
 *
 * DslEvaluation
 * SubRunData :== SubBerekening over [ListFact] vul [Fact] geeft [outputFact] door [Berekening]
 *
 */

class GegevenWord(condition: DslCondition) {
  def Bereken[A](fact: SingularFact[A]): SingularBerekenStart[A] = new SingularBerekenStart(condition, fact, List())
  def Bereken[A](fact: ListFact[A]): ListBerekenStart[A] = new ListBerekenStart(condition, fact, List())
}

class SingularBerekenStart[T] private[grammar](condition: DslCondition, output: Fact[T], berekeningenAcc: List[Derivation]) {
  def is[T1 >: T](operation: DslEvaluation[T1]): BerekeningAccumulator = new BerekeningAccumulator(condition, Specificatie(condition, output, operation) :: berekeningenAcc)
}

class ListBerekenStart[T] private[grammar](condition: DslCondition, output: Fact[List[T]], berekeningenAcc: List[Derivation]) {
  def is[T1 <: T](operation: DslEvaluation[List[T1]]): BerekeningAccumulator = new BerekeningAccumulator(condition, Specificatie(condition, output, operation) :: berekeningenAcc)

  def is[B](subRunData : SubRunData[T, B]) : BerekeningAccumulator = {
    val c = andCombineConditions(condition, factFilledCondition(subRunData.inputList)).condition
    val input = subRunData.inputList :: condition.facts.toList
    val subRunDerivation: SubRunDerivation = SubRunDerivation(input, output, c, subRunData)
    new BerekeningAccumulator(condition, subRunDerivation :: berekeningenAcc)
  }
}

class BerekeningAccumulator private[grammar](condition: DslCondition, val derivations: List[Derivation]) {
  def en[T](fact: SingularFact[T]): SingularBerekenStart[T] = new SingularBerekenStart(condition, fact, derivations)
  def en[A](fact: ListFact[A]): ListBerekenStart[A] = new ListBerekenStart(condition, fact, derivations)
}
