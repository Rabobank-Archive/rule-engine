package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.grammar.{DslCondition, DslEvaluation}
import org.scalarules.engine._
import org.scalarules.finance.nl.{Bedrag, Percentage}

import scala.language.{implicitConversions, postfixOps}
import scala.math.BigDecimal.RoundingMode.RoundingMode

trait AfrondingsWordsTrait {

  /**
    * a "filler" value required to enforce proper Dsl readability
    */
  val afgerond = new AfgerondKeyword

  implicit class AfrondingsTerm[T: Afrondbaar](afTeRonden: Fact[T]) {

    /**
      * initiates a dsl mathematical rounding sequence: half-even
      *
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def halfNaarEven(afgerond: AfgerondKeyword): AfrondingOpWord[T] = new AfrondingOpWord[T](afTeRonden, BigDecimal.RoundingMode.HALF_EVEN)

    /**
      * initiates a dsl mathematical rounding sequence: half-down
      *
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def halfNaarNulToe(afgerond: AfgerondKeyword): AfrondingOpWord[T] = new AfrondingOpWord[T](afTeRonden, BigDecimal.RoundingMode.HALF_DOWN)

    /**
      * initiates a dsl mathematical rounding sequence: floor
      *
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def naarBeneden(afgerond: AfgerondKeyword): AfrondingOpWord[T] = new AfrondingOpWord[T](afTeRonden, BigDecimal.RoundingMode.FLOOR)

    /**
      * initiates a dsl mathematical rounding sequence: ceiling
      *
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def naarBoven(afgerond: AfgerondKeyword): AfrondingOpWord[T] = new AfrondingOpWord[T](afTeRonden, BigDecimal.RoundingMode.CEILING)

    /**
      * initiates a dsl mathematical rounding sequence: down
      *
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def naarNulToe(afgerond: AfgerondKeyword): AfrondingOpWord[T] = new AfrondingOpWord[T](afTeRonden, BigDecimal.RoundingMode.DOWN)

    /**
      * initiates a dsl mathematical rounding sequence: half-up
      *
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def rekenkundig(afgerond: AfgerondKeyword): AfrondingOpWord[T] = new AfrondingOpWord[T](afTeRonden, BigDecimal.RoundingMode.HALF_UP)

    /**
      * initiates a dsl mathematical rounding sequence: up
      *
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def vanNulAf(afgerond: AfgerondKeyword): AfrondingOpWord[T] = new AfrondingOpWord[T](afTeRonden, BigDecimal.RoundingMode.UP)
  }

  class AfrondingOpWord[T: Afrondbaar](afTeRonden: Fact[T], roundingMode: RoundingMode) {

    /**
      * allows the dsl rounding to take an integer as its parameter for the amount of digits to be rounded to
      *
      * @param aantalDecimalen integer representing the number of digits to be rounded to
      * @return Afronding
      */
    def op(aantalDecimalen: Integer): Afronding[T] = new Afronding[T](afTeRonden, aantalDecimalen, roundingMode)

  }

  class Afronding[T: Afrondbaar](afTeRonden: Fact[T], aantalDecimalen: Integer, roundingMode: RoundingMode) {
    /**
      * We could have ended the call with "op" (where we have all the information),
      * but for natural language purposes and later flexibility, were are enforcing "decimalen" as closing statement.
      *
      * @return DslEvaluation[Percentage]
      */
    def decimalen: DslEvaluation[T] = {
      DslEvaluation(DslCondition.factFilledCondition(afTeRonden), new Evaluation[T] {
        override def apply(c: Context): Option[T] = {
          val ev = implicitly[Afrondbaar[T]]
          Some(ev.rondAfOp(afTeRonden.toEval(c).get, aantalDecimalen, roundingMode))
        }
      })
    }
  }

  sealed class AfgerondKeyword

  implicit def afrondbaarBigDecimal: Afrondbaar[BigDecimal] = {
    new Afrondbaar[BigDecimal] {
      override def rondAfOp(afTeRonden: BigDecimal, aantalDecimalen: Integer, afrondingsWijze: RoundingMode): BigDecimal =
        afTeRonden.setScale(aantalDecimalen, afrondingsWijze)
    }
  }

  implicit def afrondbaarPercentage: Afrondbaar[Percentage] = {
    new Afrondbaar[Percentage] {
      override def rondAfOp(afTeRonden: Percentage, aantalDecimalen: Integer, afrondingsWijze: RoundingMode): Percentage =
        afTeRonden.afgerondOp(aantalDecimalen, afrondingsWijze)
    }
  }

  implicit def afrondbaarBedrag: Afrondbaar[Bedrag] = {
    new Afrondbaar[Bedrag] {
      override def rondAfOp(afTeRonden: Bedrag, aantalDecimalen: Integer, afrondingsWijze: RoundingMode): Bedrag =
        afTeRonden.afgerondOp(aantalDecimalen, afrondingsWijze)
    }
  }
}

trait Afrondbaar[T] {
  def rondAfOp(afTeRonden: T, aantalDecimalen: Integer, afrondingsWijze: RoundingMode): T
}


