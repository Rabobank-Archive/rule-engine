package org.scalarules.finance.nl

import org.scalatest.{FlatSpec, Matchers}

class PerTest extends FlatSpec with Matchers {

  it should "do arithmetic when Termijn is not specified" in {
    val a: Bedrag Per Termijn = 10.euro per Maand
    val b: Bedrag Per Termijn = 2.euro per Maand
    a + b should be(12.euro per Maand)
  }

  it should "do addition on a number-like value" in {
    val a = 1 per Jaar
    val b = 2 per Jaar
    a + b should be(3 per Jaar)
  }

  it should "throw an exception when Termijnen don't match on addition" in {
    val a: Bedrag Per Termijn = 10.euro per Maand
    val b: Bedrag Per Termijn = 2.euro per Jaar
    intercept[IllegalArgumentException] { a + b }
  }

  it should "do subtraction on a number-like value" in {
    val a = 3.euro per Maand
    val b = 2.euro per Maand
    a - b should be(Bedrag(1) per Maand)
  }

  it should "throw an exception when Termijnen don't match on subtraction" in {
    val a: Bedrag Per Termijn = 10.euro per Maand
    val b: Bedrag Per Termijn = 2.euro per Jaar
    intercept[IllegalArgumentException] { a - b }
  }

  it should "do multiplication on a number-like value" in {
    val a = 2 per Halfjaar
    val b = 3
    a * b should be(6 per Halfjaar)
  }

  it should "do division on a number-like value" in {
    val a = 6.euro per Jaar
    val b = 3
    a / b should be(2.euro per Jaar)
  }

  it should "not do arithmetic on a non-number" in {
    val a = "hello" per Maand
    val b = "world" per Maand
    "a + b" shouldNot typeCheck
  }

  it should "not mix Periodes" in {
    "val x: BigDecimal Per Jaar = 10 per Jaar" should compile
    "val x: BigDecimal Per Jaar = 10 per Maand" shouldNot typeCheck
  }

  it should "not mix specified and non-specified Periodes" in {
    "val x: BigDecimal Per Jaar = 10" shouldNot typeCheck
    "val x: BigDecimal = 10 per Jaar" shouldNot typeCheck
  }

  it should "be convertable from Per Maand to Per Maand" in {
    (1 per Maand).maandelijks should be(1 per Maand)
    (3 per Maand).maandelijks should be(3 per Maand)
  }

  it should "be convertable from Per Kwartaal to Per Maand" in {
    (3 per Kwartaal).maandelijks should be(1 per Maand)
    (7 per Kwartaal).maandelijks should be((BigDecimal(7) / BigDecimal(3)) per Maand)
  }

  it should "be convertable from Per Halfjaar to Per Maand" in {
    (12 per Halfjaar).maandelijks should be(2 per Maand)
    (9 per Halfjaar).maandelijks should be(BigDecimal("1.5") per Maand)
  }

  it should "be convertable from Per Jaar to Per Maand" in {
    (12 per Jaar).maandelijks should be(1 per Maand)
    (13 per Jaar).maandelijks should be((BigDecimal(13) / BigDecimal(12)) per Maand)
  }

  it should "be convertable from Per Maand to Per Jaar" in {
    (2 per Maand).jaarlijks should be(24 per Jaar)
  }

  it should "be convertable from Per Kwartaal to Per Jaar" in {
    (3 per Kwartaal).jaarlijks should be(12 per Jaar)
  }

  it should "be convertable from Per Halfjaar to Per Jaar" in {
    (11 per Halfjaar).jaarlijks should be(22 per Jaar)
  }

  it should "be convertable from Per Jaar to Per Jaar" in {
    (24 per Jaar).jaarlijks should be(24 per Jaar)
  }

  it should "map over its contents, retaining the same type" in {
    (1 per Jaar) map (_ + 2) should be(3 per Jaar)
    ("123.456".euro per Maand) map (_.afgekaptOpEuros) should be("123".euro per Maand)
  }

  it should "map over its contents, changing to a different type" in {
    (1 per Maand) map (_.toString()) should be("1" per Maand)
    (10.procent per Jaar) map (_ * 10.euro) should be(1.euro per Jaar)
  }

  it should "flatMap over its contents, retaining the same type" in {
    (1 per Jaar) flatMap (_ + 2 per Jaar) should be(3 per Jaar)
    ("123.456".euro per Maand) flatMap (_.afgekaptOpEuros per Maand) should be("123".euro per Maand)
  }

  it should "flatMap over its contents, changing to a different type" in {
    (1 per Maand) flatMap (_.toString per Maand) should be("1" per Maand)
    (10.procent per Jaar) flatMap (_ * 10.euro per Jaar) should be(1.euro per Jaar)
  }

  it should "be possible to create a for comprehension using Pers" in {
    val xpm = 10 per Maand
    val ypm = 12 per Maand
    val result = for {
      x <- xpm
      y <- ypm
    } yield x + y
    result should be(22 per Maand)
  }

