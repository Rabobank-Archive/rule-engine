package org.scalarules.utils

import org.scalarules.engine.{Fact, ListFact, SingularFact}

import scala.reflect.runtime.universe._

class Glossary {
  private var facts: Map[String, Fact[Any]] = Map()

  protected def addAndCheckFacts(newFact: Fact[Any]): Unit = {
    val name: String = newFact.name.toLowerCase
    if (facts contains name) {
      throw new IllegalArgumentException(s"Attemping to add a second fact with the name '${name}', please keep the variable name and String value in " +
        s"sync. Note also that Fact names are case insensitive, so FaCt and fAcT are considered the same.")
    }

    facts += (name -> newFact)
  }

  def defineFact[A: TypeTag](naam: String, omschrijving: String = "Geen beschrijving"): SingularFact[A] = {
    val newFact = new SingularFact[A](naam, false, omschrijving)
    addAndCheckFacts( newFact )
    newFact
  }

  def defineListFact[A: TypeTag](naam: String, omschrijving: String = "Geen beschrijving"): ListFact[A] = {
    val newFact = new ListFact[A](naam, true, omschrijving)
    addAndCheckFacts( newFact )
    newFact
  }

  def getFacts: Map[String, Fact[Any]] = facts
}
