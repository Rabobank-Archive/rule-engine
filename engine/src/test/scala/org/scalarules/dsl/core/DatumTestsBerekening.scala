package org.scalarules.dsl.core

import org.scalarules.dsl.core.DatumTestGlossary._
import org.scalarules.dsl.nl.grammar._

class DatumTestsBerekening extends Berekening (

  Gegeven (DatumA < "01-01-2015".datum)
    Bereken EerderDan is "success"
  ,
  Gegeven (DatumA <= "01-01-2015".datum)
    Bereken EerderDanGelijk is "success"
  ,
  Gegeven (DatumA > "01-01-2015".datum)
    Bereken LaterDan is "success"
  ,
  Gegeven (DatumA >= "01-01-2015".datum)
    Bereken LaterDanGelijk is "success"
  ,
  Gegeven (DatumA is "01-01-2015".datum)
    Bereken GelijkAan is "success"

)
