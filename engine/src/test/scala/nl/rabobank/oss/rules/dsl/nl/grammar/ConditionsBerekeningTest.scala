package nl.rabobank.oss.rules.dsl.nl.grammar

import ConditionsBerekeningGlossary._
import nl.rabobank.oss.rules.utils.InternalBerekeningenTester

class ConditionsBerekeningTest extends InternalBerekeningenTester(new ConditionsBerekening) {

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
