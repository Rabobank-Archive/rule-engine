package org.scalarules.dsl.nl.grammar

import org.scalarules.derivations.{DefaultDerivation, Derivation, SubRunData, SubRunDerivation}
import org.scalarules.dsl.nl.grammar.DslCondition.{andCombineConditions, factFilledCondition}
import org.scalarules.dsl.nl.grammar.meta.DslMacros
import org.scalarules.engine
import org.scalarules.facts.{Fact, ListFact, SingularFact}
import org.scalarules.utils.{FileSourcePosition, SourcePosition, SourceUnknown}

import scala.language.experimental.macros

//scalastyle:off method.name

object Specificatie {
  def apply[T](dslCondition: DslCondition, output: Fact[T], dslEvaluation: DslEvaluation[T], sourcePosition: SourcePosition): Derivation = {
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

class GegevenWord(val initialCondition: DslCondition, val position: SourcePosition = SourceUnknown()) {
  val condition: DslCondition = position match {
    case SourceUnknown() => initialCondition
    case fsp @ FileSourcePosition(_, _, _, _, _) => {
      val DslCondition(facts, condition, _) = initialCondition

      DslCondition(facts, condition, position)
    }
  }

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

  /**
    * Specificeert hoe een lijst is opgebouwd uit de resultaten van een elementberekening die voor ieder element uit een invoerlijst is uitgevoerd.
    *
    * Syntax: <uitvoer> bevat resultaten van <ElementBerekening> over <invoer>
    */
  def bevat(r: ResultatenWord): LijstOpbouwConstruct[T] = new LijstOpbouwConstruct[T](condition, output, berekeningenAcc)
}

class ResultatenWord

class LijstOpbouwConstruct[Uit](condition: DslCondition, output: Fact[List[Uit]], berekeningAcc: List[Derivation]) {
  def van[I](uitTeVoerenElementBerekening: ElementBerekeningReference[I, Uit]): LijstOpbouwConstructMetBerekening[I] = new LijstOpbouwConstructMetBerekening[I](uitTeVoerenElementBerekening.berekening)

  class LijstOpbouwConstructMetBerekening[In](elementBerekening: ElementBerekening[In, Uit]) {

    def over(iterator: ListFact[In]): BerekeningAccumulator = {
      val contextAddition: In => engine.Context = (x: In) => Map(elementBerekening.invoerFact -> x)

      val subRunData = new SubRunData[Uit, In](elementBerekening.berekeningen, contextAddition, iterator, elementBerekening.uitvoerFact)

      val topLevelCondition = andCombineConditions(condition, factFilledCondition(subRunData.inputList)).condition

      new BerekeningAccumulator(condition, SubRunDerivation(subRunData.inputList :: condition.facts.toList, output, topLevelCondition, subRunData) :: berekeningAcc)
    }
  }
}

// --- supports naming the invoer and uitvoer inside an ElementBerekening
class InvoerWord{
  def is[In](iteratee: Fact[In]): InvoerSpecification[In] = new InvoerSpecification[In](iteratee)
}
class UitvoerWord{
  def is[Uit](iteratee: Fact[Uit]): UitvoerSpecification[Uit] = new UitvoerSpecification[Uit](iteratee)
}

class BerekeningAccumulator private[grammar](val condition: DslCondition, val derivations: List[Derivation]) {
  def en[A](fact: SingularFact[A]): SingularBerekenStart[A] = macro DslMacros.captureSingularBerekenSourcePositionWithAccumulatorMacroImpl[A]
  def en[A](fact: ListFact[A]): ListBerekenStart[A] = macro DslMacros.captureListBerekenSourcePositionWithAccumulatorMacroImpl[A]
}
