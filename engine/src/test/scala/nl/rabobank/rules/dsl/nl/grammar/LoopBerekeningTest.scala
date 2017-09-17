package nl.rabobank.rules.dsl.nl.grammar

import LoopBerekeningGlossary._
import nl.rabobank.rules.utils.InternalBerekeningenTester

class LoopBerekeningTest extends InternalBerekeningenTester(new LoopBerekening) {

  test("of simpele loop over lijst werkt") gegeven (
    loopInput is List[BigDecimal](0, 1, 2, 3, 4, 5, 6),
    innerLoopAdditionValue is BigDecimal(2)
  ) verwacht (
    simpleLoopResult is List[BigDecimal](2, 3, 4, 5, 6, 7, 8)
  )

  test("of loop goed omgaat met niet aanwezige innerloop waardes") gegeven (
    loopInput is List[BigDecimal](0, 1, 2, 3, 4, 5, 6)
  ) verwacht (
    simpleLoopResult is List()
  )

  test("of loop met geneste elementberekeningen werkt") gegeven (
    nestedTestInput is List(List[BigDecimal](0, 1), List[BigDecimal](2, 3), List[BigDecimal](4, 5)),
    innerLoopAdditionValue is BigDecimal(1)
  ) verwacht (
    nestedTestOutput is List(List[BigDecimal](1, 2), List[BigDecimal](3, 4), List[BigDecimal](5, 6))
  )

  test("of loop met inner loop goed omgaat met niet aanwezige innerloop waardes") gegeven (
    nestedTestInput is List(List[BigDecimal](0, 1), List[BigDecimal](2, 3), List[BigDecimal](4, 5))
  ) verwacht (
    nestedTestOutput is List(List(), List(), List())
  )

  test("of loop goed omgaat met gefilterde return values") gegeven (
    loopInput is List[BigDecimal](0, 1, 2)
  ) verwacht (
    filteredLoopResult is List[BigDecimal](4)
  )

}
