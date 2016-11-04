package org.scalarules.dsl.nl.grammar

import java.util.concurrent.atomic.AtomicLong

import org.scalarules.derivations.{DefaultDerivation, SubRunData, SubRunDerivation}
import org.scalarules.dsl.nl.grammar.DslCondition.factFilledCondition
import org.scalarules.engine._
import org.scalarules.facts.{Fact, ListFact, SingularFact}

trait DslLoopWordTrait {
  private[grammar] val counter = new AtomicLong()
  val Loop = new DslLoopWord
}

class DslLoopWord {
  def over[I](listFact: ListFact[I]): DslLoopOverWord[I] = new DslLoopOverWord(listFact)
}

class DslLoopOverWord[I](inputList: ListFact[I]) {
  def per(element: Fact[I]): DslLoop[I] = new DslLoop(inputList, contextAddition(element))

  private def contextAddition(element: Fact[I]): I => Context = (x: I) => Map(element -> x)
}

class DslLoop[I](inputList: ListFact[I], contextAddition: I => Context) {

  def doe[O](dslEvaluation: DslEvaluation[O]): SubRunData[O, I] = {
    val yieldFact: Fact[O] = new SingularFact("Anonymous_Loop_Fact_" + counter.incrementAndGet())
    val derivation = new DefaultDerivation(List(inputList), yieldFact, dslEvaluation.condition.condition, dslEvaluation.evaluation)
    new SubRunData[O, I](List(derivation), contextAddition, inputList, yieldFact)
  }

  def doe[O, C](subRunData: SubRunData[O, C]): SubRunData[List[O], I] = {
    val yieldFact: Fact[List[O]] = new ListFact("Anonymous_Loop_Fact_" + counter.incrementAndGet())
    val derivation = new SubRunDerivation(List(inputList), yieldFact, factFilledCondition(subRunData.inputList).condition, subRunData)
    new SubRunData[List[O], I](List(derivation), contextAddition, inputList, yieldFact)
  }

  def geeft[O](resultFact: Fact[O]): SubBerekening[I, O] = new SubBerekening(inputList, contextAddition, resultFact)
}

class SubBerekening[I, O](inputList: ListFact[I], contextAddition: I => Context, yieldFact: Fact[O]) {
  def door(berekening: Berekening): SubRunData[O, I] = new SubRunData(berekening.berekeningen, contextAddition, inputList, yieldFact)
}
