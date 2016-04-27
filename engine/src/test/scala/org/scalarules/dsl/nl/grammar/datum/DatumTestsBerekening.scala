package org.scalarules.dsl.nl.grammar.datum

import org.scalarules.dsl.nl.grammar._

class DatumTestsBerekening extends Berekening (

  Gegeven (InvoerDatum < "01-01-2015".datum)
    Bereken EerderDan is "success"
  ,
  Gegeven (InvoerDatum <= "01-01-2015".datum)
    Bereken EerderDanGelijk is "success"
  ,
  Gegeven (InvoerDatum > "01-01-2015".datum)
    Bereken LaterDan is "success"
  ,
  Gegeven (InvoerDatum >= "01-01-2015".datum)
    Bereken LaterDanGelijk is "success"
  ,
  Gegeven (InvoerDatum is "01-01-2015".datum)
    Bereken GelijkAan is "success"

)
