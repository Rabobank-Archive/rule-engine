package org.scalarules.dsl.nl.finance

/** Mogelijke periodes om te gebruiken met [[Per]]. */
sealed trait Termijn extends Periode

// scalastyle:off magic.number

/** Representeert een termijn van 1 maand. */
class Maand private[finance] () extends Periode(1) with Termijn {
  override def toString = "maand"
}

/** Representeert een termijn van 3 maanden. */
class Kwartaal private[finance] () extends Periode(3) with Termijn {
  override def toString = "kwartaal"
}

/** Representeert een termijn van 6 maanden. */
class Halfjaar private[finance] () extends Periode(6) with Termijn {
  override def toString = "halfjaar"
}

/** Representeert een termijn van 12 maanden / 1 jaar. */
class Jaar private[finance] () extends Periode(12) with Termijn {
  override def toString = "jaar"
}

// scalastyle:on magic.number

object Termijn {
  /*
   * These instances must be val instead of object. If they're objects, their type will become 'Jaar.type'
   * instead of 'Jaar', and then Per needs to become covariant, which we don't want.
   */

  /** Singleton Termijn-instantie van 1 maand. */
  val Maand = new Maand
  /** Singleton Termijn-instantie van 3 maanden. */
  val Kwartaal = new Kwartaal
  /** Singleton Termijn-instantie van 6 maanden. */
  val Halfjaar = new Halfjaar
  /** Singleton Termijn-instantie van 12 maanden / 1 jaar. */
  val Jaar = new Jaar
}
