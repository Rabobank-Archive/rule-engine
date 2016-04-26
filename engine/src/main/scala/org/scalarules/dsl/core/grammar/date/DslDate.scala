package org.scalarules.dsl.core.grammar.date

import org.joda.time.LocalDate

/**
  * Provides the DSL with a Date type. It supports the Ordered trait, which means it can be used
  * in DslConditions with the comparative operators.
  *
  * @param internal internal representation of the date.
  */
case class DslDate private[date](internal: LocalDate) extends Ordered[DslDate] {

  override def compare(that: DslDate): Int = internal.compareTo(that.internal)

}

