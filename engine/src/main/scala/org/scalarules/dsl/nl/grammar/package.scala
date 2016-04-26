package org.scalarules.dsl.nl

import org.scalarules.dsl.nl.grammar.date.DslDatumImplicits
import org.scalarules.engine._

package object grammar extends DslConditionImplicits
    with DslEvaluationImplicits
    with DslDatumImplicits {

  type ConditionFunction = (Condition, Condition) => Condition

  // Entrypoint for the DSL
  def Gegeven(condition: DslCondition): GegevenWord = new GegevenWord(condition) //scalastyle:ignore method.name

}
