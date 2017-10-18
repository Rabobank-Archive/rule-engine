package nl.rabobank.oss.rules.dsl.core.temporal

import org.joda.time.{LocalDate => JodaLocalDate}

/**
  * Provides the DSL with a Date type. It supports the Ordered trait, which means it can be used
  * in DslConditions with the comparative operators.
  *
  * @param internal internal representation of the date.
  */
case class LocalDate private[temporal](internal: JodaLocalDate) extends Ordered[LocalDate] {

  override def compare(that: LocalDate): Int = internal.compareTo(that.internal)

}

