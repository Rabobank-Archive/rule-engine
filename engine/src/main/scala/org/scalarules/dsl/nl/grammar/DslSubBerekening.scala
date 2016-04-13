package org.scalarules.dsl.nl.grammar

import org.scalarules.engine._

object SubBerekening {
  def over[B](listFact: ListFact[B]): SubBerekeningPart[B] = new SubBerekeningPart[B](listFact)
}

class SubBerekeningPart[B](listFact: ListFact[B]) {
  def met(operation: B => Context): SubBerekeningPart2[B] = new SubBerekeningPart2[B](listFact, operation)
  def vul(fact: Fact[B]): SubBerekeningPart2[B] = {
    val bToFact1ToB: B => Context = (x: B) => Map(fact -> x)
    new SubBerekeningPart2[B](listFact, bToFact1ToB)
  }
}

class SubBerekeningPart2[B](listFact: ListFact[B], operation: B => Context) {
  def geeft[A](resultFact: Fact[A]): SubBerekeningPart3[A, B] = new SubBerekeningPart3(listFact, operation, resultFact)
}

class SubBerekeningPart3[A, B](listFact: ListFact[B], operation: B => Context, resultFact: Fact[A]) {
  def door(berekening: Berekening): SubRunData[A, B] = new SubRunData(berekening.berekeningen, operation, listFact, resultFact)
}
