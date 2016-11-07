package org.scalarules.dsl.core.grammar

import java.util.concurrent.atomic.AtomicLong

import org.scalarules.dsl.core.grammar.AbstractDslLoopCounter.counter
import org.scalarules.dsl.core.grammar.DslCondition.factFilledCondition
import org.scalarules.engine._

object AbstractDslLoopCounter {
  private[grammar] val counter = new AtomicLong()
}

/**
  *
  * @param inputList The list of Facts of type I that are the input of the subcalculation
  * @param contextAddition Function from I to Context used to add the value of I to the Context of the subcalculation
  * @tparam I Input type
  */
abstract class AbstractDslLoop[I](inputList: ListFact[I], contextAddition: I => Context) {

  /**
    * @param dslEvaluation Simple Evaluation to be done for each item in the Inputlist
    * @tparam O Output type
    * @return SubRunData
    */
  protected def simpleEvaluation[O](dslEvaluation: DslEvaluation[O]): SubRunData[O, I] = {
    val yieldFact: Fact[O] = new SingularFact("Anonymous_Loop_Fact_" + counter.incrementAndGet())
    val derivation = new DefaultDerivation(List(inputList), yieldFact, dslEvaluation.condition.condition, dslEvaluation.evaluation)
    new SubRunData[O, I](List(derivation), contextAddition, inputList, yieldFact)
  }

  /**
    *
    * @param subRunData
    * @tparam O Output type
    * @tparam C
    * @return
    */
  protected def simpleSubRunData[O, C](subRunData: SubRunData[O, C]): SubRunData[List[O], I] = {
    val yieldFact: Fact[List[O]] = new ListFact("Anonymous_Loop_Fact_" + counter.incrementAndGet())
    val derivation = new SubRunDerivation(List(inputList), yieldFact, factFilledCondition(subRunData.inputList).condition, subRunData)
    new SubRunData[List[O], I](List(derivation), contextAddition, inputList, yieldFact)
  }

}
