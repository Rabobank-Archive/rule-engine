package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.present
import org.scalarules.dsl.en.grammar.ConditionsDerivationGlossary._
import org.scalarules.utils.InternalBerekeningenTester

class ConditionsDerivationTest extends InternalBerekeningenTester(new ConditionsDerivation) {

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
    outputShouldBeAvailableIfInputIsAvailable niet present
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
    outputShouldBeAvailableIfInputIsNotAvailable niet present
  )

}
