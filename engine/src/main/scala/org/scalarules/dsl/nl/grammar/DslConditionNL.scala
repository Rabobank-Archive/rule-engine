package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.grammar.{DslCondition, DslConditionPart}
import org.scalarules.engine._

import scala.language.implicitConversions

class DslConditionNL private[grammar] (fs: Set[Fact[Any]], c: Condition) extends DslCondition(fs, c){
  def en[T](rhs: Fact[T]): DslConditionPart[T] = &(rhs)
  def en(rhs: DslCondition): DslCondition = &(rhs)

  def of[T](rhs: Fact[T]): DslConditionPart[T] = |(rhs)
  def of(rhs: DslCondition): DslCondition = |(rhs)
}

trait DslConditionNLImplicits {
  implicit def toConditionDslNL[T](dslCondition : DslCondition): DslConditionNL = new DslConditionNL(dslCondition.facts, dslCondition.condition)
  val altijd: DslConditionNL = toConditionDslNL(DslCondition.emptyTrueCondition)
}
