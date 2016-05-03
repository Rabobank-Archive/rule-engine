package org.scalarules.dsl.nl

import org.scalarules.dsl.nl.datum.DatumImplicits
import org.scalarules.dsl.nl.grammar.{DslCondition, DslConditionImplicits, DslEvaluationImplicits, DslListFilterWord, DslLoopWordTrait, GegevenWord}
import org.scalarules.engine._

/**
  * Aggregates the keywords and implicit definitions of the Scala-Rules DSL. The implicits available in this
  * trait can be used by importing the `grammar` package object's members, or extending this trait.
  */
trait ScalaRulesDsl extends DslConditionImplicits
  with DslEvaluationImplicits
  with DatumImplicits
  with DslLoopWordTrait
  with DslListFilterWord
{

  type ConditionFunction = (Condition, Condition) => Condition

  // Entrypoint for the DSL
  def Gegeven(condition: DslCondition): GegevenWord = new GegevenWord(condition) //scalastyle:ignore method.name

}
