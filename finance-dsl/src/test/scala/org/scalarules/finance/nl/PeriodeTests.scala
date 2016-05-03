package org.scalarules.finance.nl

import org.scalatest.{FlatSpec, Matchers}

class PeriodeTest extends FlatSpec with Matchers {

  val x = 5.maanden
  val y = 2.maanden

  it should "not accept a negative amount of months" in {
    intercept[IllegalArgumentException] {
      (-1).maand
    }
  }

  it should "do addition" in {
    x + y should be(7.maanden)
  }

  it should "do subtraction" in {
    x - y should be(3.maanden)
  }

  it should "have an ordering" in {
    x > y should be(true)
  }

  it should "return the frequency of the Tijdsduur per year" in {
    12.maanden.frequentiePerJaar should be(1)
    6.maanden.frequentiePerJaar should be(2)
    4.maanden.frequentiePerJaar should be(3)
    1.maand.frequentiePerJaar should be(12)
  }

  it should "truncate the frequency per year" in {
    5.maanden.frequentiePerJaar should be(2)
    7.maanden.frequentiePerJaar should be(1)
    42.maanden.frequentiePerJaar should be(0)
  }

  it should "return the Tijdsduur in whole years" in {
    13.maanden.inAfgekapteJaren should be(1)
    23.maanden.inAfgekapteJaren should be(1)
    24.maanden.inAfgekapteJaren should be(2)
  }

  it should "truncate to whole years" in {
    13.maanden.afgekaptOpJaren should be(12.maanden)
    23.maanden.afgekaptOpJaren should be(12.maanden)
    24.maanden.afgekaptOpJaren should be(24.maanden)
  }

  it should "map over years" in {
    val expected = (0 until 10) map (_.toString)
    10.jaar mapOverJaren (_.toString) should be(expected)
  }

  it should "map over truncated years" in {
    val expected = (0 until 2) map (_.toString)
    33.maanden mapOverJaren (_.toString) should be(expected)
  }

  it should "map over 0 years" in {
    0.maanden mapOverJaren (_.toString) should be(Nil)
  }

  it should "toString" in {
    Periode(10).toString should be("10 maanden")
  }

}

  class PeriodeImplicitsTest extends FlatSpec with Matchers {

  it should "construct a Duration from an int with 'maand' and 'maanden" in {
    1.maand should be (Periode(1))
    2.maanden should be (Periode(2))
  }

  it should "construct a Duration from an int with 'jaar'" in {
    2.jaar should be (Periode(24))
  }

}
