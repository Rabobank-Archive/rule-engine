package org.scalarules.finance.nl

import org.scalatest.{FlatSpec, Matchers}

class BedragTest extends FlatSpec with Matchers {

  val x = 5.euro
  val y = 2.euro

  it should "do addition" in {
    x + y should be(7.euro)
  }

  it should "do subtraction" in {
    x - y should be(3.euro)
  }

  it should "do multiplication" in {
    x * 2 should be(10.euro)
  }

  it should "do division" in {
    x / 2 should be("2.50".euro)
  }

  it should "round to cents" in {
    val a = "123.0099".euro
    a.afgerondOpCenten should be("123.0100".euro)
  }

  it should "round up to cents" in {
    val a = "123.015".euro
    a.afgerondOpCenten should be("123.0200".euro)
  }

  it should "round down to cents" in {
    val a = "123.025".euro
    a.afgerondOpCenten should be("123.0200".euro)
  }

  it should "truncate to cents" in {
    val a = "123.0099".euro
    a.afgekaptOpCenten should be("123.0000".euro)
  }

  it should "truncate to whole euros" in {
    val a = "123.9999".euro
    a.afgekaptOpEuros should be("123.0000".euro)
  }

  it should "truncate to round hundreds of euros" in {
    val a = "1099.9999".euro
    a.afgekaptOp100Euro should be("1000.0000".euro)
  }

  it should "restore the scale after rounding" in {
    val a = "1099.9999".euro
    val expected = a.waarde.scale
    val actual = a.afgekaptOp100Euro.waarde.scale
    actual should be(expected)
  }

  it should "toString" in {
    10.euro.toString should be("€ 10,00")
  }

  it should "correctly pretty-print after truncating to hundreds of euros" in {
    200.euro.afgekaptOp100Euro.toString should be("€ 200,00")
  }

  it should "work with BigDecimals" in {
    x.waarde.getClass should be(classOf[BigDecimal])
  }

}

class BedragImplicitsTest extends FlatSpec with Matchers {

  val x = 5.euro
  val y = 2.euro

  it should "construct a Bedrag from an Int with 'euro'" in {
    5.euro should be(x)
  }

  it should "construct a Bedrag from a BigDecimal with 'euro'" in {
    BigDecimal(5).euro should be(x)
  }

  it should "construct a Bedrag from a String with 'euro'" in {
    "5".euro should be(x)
  }

  it should "not compile when constructing a Bedrag from a Double with 'euro'" in {
    "5.0.euro" shouldNot compile
  }

  it should "not compile when constructing a Bedrag from a Float with 'euro'" in {
    "5.0f.euro" shouldNot compile
  }

  it should "do commutative multiplication with Int" in {
    val x = 10
    val a = 2.euro
    (x * a) should be(a * x)
  }

  it should "do commutative multiplication with BigDecimal" in {
    val x = BigDecimal(10)
    val a = 2.euro
    (x * a) should be(a * x)
  }

  it should "not compile when multiplying a String with a Bedrag" in {
    val x = "10"
    val a = 2.euro
    "(x * a)" shouldNot compile
  }

  it should "have an ordering" in {
    x > y should be(true)
  }

}

class NumericBedragTest extends FlatSpec with Matchers {

  it should "do addition" in {
    NumericBedrag.plus(2.euro, 1.euro) should be (3.euro)
  }

  it should "do subtraction" in {
    NumericBedrag.minus(2.euro, 1.euro) should be (1.euro)
  }

  it should "NOT do multiplication" in {
    intercept[UnsupportedOperationException] {
      NumericBedrag.times(2.euro, 1.euro)
    }
  }

  it should "do negation" in {
    NumericBedrag.negate(3.euro) should be ((-3).euro)
  }

  it should "convert from Int" in {
    NumericBedrag.fromInt(3) should be (3.euro)
  }

  it should "NOT convert to Int" in {
    intercept[UnsupportedOperationException] {
      NumericBedrag.toInt(1.euro)
    }
  }

  it should "NOT convert to Long" in {
    intercept[UnsupportedOperationException] {
      NumericBedrag.toLong(1.euro)
    }
  }

  it should "NOT convert to Float" in {
    intercept[UnsupportedOperationException] {
      NumericBedrag.toFloat(1.euro)
    }
  }

  it should "NOT convert to Double" in {
    intercept[UnsupportedOperationException] {
      NumericBedrag.toDouble(1.euro)
    }
  }

  it should "compare" in {
    NumericBedrag.compare(1.euro, 2.euro) should be < 0
  }

  it should "sum a list of Bedragen" in {
    val bedragen = List(1.euro, 2.euro, 3.euro)
    bedragen.sum should be (6.euro)
  }

}
