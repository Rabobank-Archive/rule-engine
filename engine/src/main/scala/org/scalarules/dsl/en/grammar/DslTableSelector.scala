package org.scalarules.dsl.en.grammar

import org.scalarules.dsl.core.grammar.{DslCondition, DslEvaluation, Table}
import org.scalarules.dsl.core.grammar.DslCondition._
import org.scalarules.engine.{Context, Evaluation, Fact}

object DslTableSelector {
  val find = new DslTableSelector
}

class DslTableSelector {
  def in[A, X, Y](inkomensLastTabel: Fact[Table[A, X, Y]]): DslTableOperation[A, X, Y] =
    new DslTableOperation(inkomensLastTabel/*, condition, output, derivations*/)
}

//scalastyle:off method.name
class DslTableOperation[A, X, Y](tableFact: Fact[Table[A, X, Y]]/*, condition: DslCondition, output: Fact[A], derivations: List[Derivation]*/) {
  def With(waardes: value[X, Y]): DslEvaluation[A] = waardes.toEvaluation(tableFact)
  def With(waardes: values[X, Y]): DslEvaluation[List[A]] = waardes.toEvaluation(tableFact)
}

//scalastyle:off class.name
case class value[X, Y](xFact: Fact[X], yFact: Fact[Y]) {
  def toEvaluation[A](tableFact: Fact[Table[A, X, Y]]): DslEvaluation[A] = {
    val localCondition: DslCondition = andCombineConditions(factFilledCondition(tableFact), factFilledCondition(xFact), factFilledCondition(yFact))
    val evaluation = new TableEvaluation(xFact, yFact, tableFact)

    DslEvaluation(localCondition, evaluation)
  }
}

case class values[X, Y](xFact: Fact[List[X]], yFact: Fact[Y]){
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

  override def toString: String = "Find value from table"
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

  override def toString: String = "Find multiple values from table"
}
