package nl.rabobank.oss.rules.dsl.core.operators

import nl.rabobank.oss.rules.dsl.core.types.SubtractableValues

import scala.annotation.implicitNotFound

/**
  * This type class allows values of different types to be added in the DSL.
  *
  * @tparam A type of the left hand side of the adding operation
  * @tparam B type of the right hand side of the adding operation
  * @tparam C type of the result of the adding operation
  */
@implicitNotFound("No member of type class Subtractable available in scope for combination ${A} - ${B} = ${C}")
trait Subtractable[A, B, C] extends BinaryOperable[A, B, C] {
  def operation(a: A, b: B): C

  def identityLeft: A
  def identityRight: B

  def representation: String = "-"
}

object Subtractable {
  implicit def valueSubtractedByValue[A, B, C](implicit ev: SubtractableValues[A, B, C]): Subtractable[A, B, C] = new Subtractable[A, B, C] {
    override def operation(n: A, m: B): C = ev.minus(n, m)
    override def identityLeft = ev.leftUnit
    override def identityRight = ev.rightUnit
  }
  implicit def listSubtractedByList[A, B, C](implicit ev: SubtractableValues[A, B, C]): Subtractable[List[A], List[B], List[C]] = new Subtractable[List[A], List[B], List[C]] {
    override def operation(n: List[A], m: List[B]): List[C] = n.zipAll(m, ev.leftUnit, ev.rightUnit).map(t => ev.minus(t._1, t._2))
    override def identityLeft = List(ev.leftUnit)
    override def identityRight = List(ev.rightUnit)
  }
  implicit def listSubtractedByValue[A, B, C](implicit ev: SubtractableValues[A, B, C]): Subtractable[List[A], B, List[C]] = new Subtractable[List[A], B, List[C]] {
    override def operation(n: List[A], m: B): List[C] = n.map( ev.minus(_, m) )
    override def identityLeft = List(ev.leftUnit)
    override def identityRight = ev.rightUnit
  }
  implicit def valueSubtractedByList[A, B, C](implicit ev: SubtractableValues[A, B, C]): Subtractable[A, List[B], List[C]] = new Subtractable[A, List[B], List[C]] {
    override def operation(n: A, m: List[B]): List[C] = m.map( ev.minus(n, _) )
    override def identityLeft = ev.leftUnit
    override def identityRight = List(ev.rightUnit)
  }
}
