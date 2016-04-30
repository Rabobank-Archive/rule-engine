package org.scalarules.finance.nl

import org.scalarules.finance.core.NumberLike

// scalastyle:off method.name

/**
 * Geeft aan dat een bepaalde waarde van type W voorkomt voor elke termijn van type T.
 */
case class Per[W, T <: Termijn](waarde: W, termijn: T) {
  /** Returnt de som van deze W en n; per T. */
  def + (n: W Per T)(implicit ev: NumberLike[W]): W Per T = applySafely(ev.plus, this, n)

  /** Returnt het verschil tussen deze W en n; per T. */
  def - (n: W Per T)(implicit ev: NumberLike[W]): W Per T = applySafely(ev.minus, this, n)

  /** Returnt het product van deze W en n; per T. */
  def * (n: BigDecimal)(implicit ev: NumberLike[W]): W Per T = Per(ev.multiply(waarde, n), termijn)

  /** Returnt het quotiënt van deze W en n; per T. */
  def / (n: BigDecimal)(implicit ev: NumberLike[W]): W Per T = Per(ev.divide(waarde, n), termijn)

  /** Converteert dit naar de equivalente waarde per maand. */
  def maandelijks(implicit ev: NumberLike[W]): W Per Maand = Per(ev.divide(waarde, termijn.inMaanden), Maand)

  /** Converteert dit naar de equivalente waarde per jaar. */
  def jaarlijks(implicit ev: NumberLike[W]): W Per Jaar = Per(ev.multiply(waarde, termijn.frequentiePerJaar), Jaar)

  /** Past f toe op waarde en returnt het resultaat, per termijn. */
  def map[V](f: W => V): V Per T = Per(f(waarde), termijn)

  /** Past f toe op waarde en returnt het resultaat. */
  def flatMap[V](f: W => V Per T): V Per T = f(waarde)

  override def toString = s"$waarde per $termijn"

  private def applySafely(f: (W, W) => W, x: W Per T, y: W Per T): W Per T = {
    require(x.termijn == y.termijn)
    Per(f(x.waarde, y.waarde), x.termijn)
  }
}

trait PerImplicits {
  sealed abstract class PerTermijn[W](waarde: W) {
    /** Verandert waarde in een waarde die voorkomt voor elke gegeven termijn. */
    def per[T <: Termijn](termijn: T): W Per T = Per(waarde, termijn)
  }
  implicit class BedragPerTermijn(waarde: Bedrag) extends PerTermijn[Bedrag](waarde)
  implicit class PercentagePerTermijn(waarde: Percentage) extends PerTermijn[Percentage](waarde)
  implicit class BigDecimalPerTermijn(waarde: BigDecimal) extends PerTermijn[BigDecimal](waarde)
  implicit class IntToBigDecimalPerTermijn(waarde: Int) extends PerTermijn[BigDecimal](waarde)
  implicit class StringPerTermijn(waarde: String) extends PerTermijn[String](waarde)


  /**
   * Zorgt ervoor dat Numeric en Ordering operaties toegepast kunnen worden op Per
   * voor types W waarvoor Numeric gedefinieerd is.
   */
  private def numericPerPeriode[W : Numeric, T <: Termijn](termijn: T) = new Numeric[W Per T] {
    val ev = implicitly[Numeric[W]]
    override def plus(x: W Per T, y: W Per T): W Per T = Per(ev.plus(x.waarde, y.waarde), termijn)
    override def minus(x: W Per T, y: W Per T): W Per T = Per(ev.minus(x.waarde, y.waarde), termijn)
    override def times(x: W Per T, y: W Per T): W Per T =
      throw new IllegalStateException("Vermenigvuldiging van per*per zou per^2 geven, wat niets betekent.")
    override def negate(x: W Per T): W Per T = Per(ev.negate(x.waarde), termijn)
    override def fromInt(x: Int): W Per T = Per(ev.fromInt(x), termijn)
    override def toInt(x: W Per T): Int = ev.toInt(x.waarde)
    override def toLong(x: W Per T): Long = ev.toLong(x.waarde)
    override def toFloat(x: W Per T): Float = ev.toFloat(x.waarde)
    override def toDouble(x: W Per T): Double = ev.toDouble(x.waarde)
    override def compare(x: W Per T, y: W Per T): Int = ev.compare(x.waarde, y.waarde)
  }
  implicit def numericPerMaand[W : Numeric]: Numeric[W Per Maand] = numericPerPeriode(Maand)
  implicit def numericPerKwartaal[W : Numeric]: Numeric[W Per Kwartaal] = numericPerPeriode(Kwartaal)
  implicit def numericPerHalfjaar[W : Numeric]: Numeric[W Per Halfjaar] = numericPerPeriode(Halfjaar)
  implicit def numericPerJaar[W : Numeric]: Numeric[W Per Jaar] = numericPerPeriode(Jaar)

  /**
   * Zorgt ervoor dat Numeric en Ordering operaties toegepast kunnen worden op Per
   * voor types W waarvoor Numeric gedefinieerd is, en waarvoor de Termijn ongespecificeerd is.
   */
  implicit def numericPerTermijn[W : NumberLike : Numeric]: Numeric[W Per Termijn] = new Numeric[W Per Termijn] {
    val ev = implicitly[Numeric[W]]
    override def plus(x: W Per Termijn, y: W Per Termijn): W Per Termijn = x + y
    override def minus(x: W Per Termijn, y: W Per Termijn): W Per Termijn = x - y
    override def times(x: W Per Termijn, y: W Per Termijn): W Per Termijn =
      throw new IllegalStateException("Vermenigvuldiging van per*per zou per^2 geven, wat niets betekent.")
    override def negate(x: W Per Termijn): W Per Termijn = Per(ev.negate(x.waarde), x.termijn)
    override def fromInt(x: Int): W Per Termijn =
      throw new IllegalStateException("Kan geen Per maken met een onbekende Termijn")
    override def toInt(x: W Per Termijn): Int = ev.toInt(x.waarde)
    override def toLong(x: W Per Termijn): Long = ev.toLong(x.waarde)
    override def toDouble(x: W Per Termijn): Double = ev.toDouble(x.waarde)
    override def toFloat(x: W Per Termijn): Float = ev.toFloat(x.waarde)
    override def compare(x: W Per Termijn, y: W Per Termijn): Int = {
      require(x.termijn == y.termijn)
      ev.compare(x.waarde, y.waarde)
    }
  }
}
