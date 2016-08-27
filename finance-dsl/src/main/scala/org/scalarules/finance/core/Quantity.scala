package org.scalarules.finance.core

import org.scalarules.finance.nl._

import scala.annotation.implicitNotFound

/**
 * Type class die ervoor zorgt dat er gerekend kan worden met instanties van Per,
 * als de type class ook gedefinieerd is voor het 'waarde' gedeelte van de Per instantie.
 */
@implicitNotFound("No member of type class Quantity available in scope for type ${T}")
trait Quantity[T] {
  /** Returnt de som van n en m. */
  def plus(n: T, m: T): T

  /** Returnt het verschil tussen n en m. */
  def minus(n: T, m: T): T

  /** Returnt het product van n en m. */
  def multiply(n: T, m: BigDecimal): T

  /** Returnt het quotiënt van n en m. */
  def divide(n: T, m: BigDecimal): T

  /** Returnt het quotiënt van n en m. */
  def divideAsFraction(n: T, m: T): BigDecimal

  /** Returnt de negatieve waarde van n. */
  def negate(n: T): T

  /** Returnt het getal 0, met type T. */
  def zero: T

  /** Returnt het getal 1, met type T. */
  def one: T
}

object Quantity {
  implicit object QuantityBigDecimal extends Quantity[BigDecimal] {
    override def plus(n: BigDecimal, m: BigDecimal) = n + m
    override def minus(n: BigDecimal, m: BigDecimal) = n - m
    override def multiply(n: BigDecimal, m: BigDecimal) = n * m
    override def divide(n: BigDecimal, m: BigDecimal) = n / m
    override def divideAsFraction(n: BigDecimal, m: BigDecimal) = n / m
    override def negate(n: BigDecimal) = -n
    override def zero = 0
    override def one = 1
  }
  implicit object QuantityBedrag extends Quantity[Bedrag] {
    override def plus(n: Bedrag, m: Bedrag) = n + m
    override def minus(n: Bedrag, m: Bedrag) = n - m
    override def multiply(n: Bedrag, m: BigDecimal) = n * m
    override def divide(n: Bedrag, m: BigDecimal) = n / m
    override def divideAsFraction(n: Bedrag, m: Bedrag) = n / m
    override def negate(n: Bedrag) = n * -1
    override def zero = 0.euro
    override def one = 1.euro
  }
  private def quantityPerPeriode[W : Quantity, T <: Termijn](termijn: T): Quantity[W Per T] = new Quantity[W Per T] {
    override def plus(n: W Per T, m: W Per T): W Per T = n + m
    override def minus(n: W Per T, m: W Per T): W Per T = n - m
    override def multiply(n: W Per T, m: BigDecimal): W Per T = n * m
    override def divide(n: W Per T, m: BigDecimal): W Per T = n / m
    override def divideAsFraction(n: W Per T, m: W Per T): BigDecimal = {
      val ev = implicitly[Quantity[W]]
      ev.divideAsFraction(n.waarde, m.waarde)
    }
    override def negate(n: W Per T): W Per T = n * -1
    override def zero = Per(implicitly[Quantity[W]].zero, termijn)
    override def one = Per(implicitly[Quantity[W]].one, termijn)
  }
  implicit def quantityPerMaand[W : Quantity]: Quantity[W Per Maand] = quantityPerPeriode(Maand)
  implicit def quantityPerKwartaal[W : Quantity]: Quantity[W Per Kwartaal] = quantityPerPeriode(Kwartaal)
  implicit def quantityPerHalfjaar[W : Quantity]: Quantity[W Per Halfjaar] = quantityPerPeriode(Halfjaar)
  implicit def quantityPerJaar[W : Quantity]: Quantity[W Per Jaar] = quantityPerPeriode(Jaar)

  // TODO : Why is this not covered by the Addable/Subtractable/Multipliable/Divisible type classes???
  implicit def quantityList[N : Quantity](implicit ev: Quantity[N]): Quantity[List[N]] = new Quantity[List[N]] {
    override def plus(n: List[N], m: List[N]): List[N] = n.zipAll(m, ev.zero, ev.zero).map(t => ev.plus(t._1, t._2))
    override def minus(n: List[N], m: List[N]): List[N] = n.zipAll(m, ev.zero, ev.zero).map(t => ev.minus(t._1, t._2))
    override def multiply(n: List[N], m: BigDecimal): List[N] = n.map( ev.multiply(_, m) )
    override def divide(n: List[N], m: BigDecimal): List[N] = n.map( ev.divide(_, m) )
    override def divideAsFraction(n: List[N], m: List[N]): BigDecimal = ev.divideAsFraction(n.reduceLeft(ev.plus(_, _)), m.reduceLeft(ev.plus(_, _)))
    override def negate(n: List[N]): List[N] = n.map( ev.negate )
    override def zero: List[N] = List(ev.zero)
    override def one: List[N] = List(ev.one)
  }
}

