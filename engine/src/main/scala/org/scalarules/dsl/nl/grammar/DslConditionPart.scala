package org.scalarules.dsl.nl.grammar

import DslCondition._
import org.scalarules.engine._

import scala.reflect.ClassTag

//scalastyle:off method.name object.name

sealed trait Aanwezigheid
object aanwezig extends Aanwezigheid

sealed trait DslConditionComparators[T] {

  def is(value: T): DslCondition = combineWith(c => lhsEvaluation(c) contains value)
  def is(value: Fact[T]): DslCondition = combineWith(c => lhsEvaluation(c) == value.toEval(c))

  def >[A <: T : Ordering : ClassTag](value: A): DslCondition = combineWith(compareWithValue(value, implicitly[Ordering[A]].gt))
  def >[A <: T : Ordering : ClassTag](value: Fact[A]): DslCondition = combineWith(compareWithEvaluation(value.toEval, implicitly[Ordering[A]].gt))
  def >=[A <: T : Ordering : ClassTag](value: A): DslCondition = combineWith(compareWithValue(value, implicitly[Ordering[A]].gteq))
  def >=[A <: T : Ordering : ClassTag](value: Fact[A]): DslCondition = combineWith(compareWithEvaluation(value.toEval, implicitly[Ordering[A]].gteq))

  def <[A <: T : Ordering : ClassTag](value: A): DslCondition = combineWith(compareWithValue(value, implicitly[Ordering[A]].lt))
  def <[A <: T : Ordering : ClassTag](value: Fact[A]): DslCondition = combineWith(compareWithEvaluation(value.toEval, implicitly[Ordering[A]].lt))
  def <=[A <: T : Ordering : ClassTag](value: A): DslCondition = combineWith(compareWithValue(value, implicitly[Ordering[A]].lteq))
  def <=[A <: T : Ordering : ClassTag](value: Fact[A]): DslCondition = combineWith(compareWithEvaluation(value.toEval, implicitly[Ordering[A]].lteq))

  private[grammar] def lhsEvaluation: Evaluation[T]
  private[grammar] def combineWith(condition: Condition): DslCondition

  private def compareWithValue[A <: T : Ordering : ClassTag](rhsValue: A, op: (A, A) => Boolean): Condition =
    c => lhsEvaluation(c) match {
      case Some(x: A) => op(x, rhsValue)
      case _ => false // throw new exception?
    }

  private def compareWithEvaluation[A <: T : Ordering : ClassTag](rhsEvaluation: Evaluation[A], op: (A, A) => Boolean): Condition =
    c => (lhsEvaluation(c), rhsEvaluation(c)) match {
      case (Some(x: A), Some(y: A)) => op(x, y)
      case _ => false // throw new exception?
    }
}

case class DslEvaluationConditionPart[T] private[grammar](oldPart: DslCondition, dslEvaluation: DslEvaluation[T], combineMethod: ConditionFunction) extends DslConditionComparators[T] {

  override private[grammar] def lhsEvaluation: Evaluation[T] = dslEvaluation.evaluation
  override private[grammar] def combineWith(condition: Condition): DslCondition = DslCondition(oldPart.facts ++ dslEvaluation.condition.facts, combineMethod(oldPart.condition, condition))
}

case class DslConditionPart[T] private[grammar](oldPart: DslCondition, fact: Fact[T], combineMethod: ConditionFunction) extends DslConditionComparators[T] {

  override private[grammar] def lhsEvaluation: Evaluation[T] = fact.toEval
  override private[grammar] def combineWith(condition: Condition): DslCondition = DslCondition(oldPart.facts + fact, combineMethod(oldPart.condition, condition))

  def is(value: Aanwezigheid): DslCondition = combineWith(Conditions.exists(fact))

}
