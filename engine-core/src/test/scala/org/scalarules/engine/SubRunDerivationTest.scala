package org.scalarules.engine

import org.scalatest.{Matchers, FlatSpec}

class SubRunDerivationTest extends FlatSpec with Matchers {

  private val condition: Condition = c => true
  private val outputSub1 = SingularFact[Int]("SubDer1Output")
  private val inputSub1 = SingularFact[Int]("SubDer1Input1")
  private val input1Sub2 = SingularFact[Int]("SubDer2Input1")
  private val input2Sub2 = SingularFact[Int]("SubDer2Input2")
  private val derivation1 = DefaultDerivation(List(inputSub1), outputSub1, condition, new NoopEvaluation)
  private val derivation2 = DefaultDerivation(List(input1Sub2, input2Sub2, outputSub1), SingularFact[Int]("SubDer2Output"), condition, new NoopEvaluation)
  private val subDerivations = List(derivation1, derivation2)

  it should "Take inputs of subcalculations into account when returning input" in {
    val mainInput = SingularFact[Int]("MainInputFact")
    val listInput = ListFact[String]("ListInput")
    val derivation: SubRunDerivation = SubRunDerivation(List(mainInput), SingularFact[Int]("MainOutputFact"), condition,
      SubRunData[Int, String](subDerivations, _ => Map(), listInput, SingularFact[Int]("SubDer2Output")))
    derivation.input should contain only(mainInput, listInput, inputSub1, input1Sub2, input2Sub2)
  }

}
