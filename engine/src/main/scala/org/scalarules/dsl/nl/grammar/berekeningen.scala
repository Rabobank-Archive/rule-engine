package org.scalarules.dsl.nl.grammar

import org.scalarules.engine.{Derivation, Fact}

class Berekening(berekeningAccumulators: BerekeningAccumulator*) {
  val berekeningen: List[Derivation] = berekeningAccumulators.flatMap(_.derivations).toList
}

class InvoerSpec[In](val iteratee: Fact[In])
class UitvoerSpec[Uit](val resultee: Fact[Uit])

/**
  * Een FlowBerekening is een Berekening met een enkele invoer en een enkele uitvoer parameter. Deze worden voornamelijk gebruikt bij het definieren van
  * Berekeningen die als subberekening in een lijst-iteratie worden gebruikt. In de DSL kunnen deze worden vormgegeven door de volgende syntax:
  *
  * class MijnBerekening extends FlowBerekening[<invoertype>, <uitvoertype>] (
  */
class FlowBerekening[In, Uit](invoer: InvoerSpec[In], uitvoer: UitvoerSpec[Uit], berekeningAccumulators: BerekeningAccumulator*) extends Berekening(berekeningAccumulators:_*) {
  def invoerFact: Fact[In] = invoer.iteratee
  def uitvoerFact: Fact[Uit] = uitvoer.resultee
}

trait FlowBerekeningReference[In, Uit] {
  def berekening: FlowBerekening[In, Uit]
}

