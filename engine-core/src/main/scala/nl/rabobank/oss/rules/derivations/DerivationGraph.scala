package nl.rabobank.oss.rules.derivations

trait DerivationGraph {

}

case class Node(derivation: Derivation, children: List[Node]) extends DerivationGraph

