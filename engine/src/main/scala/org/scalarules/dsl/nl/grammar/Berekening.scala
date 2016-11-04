package org.scalarules.dsl.nl.grammar

import org.scalarules.derivations.Derivation

class Berekening(berekeningAccumulators: BerekeningAccumulator*) {
  val berekeningen: List[Derivation] = berekeningAccumulators.flatMap(_.derivations).toList
//  val berekeningenWithExtraInputInformation: List[(Derivation, List[Fact[Any]], List[Fact[Any]])] = berekeningAccumulators.flatMap(b => b.toDerivationAndInputSets).toList
}
