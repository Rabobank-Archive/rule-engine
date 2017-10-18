package nl.rabobank.oss.rules.dsl.core.projections

import nl.rabobank.oss.rules.utils.InternalBerekeningenTester
import nl.rabobank.oss.rules.dsl.core.projections.ProjectableFieldsGlossary._

class ProjectableFieldsTest extends InternalBerekeningenTester(new ProjectableFieldsCalculation) {

  test("show Projections work by extracting intValue from a Complex Object") gegeven (
    ComplexFact is ComplexObject(2, "Hello "),
    IntFact2 is 5
  ) verwacht (
    IntFact is 7
  )

  test("show ListProjections work by extracting values from a Complex Object List") gegeven (
    ComplexFactList is List(ComplexObject(2, "Hello "), ComplexObject(1, "World"))
  ) verwacht (
    StringFactList is List("Hello ", "World")
  )
}
