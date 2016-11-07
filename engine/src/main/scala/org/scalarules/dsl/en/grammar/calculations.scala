package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.AbstractCalculation
import org.scalarules.engine.Fact

class Calculation(calculationAccumulators: CalculationAccumulator*) extends AbstractCalculation[CalculationAccumulator](calculationAccumulators:_*)

class InputSpec[In](val iteratee: Fact[In])
class OutputSpec[Out](val resultee: Fact[Out])

/**
  * An ElementCalculation is a Calculation with a single input and a single output parameter. These are used primarily when defining a Calculation which is going
  * to be used as the calculation applied while iterating over a list of values. In the DSL these can be constructed using the following syntax:
  *
  * class MyCalculation extends ElementCalculation[<inputType>, <outputType>] (
  *   Input is <inputFact>,
  *   Output is <outputFact>,
  *   Given ...
  * )
  */
class ElementCalculation[In, Out](input: InputSpec[In], output: OutputSpec[Out], calculationAccumulators: CalculationAccumulator*) extends Calculation(calculationAccumulators:_*) {
  def inputFact: Fact[In] = input.iteratee
  def outputFact: Fact[Out] = output.resultee
}

trait ElementCalculationReference[In, Out] {
  def calculation: ElementCalculation[In, Out]
}

