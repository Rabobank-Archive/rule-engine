package org.scalarules.dsl.nl.grammar

import DslCondition._
import org.scalarules.engine.{Derivation, Context, Evaluation, Fact}

object prikken //scalastyle:ignore object.name

trait Table[O, X, Y] {
  def get(x: X, y: Y): O
}

class DslTableSelector[A](condition: DslCondition, output: Fact[A], derivations: List[Derivation]) {
  def in[X, Y](inkomensLastTabel: Fact[Table[A, X, Y]]): DslTableOperation[A, X, Y] =
    new DslTableOperation(inkomensLastTabel, condition, output, derivations)
}
class DslTableSelectorForLists[A](condition: DslCondition, output: Fact[List[A]], berekeningAcc: List[Derivation]) {
  def in[X, Y](inkomensLastTabel: Fact[Table[A, X, Y]]): DslTableOperationForLists[A, X, Y] =
    new DslTableOperationForLists(inkomensLastTabel, condition, output, berekeningAcc)
}

class DslTableOperation[A, X, Y](tableFact: Fact[Table[A, X, Y]], condition: DslCondition, output: Fact[A], derivations: List[Derivation]) {
  def met(xFact: Fact[X], yFact: Fact[Y]): BerekeningAccumulator = {
    val localCondition: DslCondition = andCombineConditions(factFilledCondition(tableFact), factFilledCondition(xFact), factFilledCondition(yFact))
    val evaluation: Evaluation[A] = new TableEvaluation(xFact, yFact, tableFact)

    val dslEvaluation = DslEvaluation(localCondition, evaluation)

    new BerekeningAccumulator(condition, Specificatie(condition, output, dslEvaluation) :: derivations)
  }
}

// TODO: We've got to be able to do better than this :)
class DslTableOperationForLists[A, X, Y](tableFact: Fact[Table[A, X, Y]], condition: DslCondition, output: Fact[List[A]], derivations: List[Derivation]) {
  def met(xFact: Fact[List[X]], yFact: Fact[Y]): BerekeningAccumulator = {
    val localCondition: DslCondition = andCombineConditions(factFilledCondition(tableFact), factFilledCondition(xFact), factFilledCondition(yFact))
    val evaluation: Evaluation[List[A]] = new RepeatedTableEvaluation(xFact, yFact, tableFact)

    val dslEvaluation = DslEvaluation(localCondition, evaluation)

    new BerekeningAccumulator(condition, Specificatie(condition, output, dslEvaluation) :: derivations)
  }
}

class TableEvaluation[A, X, Y](val xFact: Fact[X], val yFact: Fact[Y], val tableFact: Fact[Table[A, X, Y]]) extends Evaluation[A] {
  def apply(c: Context): Option[A] = tableFact.toEval(c) match {
    case Some(res) => Some(res.get(xFact.toEval(c).get, yFact.toEval(c).get))
    case _ => None
  }

  override def toString: String = "TODO: InkomensLastTabel obv ..."
}

class RepeatedTableEvaluation[A, X, Y](val xFact: Fact[List[X]], val yFact: Fact[Y], val tableFact: Fact[Table[A, X, Y]]) extends Evaluation[List[A]] {
  def apply(c: Context): Option[List[A]] = {
    val xValues = xFact.toEval(c).getOrElse(List())
    val yCoordinate: Y = yFact.toEval(c).get

    tableFact.toEval(c) match {
      case Some(res) => Some(xValues.map( res.get(_, yCoordinate) ))
      case _ => None
    }
  }

  override def toString: String = "TODO: InkomensLastTabel obv ..."
}
