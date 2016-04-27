package org.scalarules.dsl.nl

import org.scalarules.dsl.nl.grammar.datum.DatumImplicits
import org.scalarules.engine._

package object grammar extends DslConditionImplicits
    with DslEvaluationImplicits
    with DatumImplicits {

  type ConditionFunction = (Condition, Condition) => Condition

  // Entrypoint for the DSL
  def Gegeven(condition: DslCondition): GegevenWord = new GegevenWord(condition) //scalastyle:ignore method.name

}
