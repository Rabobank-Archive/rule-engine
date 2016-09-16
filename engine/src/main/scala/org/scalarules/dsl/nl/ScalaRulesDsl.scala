package org.scalarules.dsl.nl

import org.scalarules.dsl.core.grammar.{DslCondition, DslConditionImplicits, DslEvaluationImplicits, absent, present}
import org.scalarules.dsl.nl.datum.DatumImplicits
import org.scalarules.dsl.nl.grammar._

/**
  * Aggregates the keywords and implicit definitions of the Scala-Rules DSL. The implicits available in this
  * trait can be used by importing the `grammar` package object's members, or extending this trait.
  */
trait ScalaRulesDsl extends AfrondingsWordsTrait
  with DslConditionNLImplicits
  with DslConditionImplicits
  with DslEvaluationImplicits
  with DatumImplicits
  with DslLoopWordTrait
  with DslListFilterWord
{

  val aanwezig = present
  val afwezig = absent

  // Entrypoint for the DSL
  def Gegeven(condition: DslCondition): GegevenWord = new GegevenWord(condition) //scalastyle:ignore method.name

}
