package nl.rabobank.rules.dsl.core.glossaries

import nl.rabobank.rules.facts.{Fact, ListFact, SingularFact}
import nl.rabobank.rules.utils.FactMacros

import scala.language.experimental.macros

/**
  * Utility base class for collecting and namespacing `Fact`s. You can extend this class, define facts in it and receive a
  * utility collection of all facts declared in your class.
  */
class Glossary {
  /**
    * Defines a `Fact`, using the name of the `val` it is assigned to as the name of the `Fact`. Note: the value of this
    * macro *must* be assigned to a `val`, otherwise a compiler error will be raised.
    *
    * @tparam A the value type of the resulting `Fact`.
    * @return a `SingularFact` initialized with the name of the val declaration.
    */
  def defineFact[A](): SingularFact[A] = macro FactMacros.defineFactMacroImpl[A]

  /**
    * Defines a `Fact`, using the name of the `val` it is assigned to as the name of the `Fact`. Note: the value of this
    * macro *must* be assigned to a `val`, otherwise a compiler error will be raised.
    *
    * @tparam A the value type of the resulting `Fact`.
    * @param description description of the `Fact`, to be passed along to the `Fact`'s constructor.
    * @return a `SingularFact` initialized with the name of the val declaration.
    */
  def defineFact[A](description: String): SingularFact[A] = macro FactMacros.defineFactMacroWithDescriptionImpl[A]

  /**
    * Defines a `Fact`, using the name of the `val` it is assigned to as the name of the `Fact`. Note: the value of this
    * macro *must* be assigned to a `val`, otherwise a compiler error will be raised.
    *
    * @tparam A the value type of the resulting `Fact`.
    * @return a `ListFact` initialized with the name of the val declaration.
    */
  def defineListFact[A](): ListFact[A] = macro FactMacros.defineListFactMacroImpl[A]

  /**
    * Defines a `Fact`, using the name of the `val` it is assigned to as the name of the `Fact`. Note: the value of this
    * macro *must* be assigned to a `val`, otherwise a compiler error will be raised.
    *
    * @tparam A the value type of the resulting `Fact`.
    * @param description description of the `Fact`, to be passed along to the `Fact`'s constructor.
    * @return a `ListFact` initialized with the name of the val declaration.
    */
  def defineListFact[A](description: String): ListFact[A] = macro FactMacros.defineListFactMacroWithDescriptionImpl[A]

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
