package nl.rabobank.rules.facts

import nl.rabobank.rules.engine.{ErrorEvaluation, Evaluation, ListFactEvaluation, SingularFactEvaluation}

import scala.language.existentials

trait Fact[+A] {
  def name: String
  def description: String

  def toEval: Evaluation[A]

  def valueType: String

  override def toString: String = name
}

case class SingularFact[+A](name: String, description: String = "", valueType: String = "") extends Fact[A] {
  def toEval: Evaluation[A] = new SingularFactEvaluation(this)
}

case class ListFact[+A](name: String, description: String = "", valueType: String = "") extends Fact[List[A]] {
  def toEval: Evaluation[List[A]] = new ListFactEvaluation[A](this)
}

case object OriginFact extends Fact[Nothing] {
  def name: String = "___meta___OriginFact___meta___"
  def description: String = "Meta-fact used in graph construction"

  def toEval: Evaluation[Nothing] = new ErrorEvaluation("The OriginFact is a meta-fact used in graph construction to indicate top-level constant evaluations")

  def valueType: String = "Nothing"
}

case class SynthesizedFact[+A](factOriginalFact: Fact[Any], synthesizedPostfix: String, description: String = "", valueType: String = "") extends Fact[A] {
  def name: String = factOriginalFact.name + "_" + synthesizedPostfix

  def toEval: Evaluation[A] = new SingularFactEvaluation[A](this)
}
