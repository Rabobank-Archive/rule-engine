package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.nl.finance.{Bedrag, Percentage}
import org.scalarules.utils.Glossary

object LijstBerekeningGlossary extends Glossary {
  val LijstGefilterd = defineListFact[Int]("LijstGefilterd")
  val LijstGefilterdMetList = defineListFact[Int]("LijstGefilterdMetList")
  val LijstOnGefilterd = defineListFact[Int]("LijstOnGefilterd")
  val LijstGefilterdComplexObject = defineListFact[ComplexFilterObject]("LijstGefilterdComplexObject")
  val LijstOnGefilterdComplexObject = defineListFact[ComplexFilterObject]("LijstOnGefilterdComplexObject")
}
