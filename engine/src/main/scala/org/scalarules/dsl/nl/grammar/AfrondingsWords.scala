package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.nl.grammar.DslCondition._
import org.scalarules.engine._
import org.scalarules.finance.nl.Percentage

import scala.language.{implicitConversions, postfixOps}
import scala.math.BigDecimal.RoundingMode.RoundingMode

object AfrondingsWords {

  /**
    * a "filler" value required to enforce proper Dsl readability
    */
  val afgerond = new Afgerond

  implicit class AfrondingsTerm(afTeRondenPercentage: Fact[Percentage]) {

    /**
      * initiates a dsl mathematical rounding sequence: half-even
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def halfNaarEven(afgerond: Afgerond): AfrondingOpWord = new AfrondingOpWord(afTeRondenPercentage, BigDecimal.RoundingMode.HALF_EVEN)

    /**
      * initiates a dsl mathematical rounding sequence: half-down
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def halfNaarNulToe(afgerond: Afgerond): AfrondingOpWord = new AfrondingOpWord(afTeRondenPercentage, BigDecimal.RoundingMode.HALF_DOWN)

    /**
      * initiates a dsl mathematical rounding sequence: floor
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def naarBeneden(afgerond: Afgerond): AfrondingOpWord = new AfrondingOpWord(afTeRondenPercentage, BigDecimal.RoundingMode.FLOOR)

    /**
      * initiates a dsl mathematical rounding sequence: ceiling
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def naarBoven(afgerond: Afgerond): AfrondingOpWord = new AfrondingOpWord(afTeRondenPercentage, BigDecimal.RoundingMode.CEILING)

    /**
      * initiates a dsl mathematical rounding sequence: down
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def naarNulToe(afgerond: Afgerond): AfrondingOpWord = new AfrondingOpWord(afTeRondenPercentage, BigDecimal.RoundingMode.DOWN)

    /**
      * initiates a dsl mathematical rounding sequence: half-up
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def rekenkundig(afgerond: Afgerond): AfrondingOpWord = new AfrondingOpWord(afTeRondenPercentage, BigDecimal.RoundingMode.HALF_UP)

    /**
      * initiates a dsl mathematical rounding sequence: up
      * @param afgerond obligatory word-val to enable a natural language expression of rounding
      * @return AfrondingOpWord
      */
    def vanNulAf(afgerond: Afgerond): AfrondingOpWord = new AfrondingOpWord(afTeRondenPercentage, BigDecimal.RoundingMode.UP)
  }

  class AfrondingOpWord(afTeRondenPercentage: Fact[Percentage], roundingMode: RoundingMode) {

    /**
      * allows the dsl rounding to take an integer as its parameter for the amount of digits to be rounded to
      * @param aantalDecimalen integer representing the number of digits to be rounded to
      * @return Afronding
      */
    def op(aantalDecimalen: Integer): Afronding = new Afronding(afTeRondenPercentage, aantalDecimalen, roundingMode)

  }

  class Afronding(afTeRondenPercentage: Fact[Percentage], aantalDecimalen: Integer, roundingMode: RoundingMode) {
    /**
      * We could have ended the call with "op" (where we have all the information),
      * but for natural language purposes and later flexibility, were are enforcing "decimalen" as closing statement.
      * @return DslEvaluation[Percentage]
      */
    def decimalen: DslEvaluation[Percentage] = {
      DslEvaluation(factFilledCondition(afTeRondenPercentage), new Evaluation[Percentage] {
        override def apply(c: Context): Option[Percentage] = {
          Some(afTeRondenPercentage.toEval(c).get.afgerondOp(aantalDecimalen, roundingMode))
        }
      })
    }
  }

  sealed class Afgerond
}
