package org.scalarules.dsl.nl.grammar

import DslCondition.{andPredicate, orPredicate}
import org.scalarules.engine._
import org.scalatest.{FlatSpec, Matchers}

class ConditionsTestAndCondition extends FlatSpec with Matchers {

  it should "return false when given false and false" in
    assertTruthOfAndCondition(expected = false, inputA = false, inputB = false)

  it should "return false when given true and false" in
    assertTruthOfAndCondition(expected = false, inputA = true, inputB = false)

  it should "return false when given false and true" in
    assertTruthOfAndCondition(expected = false, inputA = false, inputB = true)

  it should "return true when given true and true" in
    assertTruthOfAndCondition(expected = true, inputA = true, inputB = true)

  private def assertTruthOfAndCondition(expected: Boolean, inputA: Boolean, inputB: Boolean): Unit =
    ConditionsTestHelper.assertConditionBasedMethod(andPredicate, expected, inputA, inputB)

}


class ConditionsTestOrCondition extends FlatSpec with Matchers {

  it should "return false when given false and false" in
    assertTruthOfOrCondition(expected = false, inputA = false, inputB = false)

  it should "return true when given false and true" in
    assertTruthOfOrCondition(expected = true, inputA = false, inputB = true)

  it should "return false when given true and false" in
    assertTruthOfOrCondition(expected = true, inputA = true, inputB = false)

  it should "return true when given true and true" in
    assertTruthOfOrCondition(expected = true, inputA = true, inputB = true)

  private def assertTruthOfOrCondition(expected: Boolean, inputA: Boolean, inputB: Boolean): Unit =
    ConditionsTestHelper.assertConditionBasedMethod(orPredicate, expected, inputA, inputB)

}


private object ConditionsTestHelper extends FlatSpec with Matchers {

  val context = Map.empty[Fact[_], Boolean]

  def assertConditionBasedMethod(f: (Condition, Condition) => Condition, expected: Boolean, inputA: Boolean, inputB: Boolean): Unit =
    f(_ => inputA, _ => inputB)(context) should be (expected)

}
