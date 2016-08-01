package org.scalarules.finance.nl

import org.scalatest.{FlatSpec, Matchers}

class PercentageTest extends FlatSpec with Matchers {

  it should "accept a percentage below 0" in {
      (-1).procent
  }

  it should "accept a percentage above 100" in {
      101.procent
  }

  it should "do addition on a Percentage" in {
    5.procent + 10.procent should be(15.procent)
  }

  it should "do subtraction on a Percentage" in {
    25.procent - 5.procent should be(20.procent)
  }

  it should "do multiplication on a Percentage" in {
    10.procent * 50.procent should be(BigDecimal("0.05"))
  }

  it should "do multiplication on an Int" in {
    15.procent * 50 should be(BigDecimal("7.5"))
  }

  it should "do multiplication on a BigDecimal" in {
    15.procent * BigDecimal(50) should be(BigDecimal("7.5"))
  }

  it should "do multiplication on a Bedrag" in {
    30.procent * 60.euro should be(18.euro)
  }

  it should "do multiplication on a Bedrag Per Jaar" in {
    50.procent * 60.euro per Jaar should be(30.euro per Jaar)
  }

  it should "do division on a Percentage" in {
    30.procent / 20.procent should be(BigDecimal("1.5"))
  }

  it should "do division on an Int" in {
    50.procent / 2 should be(BigDecimal("0.25"))
  }

  it should "toString" in {
    50.procent.toString should be("50%")
  }

}

class PercentageImplicitsTest extends FlatSpec with Matchers {

  it should "construct a Percentage from an Int with 'procent'" in {
    20.procent should be (Percentage(20))
  }

  it should "construct a Percentage from a BigDecimal with 'procent'" in {
    BigDecimal(10).procent should be (Percentage(10))
  }

  it should "construct a Percentage from a String with 'procent'" in {
    "50.1".procent should be (Percentage(BigDecimal("50.1")))
  }

  it should "do commutative multiplication with Int" in {
    val n = 50
    val p = Percentage(10)
    n * p should be (p * n)
  }

  it should "do commutative multiplication with BigDecimal" in {
    val n = BigDecimal(50)
    val p = Percentage(10)
    n * p should be (p * n)
  }

  it should "do commutative multiplication with Bedrag" in {
    val a = Bedrag(20)
    val p = Percentage(50)
    a * p should be (p * a)
  }

  it should "do commutative multiplication with Per" in {
    val a = 20.euro per Maand
    val p = Percentage(50)
    a * p should be (10.euro per Maand)
  }
}
