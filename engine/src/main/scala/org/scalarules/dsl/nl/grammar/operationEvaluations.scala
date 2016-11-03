package org.scalarules.dsl.nl.grammar

import org.scalarules.engine._
import org.scalarules.facts.Fact

/* ********************************************************************************************************************************************************** *
 * This file contains the actual implementations of the Evaluations supported by the DSL. The DSL implicits will create objects of these classes to feed to   *
 * the engine. If you need an extra operation, add its implementation here and then add support to the DSL for it in the dslEvaluations.scala file.           *
 * ********************************************************************************************************************************************************** */

/**
  * Evaluation providing the first value found for an ordered sequence of Facts. When this evaluation resolves, any Facts that resolve to None will be ignored.
  *
  * @param facts a List of Facts to search values for.
  * @tparam A the type returned by this Evaluation. It is inferred from the type of Facts in the input Sequence.
  */
class EersteEvaluation[A](facts: Seq[Fact[A]]) extends Evaluation[A] {
  def apply(c: Context): Option[A] = facts.find(f => f.toEval(c).isDefined) match {
    case Some(x) => x.toEval(c)
    case _ => None
  }

  override def toString: String = facts.mkString("eerste(", ", ", ")")
}

class EersteElementEvaluation[A](fact: Fact[List[A]]) extends Evaluation[A] {
  def apply(c: Context): Option[A] = fact.toEval(c) match {
    case Some(x :: xs) => Some(x)
    case _ => None
  }

  override def toString: String = s"eerste element van(${fact.name})"
}

/**
  * Evaluation providing a framework for operations which can reduce a Sequence of Facts. When this evaluation resolves, the operation parameter will be applied
  * to the Sequence of Facts using the reduceLeft function.
  *
  * @param operationName name of the operation. This is used in the toString function for documentation purposes. (You should preferably supply the same word as
  *                      is used in the DSL to create the operation. For examples, see maximum and minimum in dslEvaluations.)
  * @param operation the actual function capable of reducing a Sequence of Facts.
  * @param facts the Sequence of input Facts.
  * @tparam A type of the input Facts.
  * @tparam B type of the result of the reducing operation. Must be a supertype of type A to fit into the reduceLeft operation.
  */
class ReducableEvaluation[A, B >: A](val operationName: String, val operation: (B, A) => B, val facts: Seq[Fact[A]]) extends Evaluation[B] {
  override def apply(c: Context): Option[B] = facts.flatMap(f => f.toEval(c)).toList match {
    case Nil => None
    case list@(x :: xs) => Some(list.reduceLeft((l: B, r: A) => operation(l, r)))
  }

  override def toString: String = facts.mkString(s"$operationName(", ", ", ")")
}

/**
  * Evaluation providing an if-then-else expression. When this evaluation resolves, the condition is evaluated first. If its result is true, the danFact will be
  * used, otherwise the andersFact will be used.
  *
  * @param condition the Condition on which to base the choice of values.
  * @param danEvaluation the Evaluation to resolve the value of when the condition is true.
  * @param andersEvaluation the Evaluation to resolve the value of when the condition is false.
  * @tparam A type of the result and consequently the input Facts.
  */
class AlsDanElseEvaluation[A](val condition: Condition, val danEvaluation: Evaluation[A], val andersEvaluation: Evaluation[A]) extends Evaluation[A] {
  override def apply(c: Context): Option[A] = if (condition(c)) danEvaluation(c) else andersEvaluation(c)
}

class ListIndexSelectionEvaluation[+A](eval: Evaluation[List[A]], index: Int) extends Evaluation[A] {
  override def apply(c: Context): Option[A] = {
    eval(c) match {
      case Some(lst@(x :: xs)) if index < lst.size => Some(lst(index))
      case _ => None
    }
  }
}

class ListAggregationEvaluation[+A](eval: Evaluation[List[A]], aggregator: List[A] => A) extends Evaluation[A] {
  override def apply(c: Context): Option[A] = {
    eval(c) match {
      case Some(lst@x :: xs) => Some(aggregator(lst))
      case _ => None
    }
  }
}

