package org.scalarules.engine

import scala.annotation.tailrec
import scala.language.existentials
import scala.reflect.runtime.universe._

trait Fact[+A] {
  def name: String
  def isResult: Boolean
  def description: String

  def toEval: Evaluation[A]
}

case class SingularFact[+A : TypeTag](name: String, isResult: Boolean = true, description: String = "") extends Fact[A] {
  def toEval: Evaluation[A] = new SingularFactEvaluation(this)

  override def toString: String = name
}

case class ListFact[+A : TypeTag](name: String, isResult: Boolean = true, description: String = "") extends Fact[List[A]] {
  def toEval: Evaluation[List[A]] = new ListFactEvaluation[A](this)

  override def toString: String = name
}
