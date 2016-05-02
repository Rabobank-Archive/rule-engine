package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.nl.grammar.DslCondition.factFilledCondition
import org.scalarules.engine._

trait DslLoopWordTrait {
  val Loop = new DslLoopWord
}
class DslLoopWord {
  def over[A](listFact: ListFact[A]): DslLoopOverWord[A] = new DslLoopOverWord(listFact)
}

class DslLoopOverWord[A](listFact: ListFact[A]) {
  def per(fact: SingularFact[A]): DslLoop[A] = new DslLoop(listFact, fact)
}

class DslLoop[A](listFact: ListFact[A], fact: SingularFact[A]) {
  def doe[B, C](dslEvaluation: SubRunData[B, C]): SubRunData[List[B], A] = {
    val resultFact: Fact[List[B]] = new ListFact("LoopFact")
    val derivation: SubRunDerivation = new SubRunDerivation(List(listFact), resultFact, factFilledCondition(dslEvaluation.inputList).condition, dslEvaluation)
    new SubRunData[List[B], A](List(derivation), (x: A) => Map(fact -> x), listFact, resultFact)
  }

  def geeft[B](resultFact: Fact[B]): SubBerekening[A, B] = {
    val operation: A => Context = (x: A) => Map(fact -> x)
    new SubBerekening(listFact, operation, resultFact)
  }
}

class SubBerekening[A, B](listFact: ListFact[A], operation: A => Context, resultFact: Fact[B]) {
  def door(berekening: Berekening): SubRunData[B, A] = new SubRunData(berekening.berekeningen, operation, listFact, resultFact)
}
