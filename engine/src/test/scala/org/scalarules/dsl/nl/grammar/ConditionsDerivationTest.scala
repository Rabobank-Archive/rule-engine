package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.nl.grammar.ConditionsBerekeningGlossary._
import org.scalarules.utils.InternalBerekeningenTester

class ConditionsDerivationTest extends InternalBerekeningenTester(new ConditionsBerekening) {

  test("of altijd condition werkt met lege context") gegeven (

  ) verwacht (
    outputAlwaysAvailable is BigDecimal(10)
  )

  test("of altijd condition werkt met gevulde context") gegeven (
    availableInput is BigDecimal(1)
  ) verwacht (
    outputAlwaysAvailable is BigDecimal(10)
  )

  test("of aanwezig condition werkt met lege context") gegeven (

  ) verwacht (
    outputAlwaysAvailable is BigDecimal(10),
    outputShouldBeAvailableIfInputIsAvailable niet aanwezig
  )

  test("of aanwezig condition werkt met gevulde context") gegeven (
    availableInput is BigDecimal(1)
  ) verwacht (
    outputAlwaysAvailable is BigDecimal(10),
    outputShouldBeAvailableIfInputIsAvailable is BigDecimal(11)
  )

  test("of niet aanwezig condition werkt met lege context") gegeven (

  ) verwacht (
    outputAlwaysAvailable is BigDecimal(10),
    outputShouldBeAvailableIfInputIsNotAvailable is BigDecimal(12)
  )

  test("of niet aanwezig condition werkt met gevulde context") gegeven (
    unavailableInput is BigDecimal(1)
  ) verwacht (
    outputAlwaysAvailable is BigDecimal(10),
    outputShouldBeAvailableIfInputIsNotAvailable niet aanwezig
  )

  test("of aanwezige waarde niet wordt overschreven") gegeven (
    tryToOverwriteThisValue is BigDecimal(100)
  ) verwacht (
    tryToOverwriteThisValue is BigDecimal(100)
  )

}
