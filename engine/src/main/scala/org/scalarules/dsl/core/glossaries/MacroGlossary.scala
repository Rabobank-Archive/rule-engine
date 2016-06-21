package org.scalarules.utils

import java.lang.reflect.Field

import org.scalarules.engine.{Fact, ListFact, SingularFact}
import org.scalarules.finance.nl.Bedrag

import scala.language.experimental.macros

/**
  * Utility base class for collecting and namespacing `Fact`s. You can extend this class, define facts in it and receive a
  * utility collection of all facts declared in your class.
  */
class MacroGlossary {
  def defineFact[A](): SingularFact[A] = macro FactMacros.defineFactMacroImpl[A]
  def defineFact[A](description: String = "No description"): SingularFact[A] = macro FactMacros.defineFactMacroWithDescriptionImpl[A]

  def defineListFact[A](): ListFact[A] = macro FactMacros.defineListFactMacroImpl[A]
  def defineListFact[A](description: String = "No description"): ListFact[A] = macro FactMacros.defineListFactMacroWithDescriptionImpl[A]

  /**
    * Collects all declared `Fact`s in this `Glossary` and returns them mapped from their names to their definitions.
    */
  lazy val facts: Map[String, Fact[Any]] = {
    this.getClass.getDeclaredFields
      .filter( field => classOf[Fact[Any]].isAssignableFrom( field.getType ) )
      .map( field => {
        field.setAccessible(true)
        val fact: Fact[Any] = field.get(this).asInstanceOf[Fact[Any]]
        (fact.name, fact)
      })
      .toMap
  }

}
