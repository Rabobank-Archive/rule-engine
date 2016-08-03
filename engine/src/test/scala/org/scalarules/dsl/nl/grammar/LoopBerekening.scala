package org.scalarules.dsl.nl.grammar

import LoopBerekeningGlossary._

class LoopBerekening extends Berekening (
  Gegeven(altijd) Bereken
    simpleLoopResult is (Loop over loopInput per intermediateBigDecimal doe (intermediateBigDecimal * BigDecimal(2)))
  ,
  Gegeven(altijd) Bereken
    enhancedLoopResult is (Loop over loopInput per intermediateBigDecimal geeft innerLoopReturnValue door new Berekening(
      Gegeven (altijd) Bereken innerLoopReturnValue is intermediateBigDecimal + innerLoopAdditionValue)
  )
  ,
  Gegeven(altijd) Bereken
    enhancedLoopListInListResult is (
      Loop over loopInput per intermediateBigDecimal doe (
        Loop over innerLoopInput per intermediateInnerLoopBigDecimal doe (intermediateInnerLoopBigDecimal + intermediateBigDecimal)
      )
    )
  ,
  Gegeven(altijd) Bereken
    filteredLoopResult is (
      Loop over loopInput per intermediateBigDecimal geeft innerLoopReturnValue door new Berekening(
        Gegeven (intermediateBigDecimal is 2) Bereken innerLoopReturnValue is (intermediateBigDecimal * BigDecimal(2))
      )
    )
)
