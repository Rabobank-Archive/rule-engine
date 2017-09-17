package nl.rabobank.rules.dsl.core.operators

import nl.rabobank.rules.dsl.core.types.DivisibleValues

import scala.annotation.implicitNotFound

/**
  * This type class allows values of different types to be added in the DSL.
  *
  * @tparam A type of the left hand side of the adding operation
  * @tparam B type of the right hand side of the adding operation
  * @tparam C type of the result of the adding operation
  */
@implicitNotFound("No member of type class Divisible available in scope for combination ${A} / ${B} = ${C}")
sealed trait Divisible[A, B, C] extends BinaryOperable[A, B, C] {
  def operation(a: A, b: B): C

  def identityLeft: A
  def identityRight: B

  def representation: String = "/"
}

object Divisible {
  implicit def valueDividedByValue[A, B, C](implicit ev: DivisibleValues[A, B, C]): Divisible[A, B, C] = new Divisible[A, B, C] {
    override def operation(n: A, m: B): C = ev.divide(n, m)
    override def identityLeft = ev.leftUnit
    override def identityRight = ev.rightUnit
  }
  implicit def listDividedByList[A, B, C](implicit ev: DivisibleValues[A, B, C]): Divisible[List[A], List[B], List[C]] = new Divisible[List[A], List[B], List[C]] {
    override def operation(n: List[A], m: List[B]): List[C] = n.zip(m).map(t => ev.divide(t._1, t._2))
    override def identityLeft = List(ev.leftUnit)
    override def identityRight = List(ev.rightUnit)
  }
  implicit def listDividedByValue[A, B, C](implicit ev: DivisibleValues[A, B, C]): Divisible[List[A], B, List[C]] = new Divisible[List[A], B, List[C]] {
    override def operation(n: List[A], m: B): List[C] = n.map( ev.divide(_, m) )
    override def identityLeft = List(ev.leftUnit)
    override def identityRight = ev.rightUnit
  }
  implicit def valueDividedByList[A, B, C](implicit ev: DivisibleValues[A, B, C]): Divisible[A, List[B], List[C]] = new Divisible[A, List[B], List[C]] {
    override def operation(n: A, m: List[B]): List[C] = m.map( ev.divide(n, _) )
    override def identityLeft = ev.leftUnit
    override def identityRight = List(ev.rightUnit)
  }
}
