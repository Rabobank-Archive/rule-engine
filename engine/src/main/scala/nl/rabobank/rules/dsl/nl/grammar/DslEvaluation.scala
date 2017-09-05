package nl.rabobank.rules.dsl.nl.grammar

import nl.rabobank.rules.dsl.core.operators._
import nl.rabobank.rules.dsl.core.temporal.LocalDate
import nl.rabobank.rules.dsl.nl.grammar.DslCondition._
import nl.rabobank.rules.engine._
import nl.rabobank.rules.facts.{ListFact, SingularFact}
import nl.rabobank.rules.finance.core.Quantity
import nl.rabobank.rules.finance.nl.{Bedrag, Percentage}

import scala.language.implicitConversions

//scalastyle:off method.name

class ListUnwrappingEvaluation[A](wrapped: Evaluation[List[A]]) extends Evaluation[A] {
  override def apply(c: Context): Option[A] = wrapped(c) match {
    case Some(x :: xs) => Some(x)
    case _ => None
  }
}

class BinaryEvaluation[-A, B, +C](lhs: Evaluation[A], rhs: Evaluation[B], operatorDefinition: BinaryOperable[A, B, C]) extends Evaluation[C] {
  override def apply(c: Context): Option[C] = {
    val lhsValues = lhs(c).getOrElse(operatorDefinition.identityLeft)
    val rhsValues = rhs(c).getOrElse(operatorDefinition.identityRight)

    Some(operatorDefinition.operation(lhsValues, rhsValues))
  }

  override def toString: String = s"${lhs.toString} ${operatorDefinition.representation} ${rhs.toString}"
}

class UnaryMinusEvaluation[+A : Quantity](eval: Evaluation[A]) extends Evaluation[A] {
  override def apply(c: Context): Option[A] = {
    val ev = implicitly[Quantity[A]]

    Some(ev.negate(eval(c).getOrElse(ev.zero)))
  }
}

class DslEvaluation[+A](val condition: DslCondition, val evaluation: Evaluation[A]) {

  def +[A1 >: A, B, C](other: DslEvaluation[B])(implicit ev: Addable[A1, B, C]): DslEvaluation[C] = {
    newDslEvaluation(other, new BinaryEvaluation[A1, B, C](evaluation, other.evaluation, ev))
  }

  def -[A1 >: A, B, C](other: DslEvaluation[B])(implicit ev: Subtractable[A1, B, C]): DslEvaluation[C] = {
    newDslEvaluation(other, new BinaryEvaluation[A1, B, C](evaluation, other.evaluation, ev))
  }

  def *[A1 >: A, B, C](other: DslEvaluation[B])(implicit ev: Multipliable[A1, B, C]): DslEvaluation[C] = {
    // Values can only be multiplied with BigDecimal. But this must be commutative. In the finance DSL we solve this
    // with overloads, but here, we're working with generic types based on the value types. Overloading doesn't work
    // here, due to type erasure (Numeric[BigDecimal] erases to the same type as Numeric[Bedrag]). Therefore we need
    // a new type class to work around this issue.
    newDslEvaluation(other, new BinaryEvaluation[A1, B, C](evaluation, other.evaluation, ev))
  }

  def /[A1 >: A, B, C](other: DslEvaluation[B])(implicit ev: Divisible[A1, B, C]): DslEvaluation[C] = {
    newDslEvaluation(other, new BinaryEvaluation[A1, B, C](evaluation, other.evaluation, ev))
  }

  def unary_-[B >: A : Quantity]: DslEvaluation[B] = {
    DslEvaluation(condition, new UnaryMinusEvaluation[B](evaluation))
  }

  private def newDslEvaluation[B](other: DslEvaluation[Any], newEvaluation: Evaluation[B]) = DslEvaluation(andCombineConditions(condition, other.condition), newEvaluation)
  private def newDslEvaluation[B](other: SingularFact[B], newEvaluation: Evaluation[B]) = DslEvaluation(andCombineConditions(condition, factFilledCondition(other)), newEvaluation)
}

object DslEvaluation {
  def apply[A](condition: DslCondition, evaluation: Evaluation[A]): DslEvaluation[A] = new DslEvaluation[A](condition, evaluation)
}

trait DslEvaluationImplicits {

  implicit def factToDslEvaluation[A](fact: SingularFact[A]): DslEvaluation[A] = DslEvaluation(factFilledCondition(fact), new SingularFactEvaluation[A](fact))
  implicit def listFactToDslEvaluation[A](fact: ListFact[A]): DslEvaluation[List[A]] = DslEvaluation(factFilledCondition(fact), new ListFactEvaluation[A](fact))

  implicit def intToDslEvaluation(value: Int): DslEvaluation[Int] = DslEvaluation(emptyTrueCondition, new ConstantValueEvaluation[Int](value))
  implicit def intToBigDecimalDslEvaluation(value: Int): DslEvaluation[BigDecimal] = DslEvaluation(emptyTrueCondition, new ConstantValueEvaluation[BigDecimal](value))
  implicit def bigDecimalToDslEvaluation(value: BigDecimal): DslEvaluation[BigDecimal] = DslEvaluation(emptyTrueCondition, new ConstantValueEvaluation[BigDecimal](value))
  implicit def bedragToDslEvaluation(value: Bedrag): DslEvaluation[Bedrag] = DslEvaluation(emptyTrueCondition, new ConstantValueEvaluation[Bedrag](value))
  implicit def stringToDslEvaluation(value: String): DslEvaluation[String] = DslEvaluation(emptyTrueCondition, new ConstantValueEvaluation[String](value))
  implicit def percentageToDslEvaluation(value: Percentage): DslEvaluation[Percentage] = DslEvaluation(emptyTrueCondition, new ConstantValueEvaluation[Percentage](value))
  implicit def dslDatumToDslEvaluation(value: LocalDate): DslEvaluation[LocalDate] = DslEvaluation(emptyTrueCondition, new ConstantValueEvaluation[Datum](value))
}
