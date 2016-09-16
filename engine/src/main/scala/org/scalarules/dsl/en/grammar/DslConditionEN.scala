package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.{DslCondition, DslConditionPart}
import org.scalarules.engine._

import scala.language.implicitConversions

class DslConditionEN private[grammar] (fs: Set[Fact[Any]], c: Condition) extends DslCondition(fs, c){
  def and[T](rhs: Fact[T]): DslConditionPart[T] = &(rhs)
  def and(rhs: DslCondition): DslCondition = &(rhs)

  def or[T](rhs: Fact[T]): DslConditionPart[T] = |(rhs)
  def or(rhs: DslCondition): DslCondition = |(rhs)
}

trait DslConditionENImplicits {
  implicit def toConditionDslNL[T](dslCondition : DslCondition): DslConditionEN = new DslConditionEN(dslCondition.facts, dslCondition.condition)
  val always: DslConditionEN = toConditionDslNL(DslCondition.emptyTrueCondition)
}
