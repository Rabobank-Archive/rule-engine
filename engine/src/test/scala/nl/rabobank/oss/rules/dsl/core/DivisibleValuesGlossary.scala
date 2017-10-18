package nl.rabobank.oss.rules.dsl.core

import nl.rabobank.oss.rules.dsl.core.glossaries.Glossary
import nl.rabobank.oss.rules.finance.nl.{Bedrag, Percentage}

object DivisibleValuesGlossary extends Glossary {
  val BedragA = defineFact[Bedrag]
  val BedragB = defineFact[Bedrag]
  val BedragC = defineFact[Bedrag]
  val BedragD = defineFact[Bedrag]

  val PercentageA = defineFact[Percentage]
  val PercentageB = defineFact[Percentage]

}
