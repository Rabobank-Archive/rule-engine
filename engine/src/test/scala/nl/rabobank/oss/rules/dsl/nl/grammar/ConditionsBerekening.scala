package nl.rabobank.oss.rules.dsl.nl.grammar

import ConditionsBerekeningGlossary._

class ConditionsBerekening extends Berekening (
  Gegeven(altijd) Bereken
    outputAlwaysAvailable is BigDecimal(10)
  ,
  Gegeven(availableInput is aanwezig) Bereken
    outputShouldBeAvailableIfInputIsAvailable is BigDecimal(11)
  ,
  Gegeven(unavailableInput is afwezig) Bereken
    outputShouldBeAvailableIfInputIsNotAvailable is BigDecimal(12)
  ,
  Gegeven (altijd)
  Bereken
    tryToOverwriteThisValue is eerste (missingValueToUseInEerste, defaultValueForOverwriting) en
    defaultValueForOverwriting is BigDecimal(0)

)
