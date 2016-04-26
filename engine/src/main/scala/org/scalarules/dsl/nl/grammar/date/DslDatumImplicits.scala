package org.scalarules.dsl.nl.grammar.date

import java.util.Date

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.scalarules.dsl.core.grammar.date.DslDate

/**
  * Provides the implicit conversions to DslDate for the NL version of the DSL.
  * It defines a Dutch type alias `Datum` for [[org.scalarules.dsl.core.grammar.date.DslDate]].
  *
  * Currently supported conversions are:
  * - [[java.lang.String]] (formatted as dd-MM-yyyy)
  * - JodaTime [[org.joda.time.LocalDate]]
  * - [[java.util.Date]]
  */
trait DslDatumImplicits {

  type Datum = DslDate

  abstract class ToDslDate(internal: LocalDate) {
    /** Builds DslDate from the provided value. */
    def datum: Datum = DslDate(internal)

  }

  lazy val dtf = DateTimeFormat.forPattern("dd-MM-yyyy")

  implicit class JodaLocalDateToDslDate(external: LocalDate) extends ToDslDate(external)
  implicit class JavaDateToDslDate(external: Date) extends ToDslDate(new LocalDate(external))
  implicit class StringToDslDate(external: String) extends ToDslDate(dtf.parseLocalDate(external))

}
