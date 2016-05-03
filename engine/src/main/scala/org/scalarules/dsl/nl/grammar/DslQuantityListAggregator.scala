package org.scalarules.dsl.nl.grammar

import org.scalarules.finance.core.Quantity
import org.scalarules.engine.{Derivation, Evaluation, Fact}

trait DslQuantityListAggregator {
  private[grammar] def toEvaluation[A : Quantity](listEvaluation: Evaluation[List[A]]): Evaluation[A]

  def van[A : Quantity](operation: DslEvaluation[List[A]]): DslEvaluation[A] = new DslEvaluation[A](operation.condition, toEvaluation(operation.evaluation))
}

// scalastyle:off object.name
object totaal extends DslQuantityListAggregator {
  private[grammar] def toEvaluation[A : Quantity](listEvaluation: Evaluation[List[A]]): Evaluation[A] =
    new ListAggregationEvaluation[A](listEvaluation, _.reduceLeft((a, b) => implicitly[Quantity[A]].plus(a, b) ))
}

object substractie extends DslQuantityListAggregator {
  private[grammar] def toEvaluation[A : Quantity](listEvaluation: Evaluation[List[A]]): Evaluation[A] =
    new ListAggregationEvaluation[A](listEvaluation, _.reduceLeft((a, b) => implicitly[Quantity[A]].minus(a, b) ))
}

object gemiddelde extends DslQuantityListAggregator {
  private[grammar] def toEvaluation[A : Quantity](listEvaluation: Evaluation[List[A]]): Evaluation[A] = {
    val ev = implicitly[Quantity[A]]

    new ListAggregationEvaluation[A](listEvaluation, (lst: List[A]) => ev.divide(lst.reduceLeft( ev.plus ), lst.size))
  }
}
// scalastyle:on object.name

