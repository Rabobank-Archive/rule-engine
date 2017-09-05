package nl.rabobank.rules.dsl.nl.grammar

import nl.rabobank.rules.dsl.core.glossaries.Glossary

object LijstBerekeningGlossary extends Glossary {
  val LijstGefilterd = defineListFact[Int]
  val LijstGefilterdMetList = defineListFact[Int]
  val LijstOngefilterd = defineListFact[Int]
  val LijstGefilterdComplexObject = defineListFact[ComplexFilterObject]
  val LijstOngefilterdComplexObject = defineListFact[ComplexFilterObject]
}
