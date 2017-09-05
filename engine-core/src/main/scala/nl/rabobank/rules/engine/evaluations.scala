package nl.rabobank.rules.engine

import nl.rabobank.rules.facts.Fact

trait Evaluation[+A] {
  def apply(c: Context): Option[A]

  def asListEvaluation: Evaluation[List[A]] = new ListEvaluationWrapper(this)
}

class NoopEvaluation[+A] extends Evaluation[A] {
  def apply(c: Context): Option[A] = None

  override def toString: String = "None"
}

class ErrorEvaluation[+A](val message: String) extends Evaluation[A] {
  def apply(c: Context): Option[A] = throw new IllegalStateException(message)

  override def toString: String = "Error : " + message
}

class ListEvaluationWrapper[+A](wrappee: Evaluation[A]) extends Evaluation[List[A]] {
  def apply(c: Context): Option[List[A]] = wrappee(c) match {
    case Some(x) => Some(List(x))
    case None => None
  }
}

class ConstantValueEvaluation[+A](v: A) extends Evaluation[A] {
  def apply(c: Context): Option[A] = Some(v)

  override def toString: String = v.toString
}

class SingularFactEvaluation[+A](fact: Fact[A]) extends Evaluation[A] {
  def apply(c: Context): Option[A] = c.get(fact).asInstanceOf[Option[A]]

  override def toString: String = fact.name
}

class ListFactEvaluation[+A](fact: Fact[List[A]]) extends Evaluation[List[A]] {
  def apply(c: Context): Option[List[A]] = c.get(fact).asInstanceOf[Option[List[A]]]

  override def toString: String = fact.name
}

class ProjectionEvaluation[-A, +B](src: Evaluation[A], f: A => B) extends Evaluation[B] {
  override def apply(c: Context): Option[B] = src(c) match {
    case Some(x) => Some(f(x))
    case None => None
  }
}

class ProjectionListEvaluation[-A, +B](src: Evaluation[List[A]], f: A => B) extends Evaluation[List[B]] {
  override def apply(c: Context): Option[List[B]] = src(c) match {
    case Some(x) => Some(x.map(f))
    case None => None
  }
}
