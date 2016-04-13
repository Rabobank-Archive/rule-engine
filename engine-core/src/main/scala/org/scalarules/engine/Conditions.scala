package org.scalarules.engine

object Conditions {

  val trueCondition: Condition = (c: Context) => true

  def equalsCondition[A](lhs:Evaluation[A], rhs: Evaluation[A]): Condition = (c: Context) => (lhs(c), rhs(c)) match {
    case (Some(x), Some(y)) => x == y
    case _ => false
  }

  def andCondition(lhs: Condition, rhs: Condition): Condition = (c: Context) => lhs(c) && rhs(c)

  def orCondition(lhs: Condition, rhs: Condition): Condition = (c: Context) => lhs(c) || rhs(c)

}

