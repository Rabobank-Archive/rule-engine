package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.nl.grammar.LoopBerekeningGlossary._
import org.scalarules.utils.{InternalBerekeningenTester, lijst}

class LoopBerekeningTest extends InternalBerekeningenTester(new LoopBerekening) {

  test("of simpele loop over lijst werkt") gegeven (
    loopInput is List[BigDecimal](0, 1, 2, 3, 4, 5, 6)
  ) verwacht (
    simpleLoopResult is List[BigDecimal](0, 2, 4, 6,8, 10, 12)
  )

  test("of loop met subberekening werkt") gegeven (
    loopInput is List[BigDecimal](0, 1, 2, 3, 4, 5, 6),
    innerLoopAdditionValue is BigDecimal(1)
  ) verwacht (
    enhancedLoopResult is List[BigDecimal](1, 2, 3, 4, 5, 6, 7)
  )

  test("of loop met inner loop werkt") gegeven (
    loopInput is List[BigDecimal](0, 1, 2),
    innerLoopInput is List[BigDecimal](10, 10, 10, 10)
  ) verwacht (
    enhancedLoopListInListResult is List(lijst van 4 waarde BigDecimal(10),
                                        lijst van 4 waarde BigDecimal(11),
                                        lijst van 4 waarde BigDecimal(12))
  )

  test("of loop met inner loop goed omgaat met niet aanwezige innerloop waardes") gegeven (
    loopInput is List[BigDecimal](0, 1, 2)
  ) verwacht (
    enhancedLoopListInListResult niet aanwezig
  )

}
