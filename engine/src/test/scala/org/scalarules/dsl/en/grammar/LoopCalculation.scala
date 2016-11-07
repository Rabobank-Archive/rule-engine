package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.en.grammar.LoopCalculationGlossary._
import org.scalarules.dsl.en.grammar.meta.CalculationReference

class LoopCalculation extends Calculation (
  Given(always) determine
    simpleLoopResult contains results of SimpeleLoopElementCalculation over loopInput
  ,
  Given(always) determine
    nestedTestOutput contains results of GenesteLoopElementCalculation over nestedTestInput
  ,
  Given(always) determine
    filteredLoopResult contains results of GefilterdeLoopElementCalculation over loopInput
)

@CalculationReference
class SimpeleLoopElementCalculation extends ElementCalculation[BigDecimal, BigDecimal] (
  Input is innerLoopIteratee,
  Output is innerLoopReturnValue,
  Given (always) determine innerLoopReturnValue is innerLoopIteratee + innerLoopAdditionValue
)

@CalculationReference
class GenesteLoopElementCalculation extends ElementCalculation[List[BigDecimal], List[BigDecimal]] (
  Input is nestedOuterLoopInput,
  Output is nestedOuterLoopResult,
  Given (always) determine nestedOuterLoopResult contains results of SimpeleLoopElementCalculation over nestedOuterLoopInput
)

@CalculationReference
class GefilterdeLoopElementCalculation extends ElementCalculation[BigDecimal, BigDecimal] (
  Input is innerLoopIteratee,
  Output is innerLoopReturnValue,
  Given (innerLoopIteratee is 2) determine innerLoopReturnValue is innerLoopIteratee + BigDecimal(2)
)
