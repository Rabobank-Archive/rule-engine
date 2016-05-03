package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.operators.{Addable, Divisible, Subtractable}
import org.scalarules.engine.Evaluation

// scalastyle:off object.name
object totaal {
  private[grammar] def toEvaluation[A](listEvaluation: Evaluation[List[A]], ev: Addable[A, A, A]): Evaluation[A] =
    new ListAggregationEvaluation[A](listEvaluation, _.reduceLeft((a, b) => ev.operation(a, b) ))

  def van[A](operation: DslEvaluation[List[A]])(implicit ev: Addable[A, A, A]): DslEvaluation[A] = new DslEvaluation[A](operation.condition, toEvaluation(operation.evaluation, ev))
}

object substractie {
  private[grammar] def toEvaluation[A](listEvaluation: Evaluation[List[A]], ev: Subtractable[A, A, A]): Evaluation[A] =
    new ListAggregationEvaluation[A](listEvaluation, _.reduceLeft((a, b) => ev.operation(a, b) ))

  def van[A](operation: DslEvaluation[List[A]])(implicit ev: Subtractable[A, A, A]): DslEvaluation[A] = new DslEvaluation[A](operation.condition, toEvaluation(operation.evaluation, ev))
}

object gemiddelde {
  private[grammar] def toEvaluation[A](listEvaluation: Evaluation[List[A]], evAddable: Addable[A, A, A], evDivisible: Divisible[A, Int, A]): Evaluation[A] =
    new ListAggregationEvaluation[A](listEvaluation, (lst: List[A]) => evDivisible.operation(lst.reduceLeft( evAddable.operation ), lst.size))

  def van[A](operation: DslEvaluation[List[A]])(implicit evAddable: Addable[A, A, A], evDivisible: Divisible[A, Int, A]): DslEvaluation[A] =
    new DslEvaluation[A](operation.condition, toEvaluation(operation.evaluation, evAddable, evDivisible))
}
// scalastyle:on object.name

