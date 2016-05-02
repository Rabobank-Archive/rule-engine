package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.projections.ProjectedDslEvaluation
import org.scalarules.engine.{Context, Evaluation}

trait DslListFilterWord {
  var filter = new FilterWord
}

class DslListFilter[A](toFilter: DslEvaluation[List[A]]) {
  def op(value: A, values: A*): DslEvaluation[List[A]] = op(value :: values.toList)
  def op(values: Seq[A]): DslEvaluation[List[A]] = op(values.contains(_))

  def op(filterOperation: A => Boolean): DslEvaluation[List[A]] = DslEvaluation(toFilter.condition, new Evaluation[List[A]]{
    override def apply(c: Context): Option[List[A]] = toFilter.evaluation(c).map(_.filter(filterOperation))
  })

  def op[B](projectedEvaluation: ProjectedDslEvaluation[A, B]): ComplexFilterWord[A, B] = new ComplexFilterWord[A, B](toFilter, projectedEvaluation)
}

class ComplexFilterWord[A, B](toFilter: DslEvaluation[List[A]], projectedEvaluation: ProjectedDslEvaluation[A, B]) {
  def van(value: B, values: B*): DslEvaluation[List[A]] = van(value :: values.toList)
  def van(values: Seq[B]): DslEvaluation[List[A]] = van(values.contains(_))

  def van(filterOperation: B => Boolean): DslEvaluation[List[A]] = DslEvaluation(toFilter.condition, new Evaluation[List[A]]{
    override def apply(c: Context): Option[List[A]] = toFilter.evaluation(c).map(_.filter(a => filterOperation(projectedEvaluation.transform(a))))
  })
}

class FilterWord {
  def lijst[A](toFilter: DslEvaluation[List[A]]): DslListFilter[A] = new DslListFilter[A](toFilter)
}
