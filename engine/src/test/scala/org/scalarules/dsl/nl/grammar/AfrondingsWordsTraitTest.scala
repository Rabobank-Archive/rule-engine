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

/**
  * There is a fair bit of duplication going on here, however, rounding issues can be very subtle yet quite destructive.
  * This way there is a fair bit of redundancy within the tests and a lot of numbers are run through the functions so
  * if any discrepancies exist between the DSL and the corresponding BigDecimal.setScale function, we will find them.
  */
class AfrondingsWordsTraitTest extends PropSpec with Checkers {

  var afrondingTestBerekeningDerivations: List[Derivation] = new AfrondingsTestBerekening().derivations

  /* BigDecimal property base tests*/
  property("BigDecimal: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN) )

      }
    )
  }

  property("BigDecimal: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN) )

      }
    )
  }

  property("BigDecimal: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN) )

      }
    )
  }

  property("BigDecimal: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN) )

      }
    )
  }

  property("BigDecimal: naarBeneden should match BigDecimal.RoundingMode.FLOOR for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR) )

      }
    )
  }

  property("BigDecimal: naarBeneden should match BigDecimal.RoundingMode.FLOOR for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR) )

      }
    )
  }

  property("BigDecimal: naarBoven should match BigDecimal.RoundingMode.CEILING for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING) )

      }
    )
  }

  property("BigDecimal: naarBoven should match BigDecimal.RoundingMode.CEILING for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING) )

      }
    )
  }

  property("BigDecimal: naarNulToe should match BigDecimal.RoundingMode.DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN) )

      }
    )
  }

  property("BigDecimal: naarNulToe should match BigDecimal.RoundingMode.DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN) )

      }
    )
  }

  property("BigDecimal: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP) )

      }
    )
  }

  property("BigDecimal: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP) )

      }
    )
  }

  property("BigDecimal: vanNulAf should match BigDecimal.RoundingMode.UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP) )

      }
    )
  }

  property("BigDecimal: vanNulAf should match BigDecimal.RoundingMode.UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBigDecimalVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP) )

      }
    )
  }


  /* Percentage property based test*/
  property("Percentage: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN).procent )

      }
    )
  }

  property("Percentage: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN).procent )

      }
    )
  }

  property("Percentage: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN).procent )

      }
    )
  }

  property("Percentage: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN).procent )

      }
    )
  }

  property("Percentage: naarBeneden should match BigDecimal.RoundingMode.FLOOR for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR).procent )

      }
    )
  }

  property("Percentage: naarBeneden should match BigDecimal.RoundingMode.FLOOR for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR).procent )

      }
    )
  }

  property("Percentage: naarBoven should match BigDecimal.RoundingMode.CEILING for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING).procent )

      }
    )
  }

  property("Percentage: naarBoven should match BigDecimal.RoundingMode.CEILING for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING).procent )

      }
    )
  }

  property("Percentage: naarNulToe should match BigDecimal.RoundingMode.DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN).procent )

      }
    )
  }

  property("Percentage: naarNulToe should match BigDecimal.RoundingMode.DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN).procent )

      }
    )
  }

  property("Percentage: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP).procent )

      }
    )
  }

  property("Percentage: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP).procent )

      }
    )
  }

  property("Percentage: vanNulAf should match BigDecimal.RoundingMode.UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP).procent )

      }
    )
  }

  property("Percentage: vanNulAf should match BigDecimal.RoundingMode.UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startPercentage -> BigDecimal(testGetal).procent,
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[Percentage] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondPercentageVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP).procent )

      }
    )
  }

  /* Bedrag property based tests*/
  property("Bedrag: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
          afrondingsType -> "halfNaarEven")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN).euro )

      }
    )
  }

  property("Bedrag: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN).euro )

      }
    )
  }

  property("Bedrag: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN).euro )

      }
    )
  }

  property("Bedrag: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN).euro )

      }
    )
  }

  property("Bedrag: naarBeneden should match BigDecimal.RoundingMode.FLOOR for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR).euro )

      }
    )
  }

  property("Bedrag: naarBeneden should match BigDecimal.RoundingMode.FLOOR for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR).euro )

      }
    )
  }

  property("Bedrag: naarBoven should match BigDecimal.RoundingMode.CEILING for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING).euro )

      }
    )
  }

  property("Bedrag: naarBoven should match BigDecimal.RoundingMode.CEILING for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING).euro )

      }
    )
  }

  property("Bedrag: naarNulToe should match BigDecimal.RoundingMode.DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN).euro )

      }
    )
  }

  property("Bedrag: naarNulToe should match BigDecimal.RoundingMode.DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN).euro )

      }
    )
  }

  property("Bedrag: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP).euro )

      }
    )
  }

  property("Bedrag: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP).euro )

      }
    )
  }

  property("Bedrag: vanNulAf should match BigDecimal.RoundingMode.UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP).euro )

      }
    )
  }

  property("Bedrag: vanNulAf should match BigDecimal.RoundingMode.UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBedrag -> BigDecimal(testGetal).euro,
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[Bedrag] = runAndExtractFact(context, afrondingTestBerekeningDerivations, afgerondBedragVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP).euro )

      }
    )
  }

}
