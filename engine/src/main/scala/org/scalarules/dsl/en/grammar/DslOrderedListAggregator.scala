package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.{DslEvaluation, ListAggregationEvaluation}
import org.scalarules.engine.Evaluation

trait DslOrderedListAggregator {
  private[grammar] def toEvaluation[A : Ordering](listEvaluation: Evaluation[List[A]]): Evaluation[A]

  def of[A : Ordering](operation: DslEvaluation[List[A]]): DslEvaluation[A] = new DslEvaluation[A](operation.condition, toEvaluation(operation.evaluation))
}

// scalastyle:off object.name
object lowest extends DslOrderedListAggregator {
  private[grammar] def toEvaluation[A : Ordering](listEvaluation: Evaluation[List[A]]): Evaluation[A] = new ListAggregationEvaluation[A](listEvaluation, _.min)
}

object highest extends DslOrderedListAggregator {
  private[grammar] def toEvaluation[A : Ordering](listEvaluation: Evaluation[List[A]]): Evaluation[A] = new ListAggregationEvaluation[A](listEvaluation, _.max)
}
// scalastyle:on object.name

