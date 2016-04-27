package org.scalarules.dsl.core.projections

import scala.language.implicitConversions
import org.scalarules.dsl.core.projections.ProjectableFieldsGlossary._
import org.scalarules.dsl.core.projections.ComplexObjectProjections.toProjections
import org.scalarules.dsl.nl.grammar._
import org.scalarules.engine.Fact

class ProjectableFieldsCalculation extends Berekening(
  Gegeven(altijd) Bereken
    IntFact is (ComplexFact.intValue + IntFact2)
)

case class ComplexObject(intValue: Int, stringValue: String)

object ComplexObjectProjections {
  implicit def toProjections(fact: Fact[ComplexObject]): ComplexObjectProjections = new ComplexObjectProjections(fact)
}
class ComplexObjectProjections(complexFact: Fact[ComplexObject]) extends ProjectableFields[ComplexObject] {
  override protected def outerFact: Fact[ComplexObject] = complexFact

  val intValue: DslEvaluation[Int] = projectField(_.intValue)
  val stringValue: DslEvaluation[String] = projectField(_.stringValue)
}
