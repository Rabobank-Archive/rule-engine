package org.scalarules.engine

import org.scalarules.facts.Fact

object Conditions {

  val trueCondition: Condition = (c: Context) => true

  def exists[A](fact: Fact[A]): Condition = c => fact.toEval.apply(c).isDefined
  def notExists[A](fact: Fact[A]): Condition = c => fact.toEval.apply(c).isEmpty

  def equalsCondition[A](lhs:Evaluation[A], rhs: Evaluation[A]): Condition = (c: Context) => (lhs(c), rhs(c)) match {
    case (Some(x), Some(y)) => x == y
    case _ => false
  }

  def andCondition(lhs: Condition, rhs: Condition): Condition = (c: Context) => lhs(c) && rhs(c)

  def orCondition(lhs: Condition, rhs: Condition): Condition = (c: Context) => lhs(c) || rhs(c)

}

