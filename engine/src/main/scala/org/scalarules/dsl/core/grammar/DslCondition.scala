package org.scalarules.dsl.core.grammar

import org.scalarules.dsl.core.grammar.DslCondition._
import org.scalarules.engine._

import scala.language.implicitConversions

//scalastyle:off method.name

case class DslCondition(facts: Set[Fact[Any]], condition: Condition) {
  def &[T](rhs: Fact[T]): DslConditionPart[T] = DslConditionPart(this, rhs, andPredicate)
  def &(rhs: DslCondition): DslCondition = combine(this, rhs, andPredicate)

  def |[T](rhs: Fact[T]): DslConditionPart[T] = DslConditionPart(this, rhs, orPredicate)
  def |(rhs: DslCondition): DslCondition = combine(this, rhs, orPredicate)

  private def combine(lhs: DslCondition, rhs: DslCondition, predicate: ConditionFunction): DslCondition =
    DslCondition(lhs.facts ++ rhs.facts, predicate(lhs.condition, rhs.condition))
}

object DslCondition {
  val andPredicate: ConditionFunction = (l, r) => c => l(c) && r(c)
  val orPredicate: ConditionFunction = (l, r) => c => l(c) || r(c)

  val emptyTrueCondition: DslCondition = DslCondition(Set(), _ => true)
  def factFilledCondition[A](fact: Fact[A]): DslCondition = DslCondition(Set(fact), Conditions.exists(fact))

  def andCombineConditions(initialDslCondition: DslCondition, dslConditions: DslCondition*): DslCondition = dslConditions.foldLeft(initialDslCondition)(_ & _)
  def orCombineConditions(initialDslCondition: DslCondition, dslConditions: DslCondition*): DslCondition = dslConditions.foldLeft(initialDslCondition)(_ | _)
}

trait DslConditionImplicits {
  implicit def toConditionDslPart[T](factDef : Fact[T]): DslConditionPart[T] = DslConditionPart(emptyTrueCondition, factDef, andPredicate)
  implicit def dslEvaluationToConditionDslPart[T](dslEvaluation: DslEvaluation[T]): DslEvaluationConditionPart[T] = DslEvaluationConditionPart(emptyTrueCondition, dslEvaluation, andPredicate)
}
