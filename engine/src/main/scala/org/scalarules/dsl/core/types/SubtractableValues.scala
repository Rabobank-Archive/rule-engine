package org.scalarules.dsl.core.types

import scala.annotation.implicitNotFound

/**
  * This type class allows values of different types to be added in the DSL.
  *
  * @tparam A type of the left hand side of the adding multiply
  * @tparam B type of the right hand side of the adding multiply
  * @tparam C type of the result of the adding multiply
  */
@implicitNotFound("No member of type class Subtractable available in scope for combination ${A} - ${B} = ${C}")
trait SubtractableValues[A, B, C] {
  def minus(a: A, b: B): C

  def leftUnit: A
  def rightUnit: B
}

object SubtractableValues {
  implicit def bigDecimalSubtractedByBigDecimal: SubtractableValues[BigDecimal, BigDecimal, BigDecimal] = new SubtractableValues[BigDecimal, BigDecimal, BigDecimal] {
    override def minus(a: BigDecimal, b: BigDecimal): BigDecimal = a - b
    override def leftUnit: BigDecimal = 0
    override def rightUnit: BigDecimal = 0
  }
  implicit def numberLikeSubtractedByNumberLike[N : NumberLike]: SubtractableValues[N, N, N] = new SubtractableValues[N, N, N] {
    private val ev = implicitly[NumberLike[N]]
    override def minus(a: N, b: N): N = ev.minus(a, b)
    override def leftUnit: N = ev.zero
    override def rightUnit: N = ev.zero
  }
}
