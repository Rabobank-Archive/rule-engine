package org.scalarules.dsl.nl.grammar

import DslCondition.{factFilledCondition, andCombineConditions}
import org.scalarules.dsl.core.types.NumberLike
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
 * SingularBerekenStart ::= Fact `is` (DslEvaluation | DslListAggregationOperation)
 * ListBerekenStart     ::= Fact `is` DslListEvaluation
 *
 * DslListAggregationOperation ::= (DslNumericalListAggregator) DslListEvaluation
 * DslNumericalListAggregator  ::= (`totaal van` | `gemiddelde van`)
 *
 * DslEvaluation
 *
 */

class GegevenWord(condition: DslCondition) {
  def Bereken[A](fact: SingularFact[A]): SingularBerekenStart[A] = new SingularBerekenStart(condition, fact, List())
  def Bereken[A](fact: ListFact[A]): ListBerekenStart[A] = new ListBerekenStart(condition, fact, List())
}

class SingularBerekenStart[T] private[grammar](condition: DslCondition, output: Fact[T], berekeningenAcc: List[Derivation]) {
  def is(operation: DslEvaluation[T]): BerekeningAccumulator = new BerekeningAccumulator(condition, Specificatie(condition, output, operation) :: berekeningenAcc)

  def is[T1 >: T : NumberLike](listAggr: DslNumberLikeListAggregator): DslNumberLikeListAggregationOperation[T1] =
    new DslNumberLikeListAggregationOperation[T1](listAggr, condition, output, berekeningenAcc)

  def is[T1 >: T : Ordering](listAggr: DslOrderedListAggregator): DslOrderedListAggregationOperation[T1] =
    new DslOrderedListAggregationOperation[T1](listAggr, condition, output, berekeningenAcc)

  def is(listElSelOp: SelectElementOnLiteralIndex): DslGenericListAggregationOperation[T] =
    new DslGenericListAggregationOperation[T](listElSelOp, condition, output, berekeningenAcc)

  def is(prikOperator: prikken.type): DslTableSelector[T] = new DslTableSelector(condition, output, berekeningenAcc)
}

class ListBerekenStart[T] private[grammar](condition: DslCondition, output: Fact[List[T]], berekeningenAcc: List[Derivation]) {
  def is(operation: DslEvaluation[List[T]]): BerekeningAccumulator = new BerekeningAccumulator(condition, Specificatie(condition, output, operation) :: berekeningenAcc)
  def is(prikOperator: prikken.type): DslTableSelectorForLists[T] = new DslTableSelectorForLists(condition, output, berekeningenAcc)
  def is[T1 >: T : NumberLike](listAggr: DslNumberLikeListAggregator): DslNumberLikeListAggregationOperation[List[T1]] =
    new DslNumberLikeListAggregationOperation[List[T1]](listAggr, condition, output, berekeningenAcc)

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

//  private[grammar] def toDerivation: List[Derivation] = {
//    berekeningenAcc map { berekening =>
//      val condition = andCombineConditions(berekening.condition, berekening.evaluation.condition).condition
//      val input = berekening.condition.facts.toList ++ berekening.evaluation.condition.facts
//
//      Derivation(input, berekening.output, condition, berekening.evaluation.evaluation)
//    }
//  }

//  private[grammar] def toDerivationAndInputSets: List[(Derivation, List[Fact[Any]], List[Fact[Any]])] = {
//    berekeningenAcc map { berekening =>
//      val conditionInformation = andCombineConditions(berekening.condition, berekening.evaluation.condition)
//
//      // TODO: Consider splitting the condition inputs and evaluation inputs on the Derivation level and then combining them inside the Engine
//      (DefaultDerivation(conditionInformation.facts.toList, berekening.output, conditionInformation.condition, berekening.evaluation.evaluation),
//        berekening.condition.facts.toList, berekening.evaluation.condition.facts.toList)
//    }
//  }
}
