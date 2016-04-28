package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.{DslEvaluation, ListIndexSelectionEvaluation}
import org.scalarules.engine._

trait DslGenericListAggregator {
  private[grammar] def toEvaluation[A](listEvaluation: Evaluation[List[A]]): Evaluation[A]

  def of[A](operation: DslEvaluation[List[A]]): DslEvaluation[A] = new DslEvaluation[A](operation.condition, toEvaluation(operation.evaluation))
}

class SelectElementOnLiteralIndex(index: Int) extends DslGenericListAggregator {
  private[grammar] def toEvaluation[A](listEvaluation: Evaluation[List[A]]): Evaluation[A] = new ListIndexSelectionEvaluation[A](listEvaluation, index)
}

// scalastyle:off object.name
object element {
  def apply(literal: Int): SelectElementOnLiteralIndex = new SelectElementOnLiteralIndex(literal)
}
// scalastyle:on object.name

