package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.grammar.AbstractCalculation
import org.scalarules.engine.Fact

class Berekening(berekeningAccumulators: BerekeningAccumulator*) extends AbstractCalculation[BerekeningAccumulator](berekeningAccumulators:_*)

class InvoerSpec[In](val iteratee: Fact[In])
class UitvoerSpec[Uit](val resultee: Fact[Uit])

/**
  * Een ElementBerekening is een Berekening met een enkele invoer en een enkele uitvoer parameter. Deze worden voornamelijk gebruikt bij het definieren van
  * Berekeningen die als elementberekening in een lijst-iteratie worden gebruikt. In de DSL kunnen deze worden vormgegeven door de volgende syntax:
  *
  * class MijnBerekening extends ElementBerekening[<invoertype>, <uitvoertype>] (
  *   Invoer is <invoerFact>,
  *   Uitvoer is <uitvoerFact>,
  *   Gegeven ...
  * )
  */
class ElementBerekening[In, Uit](invoer: InvoerSpec[In], uitvoer: UitvoerSpec[Uit], berekeningAccumulators: BerekeningAccumulator*) extends Berekening(berekeningAccumulators:_*) {
  def invoerFact: Fact[In] = invoer.iteratee
  def uitvoerFact: Fact[Uit] = uitvoer.resultee
}

trait ElementBerekeningReference[In, Uit] {
  def berekening: ElementBerekening[In, Uit]
}

