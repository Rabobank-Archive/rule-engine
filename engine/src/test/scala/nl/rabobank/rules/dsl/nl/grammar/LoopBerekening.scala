package nl.rabobank.rules.dsl.nl.grammar

import LoopBerekeningGlossary._
import nl.rabobank.rules.dsl.nl.grammar.meta.BerekeningReferentie

class LoopBerekening extends Berekening (
  Gegeven(altijd) Bereken
    simpleLoopResult bevat resultaten van SimpeleLoopElementBerekening over loopInput
  ,
  Gegeven(altijd) Bereken
    nestedTestOutput bevat resultaten van GenesteLoopElementBerekening over nestedTestInput
  ,
  Gegeven(altijd) Bereken
    filteredLoopResult bevat resultaten van GefilterdeLoopElementBerekening over loopInput
)

@BerekeningReferentie
class SimpeleLoopElementBerekening extends ElementBerekening[BigDecimal, BigDecimal] (
  Invoer is innerLoopIteratee,
  Uitvoer is innerLoopReturnValue,
  Gegeven (altijd) Bereken innerLoopReturnValue is innerLoopIteratee + innerLoopAdditionValue
)

@BerekeningReferentie
class GenesteLoopElementBerekening extends ElementBerekening[List[BigDecimal], List[BigDecimal]] (
  Invoer is nestedOuterLoopInput,
  Uitvoer is nestedOuterLoopResult,
  Gegeven (altijd) Bereken nestedOuterLoopResult bevat resultaten van SimpeleLoopElementBerekening over nestedOuterLoopInput
)

@BerekeningReferentie
class GefilterdeLoopElementBerekening extends ElementBerekening[BigDecimal, BigDecimal] (
  Invoer is innerLoopIteratee,
  Uitvoer is innerLoopReturnValue,
  Gegeven (innerLoopIteratee is 2) Bereken innerLoopReturnValue is innerLoopIteratee + BigDecimal(2)
)
