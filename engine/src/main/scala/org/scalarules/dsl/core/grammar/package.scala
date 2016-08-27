package org.scalarules.dsl.core

import org.scalarules.engine._

package object grammar extends DslConditionImplicits
    with DslEvaluationImplicits {

  type ConditionFunction = (Condition, Condition) => Condition

}
