package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.core.projections.{ProjectableListFields, ProjectedDslEvaluation}
import org.scalarules.dsl.nl.grammar.LijstBerekeningGlossary._
import org.scalarules.dsl.nl.grammar.ComplexObjectProjections._
import org.scalarules.engine._
import org.scalarules.facts.ListFact

import scala.language.implicitConversions

class LijstFilter extends Berekening (
  Gegeven (altijd)
    Bereken
      LijstGefilterd is (filter lijst LijstOngefilterd op (1,2,3,4)) en
      LijstGefilterdMetList is (filter lijst LijstOngefilterd op List(1,2,3,4)) en
      LijstGefilterdComplexObject is (filter lijst LijstOngefilterdComplexObject op LijstOngefilterdComplexObject.value van (3,4,6))
)

case class ComplexFilterObject(value: Int)
class ComplexObjectProjections(complexObjectFact: ListFact[ComplexFilterObject]) extends ProjectableListFields[ComplexFilterObject] {
  def outerFact: ListFact[ComplexFilterObject] = complexObjectFact

  val value: ProjectedDslEvaluation[ComplexFilterObject, Int] = projectField(_.value)
}
object ComplexObjectProjections {
  implicit def toProjection(f: ListFact[ComplexFilterObject]): ComplexObjectProjections = new ComplexObjectProjections(f)
}
