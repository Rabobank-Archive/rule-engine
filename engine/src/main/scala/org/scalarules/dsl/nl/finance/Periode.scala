package org.scalarules.dsl.nl.finance

// scalastyle:off method.name

/**
 * Representeert een periode in maanden.
 */
case class Periode private[finance](inMaanden: Int) {
  require (inMaanden >= 0)

  /** Returnt de som van deze periode en n. */
  def + (n: Periode): Periode = Periode(inMaanden + n.inMaanden)

  /** Returnt het verschil tussen deze periode en n. */
  def - (n: Periode): Periode = Periode(inMaanden - n.inMaanden)

  /** Returnt hoe vaak deze periode in een jaar past. */
  def frequentiePerJaar: Int = 12 / inMaanden

  /** Returnt deze periode als een [[Int]], afgekapt op hele jaren. */
  def inAfgekapteJaren: Int = inMaanden / 12

  /** Kapt deze periode af (naar beneden) op hele jaren. */
  def afgekaptOpJaren: Periode = inAfgekapteJaren.jaar

  /** Past f toe op alle jaren binnen deze [[Periode]], beginnend bij 0, en afgekapt op hele jaren. */
  def mapOverJaren[T](f: Int => T): Seq[T] = (0 until inAfgekapteJaren) map f

  override def toString = s"$inMaanden maanden"
}

trait PeriodeImplicits {
  implicit class IntToTijdsduur(value: Int) {
    /** Maakt een tijdsduur in maanden. */
    def maand: Periode = maanden
    /** Maakt een tijdsduur in maanden. */
    def maanden: Periode = Periode(value)

    /** Maakt een tijdsduur in jaren. */
    def jaar: Periode = Periode(value * 12)
  }

  implicit object OrderingPeriode extends Ordering[Periode] {
    override def compare(x: Periode, y: Periode): Int = x.inMaanden compare y.inMaanden
  }
}
