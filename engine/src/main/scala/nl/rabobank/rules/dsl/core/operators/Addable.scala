package nl.rabobank.rules.dsl.core.operators

import nl.rabobank.rules.dsl.core.types.AddableValues

import scala.annotation.implicitNotFound

/**
  * This type class allows values of different types to be added in the DSL.
  *
  * @tparam A type of the left hand side of the adding operation
  * @tparam B type of the right hand side of the adding operation
  * @tparam C type of the result of the adding operation
  */
@implicitNotFound("No member of type class Addable available in scope for combination ${A} + ${B} = ${C}")
sealed trait Addable[A, B, C] extends BinaryOperable[A, B, C] {
  def operation(a: A, b: B): C

  def identityLeft: A
  def identityRight: B

  def representation: String = "+"
}

object Addable {
  implicit def valueAddedToValue[A, B, C](implicit ev: AddableValues[A, B, C]): Addable[A, B, C] = new Addable[A, B, C] {
    override def operation(n: A, m: B): C = ev.plus(n, m)
    override def identityLeft = ev.leftUnit
    override def identityRight = ev.rightUnit
  }
  implicit def listAddedToList[A, B, C](implicit ev: AddableValues[A, B, C]): Addable[List[A], List[B], List[C]] = new Addable[List[A], List[B], List[C]] {
    override def operation(n: List[A], m: List[B]): List[C] = n.zipAll(m, ev.leftUnit, ev.rightUnit).map(t => ev.plus(t._1, t._2))
    override def identityLeft = List(ev.leftUnit)
    override def identityRight = List(ev.rightUnit)
  }
  implicit def listAddedToValue[A, B, C](implicit ev: AddableValues[A, B, C]): Addable[List[A], B, List[C]] = new Addable[List[A], B, List[C]] {
    override def operation(n: List[A], m: B): List[C] = n.map( ev.plus(_, m) )
    override def identityLeft = List(ev.leftUnit)
    override def identityRight = ev.rightUnit
  }
  implicit def valueAddedToList[A, B, C](implicit ev: AddableValues[A, B, C]): Addable[A, List[B], List[C]] = new Addable[A, List[B], List[C]] {
    override def operation(n: A, m: List[B]): List[C] = m.map( ev.plus(n, _) )
    override def identityLeft = ev.leftUnit
    override def identityRight = List(ev.rightUnit)
  }
}
