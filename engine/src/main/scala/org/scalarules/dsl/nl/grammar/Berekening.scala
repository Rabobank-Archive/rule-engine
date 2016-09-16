package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.grammar.AbstractCalculation

class Berekening(berekeningAccumulators: BerekeningAccumulator*) extends AbstractCalculation[BerekeningAccumulator](berekeningAccumulators:_*) {

  //  val berekeningenWithExtraInputInformation: List[(Derivation, List[Fact[Any]], List[Fact[Any]])] = berekeningAccumulators.flatMap(b => b.toDerivationAndInputSets).toList
}
