package org.scalarules.dsl.nl.grammar

import LoopBerekeningGlossary._
import org.scalarules.dsl.nl.grammar.meta.SubBerekening

class LoopBerekening extends Berekening (
  Gegeven(altijd) Bereken
    simpleLoopResult bevat resultaten van SimpeleLoopSubBerekening over loopInput
  ,
  Gegeven(altijd) Bereken
    nestedTestOutput bevat resultaten van GenesteLoopSubBerekening over nestedTestInput
  ,
  Gegeven(altijd) Bereken
    filteredLoopResult bevat resultaten van GefilterdeLoopSubBerekening over loopInput
)

@SubBerekening
class SimpeleLoopSubBerekening extends FlowBerekening[BigDecimal, BigDecimal] (
  Invoer is innerLoopIteratee,
  Uitvoer is innerLoopReturnValue,
  Gegeven (altijd) Bereken innerLoopReturnValue is innerLoopIteratee + innerLoopAdditionValue
)

@SubBerekening
class GenesteLoopSubBerekening extends FlowBerekening[List[BigDecimal], List[BigDecimal]] (
  Invoer is nestedOuterLoopInput,
  Uitvoer is nestedOuterLoopResult,
  Gegeven (altijd) Bereken nestedOuterLoopResult bevat resultaten van SimpeleLoopSubBerekening over nestedOuterLoopInput
)

@SubBerekening
class GefilterdeLoopSubBerekening extends FlowBerekening[BigDecimal, BigDecimal] (
  Invoer is innerLoopIteratee,
  Uitvoer is innerLoopReturnValue,
  Gegeven (innerLoopIteratee is 2) Bereken innerLoopReturnValue is innerLoopIteratee + BigDecimal(2)
)
