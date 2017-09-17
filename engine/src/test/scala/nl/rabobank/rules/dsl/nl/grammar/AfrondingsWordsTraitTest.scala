package nl.rabobank.rules.dsl.nl.grammar

import nl.rabobank.rules.dsl.nl.grammar.AfrondingsTestBerekeningGlossary._
import nl.rabobank.rules.engine._
import nl.rabobank.rules.finance.nl._
import nl.rabobank.rules.utils.InternalTestUtils.runAndExtractFact
import org.scalacheck.Gen
import org.scalacheck.Prop.{all, forAll}
import org.scalatest.PropSpec
import org.scalatest.prop.Checkers

import scala.language.postfixOps

/**
  * There is a fair bit of duplication going on here, however, rounding issues can be very subtle yet quite destructive.
  * This way there is a fair bit of redundancy within the tests and a lot of numbers are run through the functions so
  * if any discrepancies exist between the DSL and the corresponding BigDecimal.setScale function, we will find them.
  */
class AfrondingsWordsTraitTest extends PropSpec with Checkers {

  /* BigDecimal property base tests*/
  property("BigDecimal: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN) )

      }
    )
  }

  property("BigDecimal: halfNaarEven should match BigDecimal.RoundingMode.HALF_EVEN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "halfNaarEven")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalHalfEven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_EVEN) )

      }
    )
  }

  property("BigDecimal: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN) )

      }
    )
  }

  property("BigDecimal: halfNaarNulToe should match BigDecimal.RoundingMode.HALF_DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "halfNaarNulToe")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalHalfNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_DOWN) )

      }
    )
  }

  property("BigDecimal: naarBeneden should match BigDecimal.RoundingMode.FLOOR for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR) )

      }
    )
  }

  property("BigDecimal: naarBeneden should match BigDecimal.RoundingMode.FLOOR for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarBeneden")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalNaarBeneden)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.FLOOR) )

      }
    )
  }

  property("BigDecimal: naarBoven should match BigDecimal.RoundingMode.CEILING for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING) )

      }
    )
  }

  property("BigDecimal: naarBoven should match BigDecimal.RoundingMode.CEILING for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarBoven")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalNaarBoven)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.CEILING) )

      }
    )
  }

  property("BigDecimal: naarNulToe should match BigDecimal.RoundingMode.DOWN for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN) )

      }
    )
  }

  property("BigDecimal: naarNulToe should match BigDecimal.RoundingMode.DOWN for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "naarNulToe")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalNaarNulToe)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.DOWN) )

      }
    )
  }

  property("BigDecimal: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP) )

      }
    )
  }

  property("BigDecimal: rekenkundig should match BigDecimal.RoundingMode.HALF_UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "rekenkundig")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalRekenkundig)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.HALF_UP) )

      }
    )
  }

  property("BigDecimal: vanNulAf should match BigDecimal.RoundingMode.UP for positive doubles") {
    check(
      forAll (for {testGetal <- Gen.posNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalVanNulAf)

        all(uitkomst.isDefined, uitkomst.get == BigDecimal(testGetal).setScale(0, BigDecimal.RoundingMode.UP) )

      }
    )
  }

  property("BigDecimal: vanNulAf should match BigDecimal.RoundingMode.UP for negative doubles") {
    check(
      forAll (for {testGetal <- Gen.negNum[Double]} yield testGetal) { testGetal: Double =>

        val context: Context = Map(startBigDecimal -> BigDecimal(testGetal),
                                    afrondingsType -> "vanNulAf")

        val uitkomst: Option[BigDecimal] = runAndExtractFact(context, new AfrondingsTestBerekening().berekeningen, afgerondBigDecimalVanNulAf)

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

  /* Bedrag property based tests*/
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
