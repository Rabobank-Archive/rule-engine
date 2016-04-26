package org.scalarules.dsl.nl.grammar

import org.scalarules.engine.{Context, Evaluation, Fact}

class DslListFilter[A](toFilter: DslEvaluation[List[A]]) {
  def op(values: A*): DslEvaluation[List[A]] = op(values.contains(_))

  def op(filterOperation: A => Boolean): DslEvaluation[List[A]] = DslEvaluation(toFilter.condition, new Evaluation[List[A]]{
    override def apply(c: Context): Option[List[A]] = {
      toFilter.evaluation(c).map(_.filter(filterOperation))
    }
  })

}

object DslListFilter {
  var filter = new FilterWord
}

class FilterWord {
  def lijst[A](toFilter: DslEvaluation[List[A]]): DslListFilter[A] = new DslListFilter[A](toFilter)
}