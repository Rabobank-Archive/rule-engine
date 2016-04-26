package org.scalarules.dsl.nl.grammar

import org.scalarules.engine._

//class DslGenericListAggregationOperation[A](listOperation: DslGenericListAggregator, condition: DslCondition, output: Fact[A], derivations: List[Derivation]) {
//  def van(operation: DslEvaluation[List[A]]): BerekeningAccumulator = {
//    val dslEvaluation: DslEvaluation[A] = new DslEvaluation[A](operation.condition, listOperation.toEvaluation(operation.evaluation))
//
//    new BerekeningAccumulator(condition, Specificatie(condition, output, dslEvaluation) :: derivations)
//  }
//}

trait DslGenericListAggregator {
  private[grammar] def toEvaluation[A](listEvaluation: Evaluation[List[A]]): Evaluation[A]

  def van[A](operation: DslEvaluation[List[A]]): DslEvaluation[A] = new DslEvaluation[A](operation.condition, toEvaluation(operation.evaluation))
}

class SelectElementOnLiteralIndex(index: Int) extends DslGenericListAggregator {
  private[grammar] def toEvaluation[A](listEvaluation: Evaluation[List[A]]): Evaluation[A] = new ListIndexSelectionEvaluation[A](listEvaluation, index)
}

// scalastyle:off object.name
object element {
  def apply(literal: Int): SelectElementOnLiteralIndex = new SelectElementOnLiteralIndex(literal)
}
// scalastyle:on object.name

