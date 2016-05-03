package org.scalarules.finance.core

import org.scalarules.finance.nl._
import org.scalatest.{FlatSpec, Matchers}

class QuantityTest extends FlatSpec with Matchers {

  behavior of "Quantity BigDecimal"
  val evBigDecimal = implicitly[Quantity[BigDecimal]]

  it should "do addition" in {
    evBigDecimal.plus(1, 2) should be (BigDecimal(3))
  }

  it should "do subtraction" in {
    evBigDecimal.minus(4, 1) should be (BigDecimal(3))
  }

  it should "do multiplication" in {
    evBigDecimal.multiply(2, 3) should be (BigDecimal(6))
  }

  it should "do division" in {
    evBigDecimal.divideAsFraction(6, 2) should be (BigDecimal(3))
  }

  it should "do negation" in {
    evBigDecimal.negate(5) should be (BigDecimal(-5))
  }

  it should "return 0" in {
    evBigDecimal.zero should be (BigDecimal(0))
  }

  it should "return 1" in {
    evBigDecimal.one should be (BigDecimal(1))
  }


  behavior of "Quantity Bedrag"
  val evAmount = implicitly[Quantity[Bedrag]]

  it should "do addition" in {
    evAmount.plus(1.euro, 2.euro) should be (3.euro)
  }

  it should "do subtraction" in {
    evAmount.minus(4.euro, 1.euro) should be (3.euro)
  }

  it should "do multiplication" in {
    evAmount.multiply(2.euro, 3) should be (6.euro)
  }

  it should "do division" in {
    evAmount.divide(6.euro, 2) should be (3.euro)
  }

  it should "do negation" in {
    evAmount.negate(5.euro) should be ((-5).euro)
  }

  it should "return 0 euro" in {
    evAmount.zero should be (0.euro)
  }

  it should "return 1 euro" in {
    evAmount.one should be (1.euro)
  }


  behavior of "Quantity Per"
  val evPer = implicitly[Quantity[BigDecimal Per Maand]]

  it should "do addition" in {
    evPer.plus(1 per Maand, 2 per Maand) should be (3 per Maand)
  }

  it should "do subtraction" in {
    evPer.minus(4 per Maand, 1 per Maand) should be (3 per Maand)
  }

  it should "do multiplication" in {
    evPer.multiply(2 per Maand, 3) should be (6 per Maand)
  }

  it should "do division" in {
    evPer.divide(6 per Maand, 2) should be (3 per Maand)
  }

  it should "do negation" in {
    evPer.negate(5 per Maand) should be (-5 per Maand)
  }

  it should "return 0 per Maand" in {
    evPer.zero should be (0 per Maand)
  }

  it should "return 1 per Maand" in {
    evPer.one should be (1 per Maand)
  }
}
