package org.scalarules.dsl.nl.grammar

import org.scalacheck.Gen
import org.scalacheck.Prop.{all, forAll}
import org.scalarules.dsl.nl.grammar.AfrondingsTestBerekeningGlossary._
import org.scalarules.engine._
import org.scalarules.finance.nl._
import org.scalarules.utils.InternalTestUtils.runAndExtractFact
import org.scalatest.{FlatSpecLike, PropSpec}
import org.scalatest.prop.Checkers

import scala.language.postfixOps

class AfrondingsWordsTest extends PropSpec with Checkers {

  property("Percentage: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN).procent )

      }
    )
  }

  property("Percentage: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN).procent )

      }
    )
  }

  property("Percentage: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN).procent )

      }
    )
  }

  property("Percentage: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN).procent )

      }
    )
  }

  property("Percentage: naarBeneden should match BigDecimal.RoundingMode.FLOOR for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR).procent )

      }
    )
  }

  property("Percentage: naarBeneden should match BigDecimal.RoundingMode.FLOOR for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR).procent )

      }
    )
  }

  property("Percentage: naarBoven should match BigDecimal.RoundingMode.CEILING for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING).procent )

      }
    )
  }

  property("Percentage: naarBoven should match BigDecimal.RoundingMode.CEILING for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING).procent )

      }
    )
  }

  property("Percentage: naarNulToe should match BigDecimal.RoundingMode.DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN).procent )

      }
    )
  }

  property("Percentage: naarNulToe should match BigDecimal.RoundingMode.DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN).procent )

      }
    )
  }

  property("Percentage: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP).procent )

      }
    )
  }

  property("Percentage: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP).procent )

      }
    )
  }

  property("Percentage: vanNulAf should match BigDecimal.RoundingMode.UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP).procent )

      }
    )
  }

  property("Percentage: vanNulAf should match BigDecimal.RoundingMode.UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondPercentageVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP).procent )

      }
    )
  }

  property("Bedrag: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN).euro )

      }
    )
  }

  property("Bedrag: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN).euro )

      }
    )
  }

  property("Bedrag: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN).euro )

      }
    )
  }

  property("Bedrag: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN).euro )

      }
    )
  }

  property("Bedrag: naarBeneden should match BigDecimal.RoundingMode.FLOOR for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR).euro )

      }
    )
  }

  property("Bedrag: naarBeneden should match BigDecimal.RoundingMode.FLOOR for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR).euro )

      }
    )
  }

  property("Bedrag: naarBoven should match BigDecimal.RoundingMode.CEILING for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING).euro )

      }
    )
  }

  property("Bedrag: naarBoven should match BigDecimal.RoundingMode.CEILING for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING).euro )

      }
    )
  }

  property("Bedrag: naarNulToe should match BigDecimal.RoundingMode.DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN).euro )

      }
    )
  }

  property("Bedrag: naarNulToe should match BigDecimal.RoundingMode.DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN).euro )

      }
    )
  }

  property("Bedrag: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP).euro )

      }
    )
  }

  property("Bedrag: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP).euro )

      }
    )
  }

  property("Bedrag: vanNulAf should match BigDecimal.RoundingMode.UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP).euro )

      }
    )
  }

  property("Bedrag: vanNulAf should match BigDecimal.RoundingMode.UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBedragVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP).euro )

      }
    )
  }

}
