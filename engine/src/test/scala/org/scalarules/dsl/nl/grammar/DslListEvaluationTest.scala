package org.scalarules.dsl.nl.grammar

import org.scalarules.finance.nl.{Percentage, Bedrag}
import org.scalarules.engine.{SingularFact, ListFact, Fact}
import org.scalatest.{FlatSpec, Matchers}

class DslListEvaluationTest extends FlatSpec with Matchers {

  val sutBedrag = new SingularFact[Bedrag]("testFactBedrag")
  val sutBedragen = new ListFact[Bedrag]("testFactBedragen")
  val sutBedragen2 = new ListFact[Bedrag]("testFactBedragen2")
  val sutBigDecimal = new SingularFact[BigDecimal]("testFactBigDecimal")
  val sutBigDecimals = new ListFact[BigDecimal]("testFactBigDecimals")
  val sutString = new SingularFact[String]("testFactString")
  val sutStrings = new ListFact[String]("testFactStrings")
  val sutPercentage = new SingularFact[Percentage]("testFactPercentage")
  val sutPercentages = new ListFact[Percentage]("testFactPercentages")

  it should "compile" in {
    -sutBedragen
    sutBedragen + sutBedragen
    sutBedragen - sutBedragen
    sutBedragen / sutBedragen

    sutBedragen + sutBedrag
    sutBedragen - sutBedrag
    sutBedragen / sutBedrag

    sutBedragen / sutBigDecimals
    sutBedragen * sutBigDecimals

    sutBedragen / sutBigDecimal
    sutBedragen * sutBigDecimal

    sutBedrag + sutBedragen
    sutBedrag - sutBedragen
    sutBedrag / sutBedragen

    sutBedrag / sutBigDecimals
    sutBedrag * sutBigDecimals

    -sutBigDecimals
    sutBigDecimals + sutBigDecimals
    sutBigDecimals - sutBigDecimals
    sutBigDecimals / sutBigDecimals
    sutBigDecimals * sutBigDecimals

    sutBigDecimals + sutBigDecimal
    sutBigDecimals - sutBigDecimal
    sutBigDecimals / sutBigDecimal
    sutBigDecimals * sutBigDecimal

    sutBigDecimals * sutBedragen

    sutBigDecimals * sutBedrag

    sutBigDecimal + sutBigDecimals
    sutBigDecimal - sutBigDecimals
    sutBigDecimal * sutBigDecimals
    sutBigDecimal / sutBigDecimals

    sutBigDecimal * sutBedragen
  }

  it should "not compile" in {
    "sutBedragen * sutBedragen" shouldNot compile
    "sutBedragen * sutBedrag" shouldNot compile
    "sutBedragen + sutBigDecimals" shouldNot compile
    "sutBedragen - sutBigDecimals" shouldNot compile
    "sutBedragen + sutBigDecimal" shouldNot compile
    "sutBedragen - sutBigDecimal" shouldNot compile
    "sutBedrag * sutBedragen" shouldNot compile
    "sutBedrag + sutBigDecimals" shouldNot compile
    "sutBedrag - sutBigDecimals" shouldNot compile
    "sutBigDecimals + sutBedragen" shouldNot compile
    "sutBigDecimals - sutBedragen" shouldNot compile
    "sutBigDecimals / sutBedragen" shouldNot compile
    "sutBigDecimals + sutBedrag" shouldNot compile
    "sutBigDecimals - sutBedrag" shouldNot compile
    "sutBigDecimal + sutBedragen" shouldNot compile
    "sutBigDecimal - sutBedragen" shouldNot compile
    "sutBigDecimals / sutBedrag" shouldNot compile
    "sutBigDecimal / sutBedragen" shouldNot compile
    "sutBedragen + sutString" shouldNot compile
    "sutBedragen + sutBigDecimal" shouldNot compile
    "sutBedragen - sutBigDecimal" shouldNot compile
    "sutBedragen * sutBedrag" shouldNot compile
    "sutBedragen + sutStrings" shouldNot compile
    "sutBedragen + sutBigDecimals" shouldNot compile
    "sutBedragen - sutBigDecimals" shouldNot compile
    "sutBedragen * sutBedragen" shouldNot compile
    "sutBigDecimals + sutBedrag" shouldNot compile
    "sutBigDecimals - sutBedrag" shouldNot compile
    "sutPercentages + sutPercentages" shouldNot compile
    "sutPercentages - sutPercentages" shouldNot compile
  }
}
