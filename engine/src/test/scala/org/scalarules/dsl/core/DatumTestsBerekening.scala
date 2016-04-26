package org.scalarules.dsl.core

import org.scalarules.dsl.core.DatumTestGlossary._
import org.scalarules.dsl.nl.grammar._

class DatumTestsBerekening extends Berekening (

  Gegeven (DatumA < "01-01-2015".datum)
    Bereken KleinerDan is "success"
  ,
  Gegeven (DatumA <= "01-01-2015".datum)
    Bereken KleinerDanGelijk is "success"
  ,
  Gegeven (DatumA > "01-01-2015".datum)
    Bereken GroterDan is "success"
  ,
  Gegeven (DatumA >= "01-01-2015".datum)
    Bereken GroterDanGelijk is "success"
  ,
  Gegeven (DatumA is "01-01-2015".datum)
    Bereken GelijkAan is "success"

)
