package org.scalarules.dsl.core.grammar

import org.scalarules.engine.Derivation

class AbstractCalculation[T <: DerivationAccumulator](derivationAccumulators: T*) {
  val derivations: List[Derivation] = derivationAccumulators.flatMap( _.derivations ).toList
}

trait DerivationAccumulator {
  def derivations: List[Derivation]
}
