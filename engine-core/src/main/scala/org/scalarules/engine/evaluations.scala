package org.scalarules.engine

trait Evaluation[+A] {
  def apply(c: Context): Option[A]

  def asListEvaluation: Evaluation[List[A]] = new ListEvaluationWrapper(this)
}

class NoopEvaluation[+A] extends Evaluation[A] {
  def apply(c: Context): Option[A] = None

  override def toString: String = "None"
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
