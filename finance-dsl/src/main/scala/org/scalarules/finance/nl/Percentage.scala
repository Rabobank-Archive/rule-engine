package org.scalarules.finance.nl

import org.scalarules.finance.core.Quantity

// scalastyle:off method.name

/**
 * Representeert een percentage.
 *
 * Een `percentage` moet tussen 0.0 en 100.0 liggen.
 */
case class Percentage private[finance] (percentage: BigDecimal) extends Ordered[Percentage] {
  require (percentage >= 0)
  require (percentage <= 100)

  /** Het percentage als een fractie tussen 0 en 1. */
  val alsFractie: BigDecimal = percentage / 100

  /** Returnt de som van dit percentage en p, als Percentage. */
  def + (p: Percentage): Percentage = Percentage(percentage + p.percentage)

  /** Returnt het verschil tussen dit percentage en p, als Percentage. */
  def - (p: Percentage): Percentage = Percentage(percentage - p.percentage)

  /** Returnt het percentage van p, als BigDecimal factor. */
  def * (p: Percentage): BigDecimal = alsFractie * p.alsFractie

  /** Returnt het percentage van n, als type T. */
  def *[T : Quantity](n: T): T = implicitly[Quantity[T]].multiply(n, alsFractie)

  /** Returnt het percentage van n, als BigDecimal factor zodat het geen precisie verliest. */
  def * (n: Int): BigDecimal = alsFractie * n

  /** Returnt het quotiënt van het percentage en p, als BigDecimal factor. */
  def / (p: Percentage): BigDecimal = alsFractie / p.alsFractie

  /** Returnt het quotiënt van het percentage en n, als BigDecimal factor. */
  def / (n: BigDecimal): BigDecimal = alsFractie / n

  override def compare(that: Percentage) = percentage compare that.percentage

  override def toString = s"$percentage%"
}

trait PercentageImplicits {
  /*
   * The 'procent' method in several of the following implicit classes can't be called '%',
   * because most numeric types already have a % method (meaning 'modulo'). The compiler keeps
   * expecting a parameter which isn't there, because it searches the real method before it
   * searches implicits.
   */

  abstract class ToPercentage(waarde: BigDecimal) {
    /** Maakt een `Percentage`. */
    def procent: Percentage = Percentage(waarde)
  }

  implicit class BigDecimalToPercentage(waarde: BigDecimal) extends ToPercentage(waarde)
  implicit class IntToPercentage(waarde: Int) extends ToPercentage(waarde)
  implicit class StringToPercentage(waarde: String) extends ToPercentage(BigDecimal(waarde))

  implicit class IntWithPercentage(waarde: Int) {
    /** Returnt het product van deze Int en Percentage n als BigDecimal. */
    def * (p: Percentage): BigDecimal = p * BigDecimal(waarde)
  }
  implicit class QuantityWithPercentage[T : Quantity](waarde: T) {
    /** Returnt het product van deze Quantity T en Percentage n als T. */
    def * (p: Percentage): T = p * waarde
  }
}
