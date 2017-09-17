package nl.rabobank.rules.derivations

trait DerivationGraph {

}

case class Node(derivation: Derivation, children: List[Node]) extends DerivationGraph

