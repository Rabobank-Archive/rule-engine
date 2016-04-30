package org.scalarules.dsl.core.types

import org.scalarules.finance.core.NumberLike
import org.scalarules.finance.nl._

import scala.annotation.implicitNotFound

/**
  * This type class allows values of different types to be added in the DSL.
  *
  * @tparam A type of the left hand side of the adding multiply
  * @tparam B type of the right hand side of the adding multiply
  * @tparam C type of the result of the adding multiply
  */
@implicitNotFound("No member of type class Addable available in scope for combination ${A} * ${B} = ${C}")
sealed trait MultipliableValues[A, B, C] {
  def multiply(a: A, b: B): C

  def leftUnit: A
  def rightUnit: B
}

object MultipliableValues {
  implicit def bigDecimalTimesBigDecimal: MultipliableValues[BigDecimal, BigDecimal, BigDecimal] = new MultipliableValues[BigDecimal, BigDecimal, BigDecimal] {
    override def multiply(a: BigDecimal, b: BigDecimal): BigDecimal = a * b
    override def leftUnit: BigDecimal = 0
    override def rightUnit: BigDecimal = 0
  }
  implicit def somethingTimesBigDecimal[N : NumberLike]: MultipliableValues[N, BigDecimal, N] = new MultipliableValues[N, BigDecimal, N] {
    private val ev = implicitly[NumberLike[N]]
    override def multiply(a: N, b: BigDecimal): N = ev.multiply(a, b)
    override def leftUnit: N = ev.zero
    override def rightUnit: BigDecimal = 0
  }
  implicit def bigDecimalTimesSomething[N : NumberLike]: MultipliableValues[BigDecimal, N, N] = new MultipliableValues[BigDecimal, N, N] {
    private val ev = implicitly[NumberLike[N]]
    override def multiply(a: BigDecimal, b: N): N = ev.multiply(b, a)
    override def leftUnit: BigDecimal = 0
    override def rightUnit: N = ev.zero
  }
  implicit def numberLikeTimesPercentage[N : NumberLike]: MultipliableValues[N, Percentage, N] = new MultipliableValues[N, Percentage, N] {
    private val ev = implicitly[NumberLike[N]]
    override def multiply(a: N, b: Percentage): N = b * a
    override def leftUnit: N = ev.zero
    override def rightUnit: Percentage = 0.procent
  }
  implicit def percentageTimesNumberLike[N : NumberLike]: MultipliableValues[Percentage, N, N] = new MultipliableValues[Percentage, N, N] {
    private val ev = implicitly[NumberLike[N]]
    override def multiply(a: Percentage, b: N): N = a * b
    override def leftUnit: Percentage = 0.procent
    override def rightUnit: N = ev.zero
  }
  implicit def bedragTimesPeriode: MultipliableValues[Bedrag, Periode, Bedrag] = new MultipliableValues[Bedrag, Periode, Bedrag] {
    override def multiply(a: Bedrag, b: Periode): Bedrag = a * b.inMaanden
    override def leftUnit: Bedrag = 0.euro
    override def rightUnit: Periode = 0.maanden
  }
}
