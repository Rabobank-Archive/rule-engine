package nl.rabobank.oss.rules.dsl.core.operators

import nl.rabobank.oss.rules.dsl.core.types.MultipliableValues

import scala.annotation.implicitNotFound

/**
  * This type class allows values of different types to be added in the DSL.
  *
  * @tparam A type of the left hand side of the adding operation
  * @tparam B type of the right hand side of the adding operation
  * @tparam C type of the result of the adding operation
  */
@implicitNotFound("No member of type class Multipliabe available in scope for combination ${A} * ${B} = ${C}")
sealed trait Multipliable[A, B, C] extends BinaryOperable[A, B, C] {
  def operation(a: A, b: B): C

  def identityLeft: A
  def identityRight: B

  def representation: String = "*"
}

object Multipliable {
  implicit def valueMultipliedByValue[A, B, C](implicit ev: MultipliableValues[A, B, C]): Multipliable[A, B, C] = new Multipliable[A, B, C] {
    override def operation(n: A, m: B): C = ev.multiply(n, m)
    override def identityLeft = ev.leftUnit
    override def identityRight = ev.rightUnit
  }
  implicit def listMultipliedByList[A, B, C](implicit ev: MultipliableValues[A, B, C]): Multipliable[List[A], List[B], List[C]] = new Multipliable[List[A], List[B], List[C]] {
    override def operation(n: List[A], m: List[B]): List[C] = n.zip(m).map(t => ev.multiply(t._1, t._2))
    override def identityLeft = List(ev.leftUnit)
    override def identityRight = List(ev.rightUnit)
  }
  implicit def listMultipliedByValue[A, B, C](implicit ev: MultipliableValues[A, B, C]): Multipliable[List[A], B, List[C]] = new Multipliable[List[A], B, List[C]] {
    override def operation(n: List[A], m: B): List[C] = n.map( ev.multiply(_, m) )
    override def identityLeft = List(ev.leftUnit)
    override def identityRight = ev.rightUnit
  }
  implicit def valueMultipliedByList[A, B, C](implicit ev: MultipliableValues[A, B, C]): Multipliable[A, List[B], List[C]] = new Multipliable[A, List[B], List[C]] {
    override def operation(n: A, m: List[B]): List[C] = m.map( ev.multiply(n, _) )
    override def identityLeft = ev.leftUnit
    override def identityRight = List(ev.rightUnit)
  }

}
