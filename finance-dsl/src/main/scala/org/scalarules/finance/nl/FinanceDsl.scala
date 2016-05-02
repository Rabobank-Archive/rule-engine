package org.scalarules.finance.nl

trait FinanceDsl extends BedragImplicits
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
