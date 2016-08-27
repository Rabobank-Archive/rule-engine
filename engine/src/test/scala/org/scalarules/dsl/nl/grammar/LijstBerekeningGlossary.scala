package org.scalarules.dsl.nl.grammar

import org.scalarules.utils.Glossary

object LijstBerekeningGlossary extends Glossary {
  val LijstGefilterd = defineListFact[Int]
  val LijstGefilterdMetList = defineListFact[Int]
  val LijstOngefilterd = defineListFact[Int]
  val LijstGefilterdComplexObject = defineListFact[ComplexFilterObject]
  val LijstOngefilterdComplexObject = defineListFact[ComplexFilterObject]
}
