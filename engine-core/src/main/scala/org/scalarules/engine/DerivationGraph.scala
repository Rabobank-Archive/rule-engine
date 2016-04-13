package org.scalarules.engine

trait DerivationGraph {

}

case class Node(derivation: Derivation, children: List[Node]) extends DerivationGraph
