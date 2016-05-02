package org.scalarules.dsl.nl

import org.scalarules.dsl.nl.datum.DatumImplicits
import org.scalarules.dsl.nl.grammar.{DslCondition, DslConditionImplicits, DslEvaluationImplicits, GegevenWord}
import org.scalarules.engine._

trait ScalaRulesDsl extends DslConditionImplicits
  with DslEvaluationImplicits
  with DatumImplicits {

  type ConditionFunction = (Condition, Condition) => Condition

  // Entrypoint for the DSL
  def Gegeven(condition: DslCondition): GegevenWord = new GegevenWord(condition) //scalastyle:ignore method.name

}
