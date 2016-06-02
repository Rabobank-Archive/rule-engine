package org.scalarules.utils

import java.lang.reflect.Field

import org.scalarules.engine.{Fact, ListFact, SingularFact}
import org.scalarules.finance.nl.Bedrag

import scala.language.experimental.macros

class Glossary {
  def defineFact[A](omschrijving: String = "Geen beschrijving"): SingularFact[A] = macro FactMacros.defineFactMacroImpl[A]

  def defineListFact[A](omschrijving: String = "Geen beschrijving"): ListFact[A] = macro FactMacros.defineListFactMacroImpl[A]

  /**
    *
    */
  lazy val getFacts: Map[String, Fact[Any]] = {
    val declaredFields: Array[Field] = this.getClass.getDeclaredFields
    declaredFields
      .filter( field => classOf[Fact[Any]].isAssignableFrom( field.getType ) )
      .map( field => {
        field.setAccessible(true)
        val fact: Fact[Any] = field.get(this).asInstanceOf[Fact[Any]]
        (fact.name, fact)
      })
      .toMap
  }

}
