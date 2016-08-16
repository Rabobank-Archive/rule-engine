package org.scalarules.dsl.core.types

import org.scalarules.finance.core.Quantity
import org.scalarules.finance.nl._

import scala.annotation.implicitNotFound

/**
  * This type class allows values of different types to be added in the DSL.
  *
  * @tparam A type of the left hand side of the adding multiply
  * @tparam B type of the right hand side of the adding multiply
  * @tparam C type of the result of the adding multiply
  */
@implicitNotFound("No member of type class DivisibleValues available in scope for combination ${A} / ${B} = ${C}")
trait DivisibleValues[A, B, C] {
  def divide(a: A, b: B): C

  def leftUnit: A
  def rightUnit: B
}

object DivisibleValues {
  implicit def bigDecimalDividedByBigDecimal: DivisibleValues[BigDecimal, BigDecimal, BigDecimal] = new DivisibleValues[BigDecimal, BigDecimal, BigDecimal] {
    override def divide(a: BigDecimal, b: BigDecimal): BigDecimal = a / b
    override def leftUnit: BigDecimal = 0
    override def rightUnit: BigDecimal = 1
  }

  implicit def somethingDividedByBigDecimal[N : Quantity]: DivisibleValues[N, BigDecimal, N] = new DivisibleValues[N, BigDecimal, N] {
    private val ev = implicitly[Quantity[N]]
    override def divide(a: N, b: BigDecimal): N = ev.divide(a, b)
    override def leftUnit: N = ev.zero
    override def rightUnit: BigDecimal = 1
  }

  implicit def somethingDividedByPercentage[N : Quantity]: DivisibleValues[N, Percentage, N] = new DivisibleValues[N, Percentage, N] {
    private val ev = implicitly[Quantity[N]]
    override def divide(a: N, b: Percentage): N = ev.divide(a, b.alsFractie)
    override def leftUnit: N = ev.zero
    override def rightUnit: Percentage = 1.procent
  }

  implicit def somethingDividedByInt[N : Quantity]: DivisibleValues[N, Int, N] = new DivisibleValues[N, Int, N] {
    // Currently BigDecimal gets wrapped in a NumberLike, which is why this will also work for BigDecimal.
    private val ev = implicitly[Quantity[N]]
    override def divide(a: N, b: Int): N = ev.divide(a, b)
    override def leftUnit: N = ev.zero
    override def rightUnit: Int = 1
  }

  implicit def percentageDividedByBigDecimal: DivisibleValues[Percentage, BigDecimal, BigDecimal] = new DivisibleValues[Percentage, BigDecimal, BigDecimal] {
    override def divide(a: Percentage, b: BigDecimal): BigDecimal = a / b
    override def leftUnit: Percentage = 0.procent
    override def rightUnit: BigDecimal = 1
  }

  implicit def somethingDividedBySomethingAsPercentage: DivisibleValues[Bedrag, Bedrag, Percentage] = new DivisibleValues[Bedrag, Bedrag, Percentage] {
    // Note: this type class instance was initially as Quantity x Quantity => Percentage, but the QuantityBigDecimal throws a fit if we do that
    // and makes it ambiguous to choose when trying to divide two BigDecimals
    //    private val ev = implicitly[Quantity[Bedrag]]
    override def divide(a: Bedrag, b: Bedrag): Percentage = (a.waarde / b.waarde * 100).procent
    override def leftUnit: Bedrag = 0.euro
    override def rightUnit: Bedrag = 1.euro
  }

  // Note: there is no somethingDividedByBigDecimal, because the division is not a commutative operation
  // and dividing a BigDecimal by something like a Bedrag would yield a BigDecimal Per Bedrag type, which
  // I do not yet foresee any use for.
}
