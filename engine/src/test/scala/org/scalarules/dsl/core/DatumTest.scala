package org.scalarules.dsl.core

import org.joda.time.LocalDate
import org.scalarules.dsl.core.DatumTestGlossary._
import org.scalarules.dsl.nl.grammar.aanwezig
import org.scalarules.utils.InternalBerekeningenTester
import org.scalarules.dsl.nl.grammar.date.DslDatumImplicits

class DatumTest extends InternalBerekeningenTester(new DatumTestsBerekening) with DslDatumImplicits {

  val supportedDateFormats = Set(
    "d-M-yyyy",
    "d-MM-yyyy",
    "dd-M-yyyy",
    "dd-MM-yyyy"
  )

  val datumKleiner = new LocalDate(2014, 1, 1)
  val datumGelijk = new LocalDate(2015, 1, 1)
  val datumGroter = new LocalDate(2016, 1, 1)

  supportedDateFormats.foreach( pattern => {

    test(s"${pattern} parsen werkt (1/3)") gegeven (
      DatumA is datumKleiner.toString(pattern).datum
    ) verwacht (
      KleinerDan is "success",
      KleinerDanGelijk is "success",
      GroterDan niet aanwezig,
      GroterDanGelijk niet aanwezig,
      GelijkAan niet aanwezig
    )

    test(s"${pattern} parsen werkt (2/3)") gegeven (
      DatumA is datumGroter.toString(pattern).datum
    ) verwacht (
      KleinerDan niet aanwezig,
      KleinerDan niet aanwezig,
      GroterDan is "success",
      GroterDanGelijk is "success",
      GelijkAan niet aanwezig
    )

    test(s"${pattern} parsen werkt (3/3)") gegeven (
      DatumA is datumGelijk.toString(pattern).datum
    ) verwacht (
      KleinerDan niet aanwezig,
      KleinerDanGelijk is "success",
      GroterDan niet aanwezig,
      GroterDanGelijk is "success",
      GelijkAan is "success"
    )

  })

}
