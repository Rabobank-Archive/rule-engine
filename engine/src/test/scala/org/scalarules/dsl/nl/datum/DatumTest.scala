package org.scalarules.dsl.nl.datum

import org.joda.time.LocalDate
import org.scalarules.dsl.nl.grammar.aanwezig
import org.scalarules.utils.InternalBerekeningenTester
import org.scalarules.dsl.nl.datum.DatumTestGlossary._

class DatumTest extends InternalBerekeningenTester(new DatumTestsBerekening) with DatumImplicits {

  val supportedDateFormats = Set(
    "d-M-yyyy",
    "d-MM-yyyy",
    "dd-M-yyyy",
    "dd-MM-yyyy"
  )

  val datumEerder = new LocalDate(2014, 1, 1)
  val datumGelijk = new LocalDate(2015, 1, 1)
  val datumLater = new LocalDate(2016, 1, 1)

  supportedDateFormats.foreach( pattern => {

    test(s"${pattern} parsen werkt (1/3)") gegeven (
      InvoerDatum is datumEerder.toString(pattern).datum
    ) verwacht (
      EerderDan is "success",
      EerderDanGelijk is "success",
      LaterDan niet aanwezig,
      LaterDanGelijk niet aanwezig,
      GelijkAan niet aanwezig
    )

    test(s"${pattern} parsen werkt (2/3)") gegeven (
      InvoerDatum is datumLater.toString(pattern).datum
    ) verwacht (
      EerderDan niet aanwezig,
      EerderDan niet aanwezig,
      LaterDan is "success",
      LaterDanGelijk is "success",
      GelijkAan niet aanwezig
    )

    test(s"${pattern} parsen werkt (3/3)") gegeven (
      InvoerDatum is datumGelijk.toString(pattern).datum
    ) verwacht (
      EerderDan niet aanwezig,
      EerderDanGelijk is "success",
      LaterDan niet aanwezig,
      LaterDanGelijk is "success",
      GelijkAan is "success"
    )

  })

}
