package org.scalarules.dsl.core

import org.scalarules.dsl.nl.finance._
import DivisibleValuesGlossary._
import org.scalarules.utils.InternalBerekeningenTester

class DivisibleValuesTest extends InternalBerekeningenTester(new DivisibleValuesBerekening) {

  test("Percentage tussen twee bedragen") gegeven (
    BedragA is 400.euro,
    BedragB is 800.euro
  ) verwacht (
    PercentageA is 50.procent
  )

}
