package org.scalarules.dsl.core.projections

import org.scalarules.utils.InternalBerekeningenTester
import org.scalarules.dsl.core.projections.ProjectableFieldsGlossary._

class ProjectableFieldsTest extends InternalBerekeningenTester(new ProjectableFieldsCalculation) {

  test("Projections work by extracting intValue from a Complex Object") gegeven (
    ComplexFact is ComplexObject(2, "Hello "),
    IntFact2 is 5
  ) verwacht (
    IntFact is 7
  )
}
