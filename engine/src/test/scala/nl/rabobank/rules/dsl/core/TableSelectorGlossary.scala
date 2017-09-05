package nl.rabobank.rules.dsl.core

import nl.rabobank.rules.dsl.core.glossaries.Glossary
import nl.rabobank.rules.dsl.nl.grammar.Table

object TableSelectorGlossary extends Glossary {

  val IndexX = defineFact[Int]
  val IndexY = defineFact[Int]
  val ResultString = defineFact[String]
  val TableFact = defineFact[Table[String, Int, Int]]

  val IndexXRange = defineListFact[Int]
  val ResultList = defineListFact[String]
}
