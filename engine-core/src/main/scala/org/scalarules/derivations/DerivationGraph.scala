package org.scalarules.derivations

import org.scalarules.engine.Derivation

trait DerivationGraph {

}

case class Node(derivation: Derivation, children: List[Node]) extends DerivationGraph

