package org.scalarules.dsl.nl.grammar

import java.util.concurrent.atomic.AtomicLong

import org.scalarules.dsl.nl.grammar.DslCondition.factFilledCondition
import org.scalarules.engine._

trait DslLoopWordTrait {
  private[grammar] val counter = new AtomicLong()
  val Loop = new DslLoopWord
}

class DslLoopWord {
  def over[A](listFact: ListFact[A]): DslLoopOverWord[A] = new DslLoopOverWord(listFact)
}

class DslLoopOverWord[A](listFact: ListFact[A]) {
  def per(fact: Fact[A]): DslLoop[A] = new DslLoop(listFact, fact)
}

class DslLoop[A](listFact: ListFact[A], fact: Fact[A]) {
  def doe[B, C](subRunData: SubRunData[B, C]): SubRunData[List[B], A] =
    shortInnerLoop((resultFact: Fact[List[B]]) => new SubRunDerivation(List(listFact), resultFact, factFilledCondition(subRunData.inputList).condition, subRunData))

  def doe[B](dslEvaluation: DslEvaluation[B]): SubRunData[List[B], A] =
    shortInnerLoop((resultFact: Fact[List[B]]) => new DefaultDerivation(List(listFact), resultFact, dslEvaluation.condition.condition, dslEvaluation.evaluation))

  private def shortInnerLoop[B](derivationFunction: Fact[List[B]] => Derivation): SubRunData[List[B], A] = {
    val yieldFact: Fact[List[B]] = new ListFact("Anonymous_Loop_Fact_" + counter.incrementAndGet())
    val derivation = derivationFunction(yieldFact)
    new SubRunData[List[B], A](derivations = List(derivation), contextAdditions = (x: A) => Map(fact -> x), inputList = listFact, yieldFact = yieldFact)
  }

  def geeft[B](resultFact: Fact[B]): SubBerekening[A, B] = {
    val operation: A => Context = (x: A) => Map(fact -> x)
    new SubBerekening(listFact, operation, resultFact)
  }
}

class SubBerekening[A, B](listFact: ListFact[A], operation: A => Context, resultFact: Fact[B]) {
  def door(berekening: Berekening): SubRunData[B, A] = new SubRunData(berekening.berekeningen, operation, listFact, resultFact)
}
