package nl.rabobank.rules.dsl.nl

import nl.rabobank.rules.dsl.nl.datum.DatumImplicits
import nl.rabobank.rules.dsl.nl.grammar._
import nl.rabobank.rules.dsl.nl.grammar.meta.DslMacros
import nl.rabobank.rules.engine._

import scala.language.experimental.macros

/**
  * Aggregates the keywords and implicit definitions of the Rabo-Rules DSL. The implicits available in this
  * trait can be used by importing the `grammar` package object's members, or extending this trait.
  */
trait RaboRulesDsl extends AfrondingsWordsTrait
  with DslConditionImplicits
  with DslEvaluationImplicits
  with DatumImplicits
  with DslListFilterWord
{

  type ConditionFunction = (Condition, Condition) => Condition

  // Entrypoint for the DSL
  def Gegeven(condition: DslCondition): GegevenWord = macro DslMacros.captureGegevenSourcePositionMacroImpl //scalastyle:ignore method.name

  val resultaten = new ResultatenWord

  val Invoer = new InvoerWord
  val Uitvoer = new UitvoerWord
}

