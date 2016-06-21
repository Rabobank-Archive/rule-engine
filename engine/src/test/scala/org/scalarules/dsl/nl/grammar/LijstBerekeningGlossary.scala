package org.scalarules.dsl.nl.grammar

import org.scalarules.utils.MacroGlossary

object LijstBerekeningGlossary extends MacroGlossary {
  val LijstGefilterd = defineListFact[Int]
  val LijstGefilterdMetList = defineListFact[Int]
  val LijstOngefilterd = defineListFact[Int]
  val LijstGefilterdComplexObject = defineListFact[ComplexFilterObject]
  val LijstOngefilterdComplexObject = defineListFact[ComplexFilterObject]
}
