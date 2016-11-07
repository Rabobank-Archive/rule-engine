package org.scalarules.dsl.nl

import org.scalarules.dsl.core.grammar.{DslConditionImplicits, DslEvaluationImplicits, absent, present}
import org.scalarules.dsl.nl.datum.DatumImplicits
import org.scalarules.dsl.nl.grammar._
import org.scalarules.dsl.nl.grammar.meta.DslMacros

import scala.language.experimental.macros

/**
  * Aggregates the keywords and implicit definitions of the Scala-Rules DSL. The implicits available in this
  * trait can be used by importing the `grammar` package object's members, or extending this trait.
  */
trait ScalaRulesDsl extends AfrondingsWordsTrait
  with DslConditionNLImplicits
  with DslConditionImplicits
  with DslEvaluationImplicits
  with DatumImplicits
  with DslListFilterWord
{

  val aanwezig = present
  val afwezig = absent

  // Entrypoint for the DSL
  def Gegeven(condition: DslConditionNL): GegevenWord = macro DslMacros.captureGegevenSourcePositionMacroImpl //scalastyle:ignore method.name

  val resultaten = new ResultatenWord

  val Invoer = new InvoerWord
  val Uitvoer = new UitvoerWord
}

