package nl.rabobank.oss.rules.dsl.nl.grammar

import nl.rabobank.oss.rules.dsl.core.projections.{ProjectableListFields, ProjectedDslEvaluation}
import nl.rabobank.oss.rules.dsl.core.projections.ProjectedDslEvaluation
import nl.rabobank.oss.rules.dsl.nl.grammar.LijstBerekeningGlossary._
import nl.rabobank.oss.rules.dsl.nl.grammar.ComplexObjectProjections._
import nl.rabobank.oss.rules.engine._
import nl.rabobank.oss.rules.facts.ListFact

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
