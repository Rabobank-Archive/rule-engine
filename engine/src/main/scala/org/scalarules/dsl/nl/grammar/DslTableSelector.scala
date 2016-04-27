package org.scalarules.dsl.nl.grammar

import DslCondition._
import org.scalarules.engine.{Derivation, Context, Evaluation, Fact}

object DslTableSelector {
  var prikken = new DslTableSelector
}

trait Table[O, X, Y] {
  def get(x: X, y: Y): O
}

class DslTableSelector {
  def in[A, X, Y](inkomensLastTabel: Fact[Table[A, X, Y]]): DslTableOperation[A, X, Y] =
    new DslTableOperation(inkomensLastTabel/*, condition, output, derivations*/)
}

class DslTableOperation[A, X, Y](tableFact: Fact[Table[A, X, Y]]/*, condition: DslCondition, output: Fact[A], derivations: List[Derivation]*/) {
  def met(waardes: waarde[X, Y]): DslEvaluation[A] = waardes.toEvaluation(tableFact)
  def met(waardes: waardes[X, Y]): DslEvaluation[List[A]] = waardes.toEvaluation(tableFact)
}

//scalastyle:off class.name
case class waarde[X, Y](xFact: Fact[X], yFact: Fact[Y]) {
  def toEvaluation[A](tableFact: Fact[Table[A, X, Y]]): DslEvaluation[A] = {
    val localCondition: DslCondition = andCombineConditions(factFilledCondition(tableFact), factFilledCondition(xFact), factFilledCondition(yFact))
    val evaluation = new TableEvaluation(xFact, yFact, tableFact)

    DslEvaluation(localCondition, evaluation)
  }
}

case class waardes[X, Y](xFact: Fact[List[X]], yFact: Fact[Y]){
  def toEvaluation[A](tableFact: Fact[Table[A, X, Y]]): DslEvaluation[List[A]] = {
    val localCondition: DslCondition = andCombineConditions(factFilledCondition(tableFact), factFilledCondition(xFact), factFilledCondition(yFact))
    val evaluation = new RepeatedTableEvaluation(xFact, yFact, tableFact)

    DslEvaluation(localCondition, evaluation)
  }
}
//scalastyle:on class.name

class TableEvaluation[A, X, Y](val xFact: Fact[X], val yFact: Fact[Y], val tableFact: Fact[Table[A, X, Y]]) extends Evaluation[A] {
  def apply(c: Context): Option[A] = tableFact.toEval(c) match {
    case Some(res) => Some(res.get(xFact.toEval(c).get, yFact.toEval(c).get))
    case _ => None
  }

  override def toString: String = "Prikken uit een tabel"
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

  override def toString: String = "Meerdere prikken uit een tabel"
}
