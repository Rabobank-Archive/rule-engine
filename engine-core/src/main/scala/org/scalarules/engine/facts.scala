package org.scalarules.engine

import scala.language.existentials

trait Fact[+A] {
  def name: String
  def description: String

  def toEval: Evaluation[A]
}

case class SingularFact[+A](name: String, description: String = "") extends Fact[A] {
  def toEval: Evaluation[A] = new SingularFactEvaluation(this)

  override def toString: String = name
}

case class ListFact[+A](name: String, description: String = "") extends Fact[List[A]] {
  def toEval: Evaluation[List[A]] = new ListFactEvaluation[A](this)

  override def toString: String = name
}
