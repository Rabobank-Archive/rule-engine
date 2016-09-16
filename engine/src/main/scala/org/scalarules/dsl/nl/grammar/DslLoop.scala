package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.grammar.{AbstractDslLoop, DslEvaluation}
import org.scalarules.engine._

trait DslLoopWordTrait {
  val Loop = new DslLoopWord
}

class DslLoopWord {
  def over[I](listFact: ListFact[I]): DslLoopOverWord[I] = new DslLoopOverWord(listFact)
}

class DslLoopOverWord[I](inputList: ListFact[I]) {
  def per(element: Fact[I]): DslLoop[I] = new DslLoop(inputList, contextAddition(element))

  private def contextAddition(element: Fact[I]): I => Context = (x: I) => Map(element -> x)
}

class DslLoop[I](inputList: ListFact[I], contextAddition: I => Context) extends AbstractDslLoop(inputList, contextAddition) {
  def doe[O](dslEvaluation: DslEvaluation[O]): SubRunData[O, I] = super.simpleEvaluation(dslEvaluation)
  def doe[O, C](subRunData: SubRunData[O, C]): SubRunData[List[O], I] = super.simpleSubRunData(subRunData)
  def geeft[O](resultFact: Fact[O]): SubBerekening[I, O] = new SubBerekening(inputList, contextAddition, resultFact)
}

class SubBerekening[I, O](inputList: ListFact[I], contextAddition: I => Context, yieldFact: Fact[O]) {
  def door(berekening: Berekening): SubRunData[O, I] = new SubRunData(berekening.derivations, contextAddition, inputList, yieldFact)
}
