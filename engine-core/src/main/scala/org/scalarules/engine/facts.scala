package org.scalarules.engine

import scala.annotation.tailrec
import scala.language.existentials
import scala.reflect.runtime.universe._

trait Fact[+A] {
  def name: String
  def isResult: Boolean
  def description: String

  def toFunc: Evaluation[A]
  def valueClass: Class[_]
}

case class SingularFact[+A : TypeTag](name: String, isResult: Boolean = true, description: String = "") extends Fact[A] {
  def toFunc: Evaluation[A] = new SingularFactEvaluation(this)

  override def toString: String = name

  override def valueClass: Class[_] = {
    val tag = implicitly[TypeTag[A]]

    tag.mirror.runtimeClass(tag.tpe)
  }
}

case class ListFact[+A : TypeTag](name: String, isResult: Boolean = true, description: String = "") extends Fact[List[A]] {
  def toFunc: Evaluation[List[A]] = new ListFactEvaluation[A](this)

  override def toString: String = name

  override def valueClass: Class[_] = {
    val tag = implicitly[TypeTag[A]]

    tag.mirror.runtimeClass(resolveTypeTagToElementType(tag.tpe))
  }

  @tailrec
  private def resolveTypeTagToElementType(tpe: Type): Type = tpe.typeArgs match {
    case t :: ts => resolveTypeTagToElementType(t)
    case Nil => tpe
  }
}
