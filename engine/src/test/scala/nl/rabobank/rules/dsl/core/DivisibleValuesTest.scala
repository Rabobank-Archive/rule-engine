package nl.rabobank.rules.dsl.core

import DivisibleValuesGlossary._
import nl.rabobank.rules.finance.nl._
import nl.rabobank.rules.utils.InternalBerekeningenTester

class DivisibleValuesTest extends InternalBerekeningenTester(new DivisibleValuesBerekening) {

  test("Percentage tussen twee bedragen") gegeven (
    BedragA is 400.euro,
    BedragB is 800.euro
  ) verwacht (
    PercentageA is 50.procent
  )

  test("Bedrag gedeeld door percentage geeft bedrag") gegeven (
    BedragD is 400.euro,
    PercentageB is 50.procent
    ) verwacht (
      BedragC is 800.euro
    )

}
