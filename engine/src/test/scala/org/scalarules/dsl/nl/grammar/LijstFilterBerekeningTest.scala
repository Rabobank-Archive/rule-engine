package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.nl.grammar.LijstBerekeningGlossary._
import org.scalarules.utils.InternalBerekeningenTester

class LijstFilterBerekeningTest extends InternalBerekeningenTester(new LijstFilter) {

  test("of filteren van lijst werkt") gegeven (
    LijstOnGefilterd is List(0, 1, 2, 3, 4, 5, 6)
  ) verwacht (
    LijstGefilterd is List(1, 2, 3, 4)
  )

  test("of filteren van lijst werkt met Sequence") gegeven (
    LijstOnGefilterd is List(0, 1, 2, 3, 4, 5, 6)
  ) verwacht (
    LijstGefilterdMetList is List(1, 2, 3, 4)
  )

  test("of filteren van lijst werkt met complexe objecten") gegeven (
    LijstOnGefilterdComplexObject is List(ComplexFilterObject(0), ComplexFilterObject(1), ComplexFilterObject(2), ComplexFilterObject(3),
      ComplexFilterObject(4), ComplexFilterObject(5))
  ) verwacht (
    LijstGefilterdComplexObject is List(ComplexFilterObject(3),ComplexFilterObject(4))
  )
}
