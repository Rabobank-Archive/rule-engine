package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.types.NumberLike
import org.scalarules.engine.{Derivation, Evaluation, Fact}

trait DslNumberLikeListAggregator {
  private[grammar] def toEvaluation[A : NumberLike](listEvaluation: Evaluation[List[A]]): Evaluation[A]

  def van[A : NumberLike](operation: DslEvaluation[List[A]]): DslEvaluation[A] = new DslEvaluation[A](operation.condition, toEvaluation(operation.evaluation))
}

// scalastyle:off object.name
object totaal extends DslNumberLikeListAggregator {
  private[grammar] def toEvaluation[A : NumberLike](listEvaluation: Evaluation[List[A]]): Evaluation[A] =
    new ListAggregationEvaluation[A](listEvaluation, _.reduceLeft((a, b) => implicitly[NumberLike[A]].plus(a, b) ))
}

object substractie extends DslNumberLikeListAggregator {
  private[grammar] def toEvaluation[A : NumberLike](listEvaluation: Evaluation[List[A]]): Evaluation[A] =
    new ListAggregationEvaluation[A](listEvaluation, _.reduceLeft((a, b) => implicitly[NumberLike[A]].minus(a, b) ))
}

object gemiddelde extends DslNumberLikeListAggregator {
  private[grammar] def toEvaluation[A : NumberLike](listEvaluation: Evaluation[List[A]]): Evaluation[A] = {
    val ev = implicitly[NumberLike[A]]

    new ListAggregationEvaluation[A](listEvaluation, (lst: List[A]) => ev.divide(lst.reduceLeft( ev.plus ), lst.size))
  }
}
// scalastyle:on object.name

