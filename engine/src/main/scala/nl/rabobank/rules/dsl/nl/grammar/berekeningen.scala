package nl.rabobank.rules.dsl.nl.grammar

import nl.rabobank.rules.derivations.Derivation
import nl.rabobank.rules.facts.Fact

class Berekening(berekeningAccumulators: BerekeningAccumulator*) {
  val berekeningen: List[Derivation] = berekeningAccumulators.flatMap(_.derivations).toList
}

class InvoerSpecification[In](val iteratee: Fact[In])
class UitvoerSpecification[Uit](val resultee: Fact[Uit])

/**
  * Een ElementBerekening is een Berekening met een enkele invoer en een enkele uitvoer parameter. Deze worden voornamelijk gebruikt bij het definieren van
  * Berekeningen die als elementberekening in een lijst-iteratie worden gebruikt. In de DSL kunnen deze worden vormgegeven door de volgende syntax:
  *
  * class MijnBerekening extends ElementBerekening[<invoertype>, <uitvoertype>] (
  */
class ElementBerekening[In, Uit](invoer: InvoerSpecification[In], uitvoer: UitvoerSpecification[Uit], berekeningAccumulators: BerekeningAccumulator*) extends Berekening(berekeningAccumulators:_*) {
  def invoerFact: Fact[In] = invoer.iteratee
  def uitvoerFact: Fact[Uit] = uitvoer.resultee
}

trait ElementBerekeningReference[In, Uit] {
  def berekening: ElementBerekening[In, Uit]
}
