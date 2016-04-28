package org.scalarules.dsl.nl

import org.scalarules.dsl.core.grammar.{DslConditionImplicits, DslEvaluationImplicits, PresentWord}
import org.scalarules.dsl.nl.grammar.datum.DatumImplicits
import org.scalarules.engine._

package object grammar
    extends DslConditionNLImplicits
    with DslConditionImplicits
    with DslEvaluationImplicits
    with DatumImplicits {

  type ConditionFunction = (Condition, Condition) => Condition

  val aanwezig = new PresentWord
  // Entrypoint for the DSL
  def Gegeven(condition: DslConditionNL): GegevenWord = new GegevenWord(condition) //scalastyle:ignore method.name

}
