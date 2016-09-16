package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.{absent, present}
import org.scalarules.dsl.en.grammar.ConditionsDerivationGlossary._

class ConditionsDerivation extends Calculation (
  Given(always) determine
    outputAlwaysAvailable is BigDecimal(10)
  ,
  Given(availableInput is present) determine
    outputShouldBeAvailableIfInputIsAvailable is BigDecimal(11)
  ,
  Given(unavailableInput is absent) determine
    outputShouldBeAvailableIfInputIsNotAvailable is BigDecimal(12)
)
