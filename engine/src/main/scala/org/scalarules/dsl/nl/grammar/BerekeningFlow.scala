package org.scalarules.dsl.nl.grammar

import DslCondition.{andCombineConditions, factFilledCondition}
import org.scalarules.dsl.nl.grammar.`macro`.DslMacros
import org.scalarules.engine._
import org.scalarules.utils.SourcePosition

import scala.annotation.compileTimeOnly
import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros

//scalastyle:off method.name

object Specificatie {
  def apply[T](dslCondition: DslCondition, output: Fact[T], dslEvaluation: DslEvaluation[T], sourcePosition: SourcePosition): Derivation = {
    val condition = andCombineConditions(dslCondition, dslEvaluation.condition).condition
    val input = dslCondition.facts.toList ++ dslEvaluation.condition.facts

    DefaultDerivation(input, output, condition, dslEvaluation.evaluation, Some(sourcePosition))
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

class GegevenWord(val condition: DslCondition, val position: SourcePosition) {
  println( s"Defined Gegeven at : ${position}" ) // scalastyle:ignore

  def Bereken[A](fact: SingularFact[A]): SingularBerekenStart[A] = macro DslMacros.captureSingularBerekenSourcePositionMacroImpl[A]
  def Bereken[A](fact: ListFact[A]): ListBerekenStart[A] = macro DslMacros.captureListBerekenSourcePositionMacroImpl[A]
}

class SingularBerekenStart[T] (condition: DslCondition, output: Fact[T], berekeningenAcc: List[Derivation], sourcePosition: SourcePosition) {
  def is[T1 >: T](operation: DslEvaluation[T1]): BerekeningAccumulator = new BerekeningAccumulator(condition, Specificatie(condition, output, operation, sourcePosition) :: berekeningenAcc)
}

class ListBerekenStart[T] (condition: DslCondition, output: Fact[List[T]], berekeningenAcc: List[Derivation], sourcePosition: SourcePosition) {
  def is[T1 <: T](operation: DslEvaluation[List[T1]]): BerekeningAccumulator = new BerekeningAccumulator(condition, Specificatie(condition, output, operation, sourcePosition) :: berekeningenAcc)

  def is[B](subRunData : SubRunData[T, B]) : BerekeningAccumulator = {
    val c = andCombineConditions(condition, factFilledCondition(subRunData.inputList)).condition
    val input = subRunData.inputList :: condition.facts.toList
    val subRunDerivation: SubRunDerivation = SubRunDerivation(input, output, c, subRunData)
    new BerekeningAccumulator(condition, subRunDerivation :: berekeningenAcc)
  }
}

class BerekeningAccumulator private[grammar](val condition: DslCondition, val derivations: List[Derivation]) {
  def en[A](fact: SingularFact[A]): SingularBerekenStart[A] = macro DslMacros.captureSingularBerekenSourcePositionWithAccumulatorMacroImpl[A]
  def en[A](fact: ListFact[A]): ListBerekenStart[A] = macro DslMacros.captureListBerekenSourcePositionWithAccumulatorMacroImpl[A]
}
