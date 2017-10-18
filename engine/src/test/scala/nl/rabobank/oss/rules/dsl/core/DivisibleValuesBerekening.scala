package nl.rabobank.oss.rules.dsl.core

import nl.rabobank.oss.rules.dsl.nl.grammar._
import DivisibleValuesGlossary._

class DivisibleValuesBerekening extends Berekening (

  Gegeven (altijd)
    Bereken PercentageA is BedragA / BedragB
,

  Gegeven (altijd)
    Bereken BedragC is BedragD / PercentageB

)
