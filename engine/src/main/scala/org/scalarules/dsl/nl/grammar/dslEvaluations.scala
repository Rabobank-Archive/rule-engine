package org.scalarules.dsl.nl.grammar

import DslCondition._
import org.scalarules.finance.nl.Bedrag
import org.scalarules.engine._
import org.scalarules.facts.Fact

//scalastyle:off object.name

/* ********************************************************************************************************************************************************** *
 * This file contains the implicit objects which are part of the DSL. Note that they all return instances of various Evaluation classes. These extensions of  *
 * Evaluation are defined in operationEvaluations.scala and contain the actual implementations of each operation. If you wish to add an operation to the DSL, *
 * please add its DSL-part to this file and its implementation to the operationEvaluations.scala file.                                                        *
 * ************************************************************************************************************************************************************/

object eerste {
  def apply[A](facts: Fact[A]*): DslEvaluation[A] = {
    val condition = facts.map(f => factFilledCondition(f)).reduceLeft((b, a) => orCombineConditions(b, a))

    DslEvaluation[A](condition, new EersteEvaluation(facts))
  }

  def elementVan[A](fact: Fact[List[A]]): DslEvaluation[A] = {
    val condition = factFilledCondition(fact)

    DslEvaluation[A](condition, new EersteElementEvaluation[A](fact))
  }
}

sealed trait ReducableDslOperation {
  /**
    * @return name of the operation, used to reconstruct the expression for documentation purposes.
    */
  protected def operationName: String

  private[grammar] def operation[A](func: (A, A) => A, facts: Seq[Fact[A]]): DslEvaluation[A] = {
    val condition = facts.map(f => factFilledCondition(f)).reduceLeft((b, a) => andCombineConditions(b, a))

    DslEvaluation[A](condition, new ReducableEvaluation(operationName, func, facts))
  }
}

object minimum extends ReducableDslOperation {
  override def operationName = "minimum"

  def apply[A: Numeric](facts: Fact[A]*): DslEvaluation[A] = {
    implicit val ev = implicitly[Numeric[A]]
    operation(ev.min, facts)
  }
}

object maximum extends ReducableDslOperation {
  override def operationName = "maximum"

  def apply[A: Numeric](facts: Fact[A]*): DslEvaluation[A] = {
    implicit val ev = implicitly[Numeric[A]]
    operation(ev.max, facts)
  }
}

object gecombineerdMaximum {
  def apply[A : Ordering](facts: Fact[List[A]]*): DslEvaluation[List[A]] = {
    val condition = facts.map(f => factFilledCondition(f)).reduceLeft((b, a) => andCombineConditions(b, a))

    DslEvaluation(condition, new Evaluation[List[A]] {
      override def apply(c: Context): Option[List[A]] = Some(facts.map( _.toEval(c).get ).transpose.map( _.max ).toList)
    })
  }
}

object gecombineerdMinimum {
  def apply[A : Ordering](facts: Fact[List[A]]*): DslEvaluation[List[A]] = {
    val condition = facts.map(f => factFilledCondition(f)).reduceLeft((b, a) => andCombineConditions(b, a))

    DslEvaluation(condition, new Evaluation[List[A]] {
      override def apply(c: Context): Option[List[A]] = Some(facts.map( _.toEval(c).get ).transpose.map( _.min ).toList)
    })
  }
}

object als {
  def apply[A](dslCondition: DslCondition): AlsResult[A] = new AlsResult(dslCondition)

  class AlsResult[A] private[grammar](alsCondition: DslCondition) {
    def dan(eval: DslEvaluation[A]): DanResult[A] = new DanResult(alsCondition, eval)
  }

  class DanResult[A]private[grammar](alsCondition: DslCondition, danEvaluation: DslEvaluation[A]) {
    def anders(andersEvaluation: DslEvaluation[A]) : DslEvaluation[A] = {
      val facts = alsCondition.facts ++ danEvaluation.condition.facts ++ andersEvaluation.condition.facts
      val condition = facts.map(f => factFilledCondition(f)).foldLeft(emptyTrueCondition)((l, r) => andCombineConditions(l, r))
      val evaluation = new AlsDanElseEvaluation(alsCondition.condition, danEvaluation.evaluation, andersEvaluation.evaluation)

      DslEvaluation(condition, evaluation)
    }
  }
}

object AfgekaptOp100Euro {
  def apply(eval: DslEvaluation[Bedrag]): DslEvaluation[Bedrag] = {
    DslEvaluation(eval.condition,
      new Evaluation[Bedrag] {
        override def apply(c: Context): Option[Bedrag] = eval.evaluation(c) match {
          case Some(x) => Some(x.afgekaptOp100Euro)
          case _ => None
        }
      }
    )
  }
}
