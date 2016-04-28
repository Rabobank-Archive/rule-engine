package org.scalarules.dsl

import org.scalarules.dsl.core.grammar.{DslConditionImplicits, DslEvaluationImplicits, PresentWord}

package object en
  extends DslConditionImplicits
  with DslEvaluationImplicits
{

  val present = new PresentWord
}