  it should "toString" in {
    (1 per Maand).toString should be(s"1 per ${Maand.toString}")
  }

}

class PerImplicitsTest extends FlatSpec with Matchers {

  it should "work with boolean operators from Ordered trait" in {
    val a = Bedrag(10) per Kwartaal
    val b = Bedrag(2) per Kwartaal
    a > b should be(true)
    a < b should be(false)
  }

  it should "work with boolean operators from Ordered trait on types that don't directly extend Ordering" in {
    val a = 10 per Jaar
    val b = 2 per Jaar
    a > b should be(true)
    a < b should be(false)
  }

  it should "be Orderable for BigDecimal" in {
    val list = List(3 per Kwartaal, 2 per Kwartaal, 4 per Kwartaal)
    list.sorted should be(List(2 per Kwartaal, 3 per Kwartaal, 4 per Kwartaal))
  }

  it should "be Numeric for BigDecimal" in {
    val list = List(3 per Kwartaal, 2 per Kwartaal, 4 per Kwartaal)
    list.sum should be(9 per Kwartaal)
  }

  it should "be Orderable for Bedrag" in {
    val list = List(3.euro per Maand, 2.euro per Maand, 4.euro per Maand)
    list.sorted should be(List(2.euro per Maand, 3.euro per Maand, 4.euro per Maand))
  }

  it should "be Numeric for Bedrag" in {
    val list = List(3.euro per Maand, 2.euro per Maand, 4.euro per Maand)
    list.sum should be(9.euro per Maand)
  }

  it should "work with boolean operators from Ordered trait when Termijn is not specified" in {
    val a: Bedrag Per Termijn = 10.euro per Maand
    val b: Bedrag Per Termijn = 2.euro per Maand
    a > b should be(true)
  }

  it should "throw an exception when Termijnen don't match on boolean operations" in {
    val a: Bedrag Per Termijn = 10.euro per Maand
    val b: Bedrag Per Termijn = 2.euro per Jaar
    intercept[IllegalArgumentException] {
      a > b
    }
  }

}

class NumericPerPeriodeTest extends FlatSpec with Matchers {

  val evPeriode = implicitly[Numeric[BigDecimal Per Halfjaar]]
  val xp = 8 per Halfjaar
  val yp = 3 per Halfjaar

  it should "add" in {
    evPeriode.plus(xp, yp) should be(11 per Halfjaar)
  }
  it should "subtract" in {
    evPeriode.minus(xp, yp) should be(5 per Halfjaar)
  }
  it should "refuse to multiply because of the resulting unit" in {
    intercept[IllegalStateException] {
    evPeriode.times(xp, yp)
  }
  }
  it should "negate" in {
    evPeriode.negate(xp) should be(-8 per Halfjaar)
  }
  it should "convert from Int" in {
    evPeriode.fromInt(5) should be(5 per Halfjaar)
  }
  it should "convert to Int" in {
    evPeriode.toInt(xp) should be(8)
  }
  it should "convert to Long" in {
    evPeriode.toLong(xp) should be(8L)
  }
  it should "convert to Float" in {
    evPeriode.toFloat(xp) should be(8.0F)
  }
  it should "convert to Double" in {
    evPeriode.toDouble(xp) should be(8.0D)
  }
  it should "compare" in {
    evPeriode.compare(xp, yp) should be > 0
  }

}


class NumericPerTermijnTest extends FlatSpec with Matchers {

  val evTermijn = implicitly[Numeric[BigDecimal Per Termijn]]
  val xt: BigDecimal Per Termijn = 8 per Halfjaar
  val yt: BigDecimal Per Termijn = 3 per Halfjaar

  it should "add" in {
    evTermijn.plus(xt, yt) should be (11 per Halfjaar)
  }

  it should "subtract" in {
    evTermijn.minus(xt, yt) should be (5 per Halfjaar)
  }

  it should "refuse to multiply because of the resulting unit" in {
    intercept[IllegalStateException] {
      evTermijn.times(xt, yt)
    }
  }

  it should "negate" in {
    evTermijn.negate(xt) should be (-8 per Halfjaar)
  }

  it should "refuse to convert from Int because we don't know the Termijn" in {
    intercept[IllegalStateException] {
      evTermijn.fromInt(5)
    }
  }

  it should "convert to Int" in {
    evTermijn.toInt(xt) should be (8)
  }

  it should "convert to Long" in {
    evTermijn.toLong(xt) should be (8L)
  }

  it should "convert to Float" in {
    evTermijn.toFloat(xt) should be (8.0F)
  }

  it should "convert to Double" in {
    evTermijn.toDouble(xt) should be (8.0D)
  }

  it should "compare" in {
    evTermijn.compare(xt, yt) should be > 0
  }

}
