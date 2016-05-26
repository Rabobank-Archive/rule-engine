package org.scalarules.utils

import org.scalarules.engine.{Fact, ListFact, SingularFact}
import org.scalarules.finance.nl.Bedrag

import scala.language.experimental.macros

class Glossary {
  def defineFact[A](omschrijving: String = "Geen beschrijving"): SingularFact[A] = macro FactMacros.defineFactMacroImpl[A]

  def defineListFact[A](omschrijving: String = "Geen beschrijving"): ListFact[A] = macro FactMacros.defineListFactMacroImpl[A]

  def getFacts: Map[String, Fact[Any]] = ??? /* {
    val map: Array[(String, Fact[Any])] = this.getClass().getDeclaredFields().filter(field => classOf[Fact[Any]].isAssignableFrom(field.getType()))
      .map(field => field.get(this).asInstanceOf[Fact[Any]])
      .map(value => (value.name -> value))
    map
      .toMap
  }*/
}
