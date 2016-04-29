package org.scalarules.dsl.core.projections

import scala.language.implicitConversions
import org.scalarules.dsl.core.projections.ProjectableFieldsGlossary._
import org.scalarules.dsl.core.projections.ComplexObjectProjections.toProjections
import org.scalarules.dsl.nl.grammar._
import org.scalarules.engine.{Fact, ListFact, SingularFact}

class ProjectableFieldsCalculation extends Berekening(
  Gegeven(altijd) Bereken
    IntFact is ComplexFact.intValue + IntFact2 en
    StringFactList is ComplexFactList.stringValue
)

case class ComplexObject(intValue: Int, stringValue: String)

object ComplexObjectProjections {
  implicit def toProjections(fact: SingularFact[ComplexObject]): ComplexObjectProjections = new ComplexObjectProjections(fact)
  implicit def toProjections(fact: ListFact[ComplexObject]): ComplexObjectListProjections = new ComplexObjectListProjections(fact)
}

class ComplexObjectProjections(complexFact: Fact[ComplexObject]) extends ProjectableFields[ComplexObject] {
  override protected def outerFact: Fact[ComplexObject] = complexFact

  val intValue: DslEvaluation[Int] = projectField(_.intValue)
  val stringValue: DslEvaluation[String] = projectField(_.stringValue)
}

class ComplexObjectListProjections(complexFact: ListFact[ComplexObject]) extends ProjectableListFields[ComplexObject] {
  override protected def outerFact: Fact[List[ComplexObject]] = complexFact

  val intValue: DslEvaluation[List[Int]] = projectField(_.intValue)
  val stringValue: DslEvaluation[List[String]] = projectField(_.stringValue)
}
