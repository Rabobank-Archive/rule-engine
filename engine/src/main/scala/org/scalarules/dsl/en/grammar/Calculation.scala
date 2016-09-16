package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.AbstractCalculation

class Calculation(berekeningAccumulators: CalculationAccumulator*) extends AbstractCalculation[CalculationAccumulator](berekeningAccumulators:_*) {

  //  val berekeningenWithExtraInputInformation: List[(Derivation, List[Fact[Any]], List[Fact[Any]])] = berekeningAccumulators.flatMap(b => b.toDerivationAndInputSets).toList
}
