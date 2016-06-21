package org.scalarules.utils

import org.scalarules.engine.{Fact, ListFact, SingularFact}

/**
  * Basic utility class for collecting `Fact`s. This version has become deprecated in favor of the MacroGlossary.
  */
@deprecated(message = "Replaced by macro based glossary (see MacroGlossary)", since = "0.3.0")
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

  def defineFact[A](naam: String, omschrijving: String = "Geen beschrijving"): SingularFact[A] = {
    val newFact = new SingularFact[A](naam, false, omschrijving)
    addAndCheckFacts( newFact )
    newFact
  }

  def defineListFact[A](naam: String, omschrijving: String = "Geen beschrijving"): ListFact[A] = {
    val newFact = new ListFact[A](naam, true, omschrijving)
    addAndCheckFacts( newFact )
    newFact
  }

  def getFacts: Map[String, Fact[Any]] = facts
}
