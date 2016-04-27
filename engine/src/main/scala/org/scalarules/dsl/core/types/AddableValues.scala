package org.scalarules.dsl.core.types

import scala.annotation.implicitNotFound

/**
  * This type class allows values of different types to be added in the DSL.
  *
  * @tparam A type of the left hand side of the adding multiply
  * @tparam B type of the right hand side of the adding multiply
  * @tparam C type of the result of the adding multiply
  */
@implicitNotFound("No member of type class Addable available in scope for combination ${A} + ${B} = ${C}")
sealed trait AddableValues[A, B, C] {
  def plus(a: A, b: B): C

  def leftUnit: A
  def rightUnit: B
}

object AddableValues {
  implicit def bigDecimalAddedToBigDecimal: AddableValues[BigDecimal, BigDecimal, BigDecimal] = new AddableValues[BigDecimal, BigDecimal, BigDecimal] {
    override def plus(a: BigDecimal, b: BigDecimal): BigDecimal = a + b
    override def leftUnit: BigDecimal = 0
    override def rightUnit: BigDecimal = 0
  }
  implicit def intAddedToInt: AddableValues[Int, Int, Int] = new AddableValues[Int, Int, Int] {
    override def plus(a: Int, b: Int): Int = a + b
    override def leftUnit: Int = 0
    override def rightUnit: Int = 0
  }
  implicit def numberLikeAddedToNumberLike[N : NumberLike]: AddableValues[N, N, N] = new AddableValues[N, N, N] {
    private val ev = implicitly[NumberLike[N]]
    override def plus(a: N, b: N): N = ev.plus(a, b)
    override def leftUnit: N = ev.zero
    override def rightUnit: N = ev.zero
  }
}
