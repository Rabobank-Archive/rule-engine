package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.nl.grammar.DslCondition._
import org.scalarules.engine._
import org.scalarules.utils.{SourcePosition, SourceUnknown}

import scala.language.implicitConversions

case class DslCondition(facts: Set[Fact[Any]], condition: Condition, sourcePosition: SourcePosition = SourceUnknown()) {
  def en[T](rhs: Fact[T]): DslConditionPart[T] = DslConditionPart(this, rhs, andPredicate)
  def en(rhs: DslCondition): DslCondition = combine(this, rhs, andPredicate)

  def of[T](rhs: Fact[T]): DslConditionPart[T] = DslConditionPart(this, rhs, orPredicate)
  def of(rhs: DslCondition): DslCondition = combine(this, rhs, orPredicate)

  private def combine(lhs: DslCondition, rhs: DslCondition, predicate: ConditionFunction): DslCondition =
    DslCondition(lhs.facts ++ rhs.facts, predicate(lhs.condition, rhs.condition))
}

object DslCondition {
  val andPredicate: ConditionFunction = (l, r) => c => l(c) && r(c)
  val orPredicate: ConditionFunction = (l, r) => c => l(c) || r(c)

  val emptyTrueCondition: DslCondition = DslCondition(Set(), _ => true)
  def factFilledCondition[A](fact: Fact[A]): DslCondition = DslCondition(Set(fact), Conditions.exists(fact))

  def andCombineConditions(initialDslCondition: DslCondition, dslConditions: DslCondition*): DslCondition = dslConditions.foldLeft(initialDslCondition)(_ en _)
  def orCombineConditions(initialDslCondition: DslCondition, dslConditions: DslCondition*): DslCondition = dslConditions.foldLeft(initialDslCondition)(_ of _)
}

trait DslConditionImplicits {
  implicit def toConditionDslPart[T](factDef : Fact[T]): DslConditionPart[T] = DslConditionPart(emptyTrueCondition, factDef, andPredicate)
  implicit def dslEvaluationToConditionDslPart[T](dslEvaluation: DslEvaluation[T]): DslEvaluationConditionPart[T] = DslEvaluationConditionPart(emptyTrueCondition, dslEvaluation, andPredicate)
  val altijd: DslCondition = emptyTrueCondition
}
