package org.scalarules.dsl.core.grammar

import org.scalarules.dsl.nl.finance.{Bedrag, Percentage}
import org.scalarules.engine.SingularFact
import org.scalatest.{FlatSpec, Matchers}

class DslEvaluationTest extends FlatSpec with Matchers {

  val sutBedrag = new SingularFact[Bedrag]("testFactBedrag")
  val sutBigDecimal = new SingularFact[BigDecimal]("testFactBigDecimal")
  val sutString = new SingularFact[String]("testFactString")
  val sutPercentage = new SingularFact[Percentage]("testFactPercentage")

  it should "compile" in {
    -sutBedrag
    sutBedrag + sutBedrag
    sutBedrag - sutBedrag
    sutBedrag / sutBedrag
    sutBedrag / sutBigDecimal
    sutBedrag * sutBigDecimal
    -sutBigDecimal
    sutBigDecimal + sutBigDecimal
    sutBigDecimal - sutBigDecimal
    sutBigDecimal * sutBedrag
    sutBigDecimal / sutBigDecimal
    sutBigDecimal * sutBigDecimal
    sutBigDecimal * sutPercentage
    sutPercentage * sutBigDecimal
    sutBedrag * sutPercentage
    sutPercentage * sutBedrag
  }

  it should "not compile" in {
    "-sutString" shouldNot compile
    "sutBedrag + sutString" shouldNot compile
    "sutBedrag + sutBigDecimal" shouldNot compile
    "sutBedrag - sutBigDecimal" shouldNot compile
    "sutBedrag * sutBedrag" shouldNot compile
    "sutBigDecimal + sutBedrag" shouldNot compile
    "sutBigDecimal - sutBedrag" shouldNot compile
    "-sutPercentage" shouldNot compile
    "sutPercentage + sutPercentage" shouldNot compile
    "sutPercentage - sutPercentage" shouldNot compile
  }
}
