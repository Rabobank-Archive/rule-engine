package nl.rabobank.rules.dsl.nl.datum

import java.util.Date

import nl.rabobank.rules.dsl.core.temporal.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.{LocalDate => JodaLocalDate}

/**
  * Provides the implicit conversions to DslDate for the NL version of the DSL.
  * It defines a Dutch type alias `Datum` for `nl.rabobank.rules.dsl.core.temporal.LocalDate`.
  *
  * Currently supported conversions are:
  * - `String` (formatted as dd-MM-yyyy)
  * - JodaTime `org.joda.time.LocalDate`
  * - `java.util.Date`
  */
trait DatumImplicits {

  type Datum = LocalDate

  abstract class ToDslDate(internal: JodaLocalDate) {
    /** Builds DslDate from the provided value. */
    def datum: Datum = LocalDate(internal)

  }

  lazy val dtf = DateTimeFormat.forPattern("dd-MM-yyyy")

  implicit class JodaLocalDateToDslDate(external: JodaLocalDate) extends ToDslDate(external)
  implicit class JavaDateToDslDate(external: Date) extends ToDslDate(new JodaLocalDate(external))
  implicit class StringToDslDate(external: String) extends ToDslDate(dtf.parseLocalDate(external))

}
