package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.grammar.DslCondition.{andCombineConditions, factFilledCondition}
import org.scalarules.dsl.core.grammar.{DerivationAccumulator, DslEvaluation}
import org.scalarules.dsl.nl.grammar.meta.DslMacros
import org.scalarules.engine
import org.scalarules.engine._
import org.scalarules.utils.{FileSourcePosition, SourcePosition, SourceUnknown}

import scala.language.experimental.macros

//scalastyle:off method.name

object Specificatie {
  def apply[T](dslCondition: DslConditionNL, output: Fact[T], dslEvaluation: DslEvaluation[T], sourcePosition: SourcePosition): Derivation = {
    val condition = andCombineConditions(dslCondition, dslEvaluation.condition).condition
    val input = dslCondition.facts.toList ++ dslEvaluation.condition.facts

    DefaultDerivation(input, output, condition, dslEvaluation.evaluation, sourcePosition, dslCondition.sourcePosition)
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
 * LijstOpbouwConstruct ::= [Fact] `bevat` `resultaten` `van` [ElementBerekening] `over` [Fact]
 */
class GegevenWord(val initialCondition: DslConditionNL, val position: SourcePosition = SourceUnknown()) {
  val condition: DslConditionNL = position match {
    case SourceUnknown() => initialCondition
    case fsp @ FileSourcePosition(_, _, _, _, _) => {
      new DslConditionNL(initialCondition.facts, initialCondition.condition, position)
    }
  }

  def Bereken[A](fact: SingularFact[A]): SingularBerekenStart[A] = macro DslMacros.captureSingularBerekenSourcePositionMacroImpl[A]
  def Bereken[A](fact: ListFact[A]): ListBerekenStart[A] = macro DslMacros.captureListBerekenSourcePositionMacroImpl[A]
}

class SingularBerekenStart[T] (condition: DslConditionNL, output: Fact[T], berekeningenAcc: List[Derivation], sourcePosition: SourcePosition) {
  def is[T1 >: T](operation: DslEvaluation[T1]): BerekeningAccumulator = new BerekeningAccumulator(condition, Specificatie(condition, output, operation, sourcePosition) :: berekeningenAcc)
}

class ListBerekenStart[T] (condition: DslConditionNL, output: Fact[List[T]], berekeningenAcc: List[Derivation], sourcePosition: SourcePosition) {
  def is[T1 <: T](operation: DslEvaluation[List[T1]]): BerekeningAccumulator = new BerekeningAccumulator(condition, Specificatie(condition, output, operation, sourcePosition) :: berekeningenAcc)

  def is[B](subRunData : SubRunData[T, B]) : BerekeningAccumulator = {
    val c = andCombineConditions(condition, factFilledCondition(subRunData.inputList)).condition
    val input = subRunData.inputList :: condition.facts.toList
    val subRunDerivation: SubRunDerivation = SubRunDerivation(input, output, c, subRunData)
    new BerekeningAccumulator(condition, subRunDerivation :: berekeningenAcc)
  }

  /**
    * Specificeert hoe een lijst is opgebouwd uit de resultaten van een elementberekening die voor ieder element uit een invoerlijst is uitgevoerd.
    *
    * Syntax: <uitvoer> bevat resultaten van <ElementBerekening> over <invoer>
    */
  def bevat(r: ResultatenWord): LijstOpbouwConstruct[T] = new LijstOpbouwConstruct[T](condition, output, berekeningenAcc)
}

class ResultatenWord

class LijstOpbouwConstruct[Uit](condition: DslConditionNL, output: Fact[List[Uit]], berekeningAcc: List[Derivation]) {
  def van[I](uitTeVoerenElementBerekening: ElementBerekeningReference[I, Uit]): LijstOpbouwConstructMetBerekening[I] = new LijstOpbouwConstructMetBerekening[I](uitTeVoerenElementBerekening.berekening)

  class LijstOpbouwConstructMetBerekening[In](elementBerekening: ElementBerekening[In, Uit]) {

    def over(iterator: ListFact[In]): BerekeningAccumulator = {
      val contextAddition: In => engine.Context = (x: In) => Map(elementBerekening.invoerFact -> x)

      val subRunData = new SubRunData[Uit, In](elementBerekening.derivations, contextAddition, iterator, elementBerekening.uitvoerFact)

      val topLevelCondition = andCombineConditions(condition, factFilledCondition(subRunData.inputList)).condition

      new BerekeningAccumulator(condition, SubRunDerivation(subRunData.inputList :: condition.facts.toList, output, topLevelCondition, subRunData) :: berekeningAcc)
    }
  }
}

// --- supports naming the invoer and uitvoer inside an ElementBerekening
class InvoerWord{
  def is[In](iteratee: Fact[In]): InvoerSpec[In] = new InvoerSpec[In](iteratee)
}
class UitvoerWord{
  def is[Uit](iteratee: Fact[Uit]): UitvoerSpec[Uit] = new UitvoerSpec[Uit](iteratee)
}

class BerekeningAccumulator private[grammar](val condition: DslConditionNL, val derivations: List[Derivation]) extends DerivationAccumulator {
  def en[A](fact: SingularFact[A]): SingularBerekenStart[A] = macro DslMacros.captureSingularBerekenSourcePositionWithAccumulatorMacroImpl[A]
  def en[A](fact: ListFact[A]): ListBerekenStart[A] = macro DslMacros.captureListBerekenSourcePositionWithAccumulatorMacroImpl[A]
}
