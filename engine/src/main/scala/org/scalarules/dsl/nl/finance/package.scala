package org.scalarules.dsl.nl

/**
  * Aggregeert de implicits van de verschillende onderdelen van de DSL. Op deze manier is een simpel
  *     import nl.rabobank.itn.fpx.moa.calculation.dsl._
  * genoeg om alle implicits van de DSL in scope te brengen.
  */
package object finance extends BedragImplicits
    with PeriodeImplicits
    with PercentageImplicits
    with PerImplicits
    with Ordering.ExtraImplicits {

  type Looptijd = Periode

  /** Singleton Termijn-instantie van 1 maand. */
  val Maand = Termijn.Maand
  /** Singleton Termijn-instantie van 3 maanden. */
  val Kwartaal = Termijn.Kwartaal
  /** Singleton Termijn-instantie van 6 maanden. */
  val Halfjaar = Termijn.Halfjaar
  /** Singleton Termijn-instantie van 12 maanden / 1 jaar. */
  val Jaar = Termijn.Jaar
}
