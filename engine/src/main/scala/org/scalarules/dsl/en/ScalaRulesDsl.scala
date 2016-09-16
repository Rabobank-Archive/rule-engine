package org.scalarules.dsl.en

import org.scalarules.dsl.core.grammar.{DslCondition, DslConditionImplicits, DslEvaluationImplicits, absent, present}
import org.scalarules.dsl.en.date.DateImplicits
import org.scalarules.dsl.en.grammar._
import org.scalarules.engine._

/**
  * Aggregates the keywords and implicit definitions of the Scala-Rules DSL. The implicits available in this
  * trait can be used by importing the `grammar` package object's members, or extending this trait.
  */
trait ScalaRulesDsl extends /*AfrondingsWordsTrait*/
  /*with */DslConditionENImplicits
  with DslConditionImplicits
  with DslEvaluationImplicits
  with DateImplicits
  /*with DslLoopWordTrait
  with DslListFilterWord*/
{

  // Entrypoint for the DSL
  def Given(condition: DslCondition): GivenWord = new GivenWord(condition) //scalastyle:ignore method.name

}
