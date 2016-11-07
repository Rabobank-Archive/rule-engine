package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.grammar.DslEvaluation
import org.scalarules.dsl.core.projections.ProjectedDslEvaluation
import org.scalarules.engine.{Context, Evaluation}

trait DslListFilterWord {
  val filter = new FilterWord
}

class FilterWord {
  def lijst[A](toFilter: DslEvaluation[List[A]]): DslListFilter[A] = new DslListFilter[A](toFilter)
}

class DslListFilter[A](toFilter: DslEvaluation[List[A]]) extends DslFilterListTrait[A, A]{
  def op(value: A, values: A*): DslEvaluation[List[A]] = op(value :: values.toList)
  def op(values: Seq[A]): DslEvaluation[List[A]] = filter(toFilter, identity, values.contains(_))

  def op(filterOperation: A => Boolean): DslEvaluation[List[A]] = filter(toFilter, identity, filterOperation)

  def op[B](projectedEvaluation: ProjectedDslEvaluation[A, B]): ComplexFilterWord[A, B] = new ComplexFilterWord[A, B](toFilter, projectedEvaluation.transform)
}

class ComplexFilterWord[A, B](toFilter: DslEvaluation[List[A]], transform: A => B) extends DslFilterListTrait[A, B]{
  def van(value: B, values: B*): DslEvaluation[List[A]] = van(value :: values.toList)
  def van(values: Seq[B]): DslEvaluation[List[A]] = filter(toFilter, transform, values.contains(_))

  def van(filterOperation: B => Boolean): DslEvaluation[List[A]] = filter(toFilter, transform, filterOperation)
}

sealed trait DslFilterListTrait[A, B] {
  protected def filter(toFilter: DslEvaluation[List[A]], transform: A => B, filterOp: B => Boolean): DslEvaluation[List[A]] =
    DslEvaluation(toFilter.condition, new Evaluation[List[A]] {
      override def apply(c: Context): Option[List[A]] = toFilter.evaluation(c).map(_.filter(value => filterOp(transform(value))))
    })
}
