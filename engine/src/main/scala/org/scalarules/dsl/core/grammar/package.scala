package org.scalarules.dsl.core

import org.scalarules.dsl.en.grammar.GivenWord
import org.scalarules.dsl.nl.grammar.datum.DatumImplicits
import org.scalarules.engine._

package object grammar extends DslConditionImplicits
    with DslEvaluationImplicits {

  type ConditionFunction = (Condition, Condition) => Condition

  // Entrypoint for the DSL
  def Given(condition: DslCondition): GivenWord = new GivenWord(condition) //scalastyle:ignore method.name
}
