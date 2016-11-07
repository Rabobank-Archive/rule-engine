package org.scalarules.dsl.en

import org.scalarules.dsl.core.grammar.{DslCondition, DslConditionImplicits, DslEvaluationImplicits}
import org.scalarules.dsl.en.date.DateImplicits
import org.scalarules.dsl.en.grammar._
import org.scalarules.dsl.en.grammar.meta.DslMacros

import scala.language.experimental.macros

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
  def Given(condition: DslCondition): GivenWord = macro DslMacros.captureGivenSourcePositionMacroImpl //scalastyle:ignore method.name

  val results = new ResultsWord

  val Input = new InputWord
  val Output = new OutputWord

}
