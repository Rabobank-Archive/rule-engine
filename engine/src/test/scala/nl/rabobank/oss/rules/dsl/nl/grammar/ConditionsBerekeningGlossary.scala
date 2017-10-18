package nl.rabobank.oss.rules.dsl.nl.grammar

import nl.rabobank.oss.rules.dsl.core.glossaries.Glossary

object ConditionsBerekeningGlossary extends Glossary {
  val availableInput = defineFact[BigDecimal]
  val unavailableInput = defineFact[BigDecimal]

  val outputAlwaysAvailable = defineFact[BigDecimal]
  val outputShouldBeAvailableIfInputIsAvailable = defineFact[BigDecimal]
  val outputShouldBeAvailableIfInputIsNotAvailable = defineFact[BigDecimal]

  val tryToOverwriteThisValue = defineFact[BigDecimal]
  val defaultValueForOverwriting = defineFact[BigDecimal]
  val missingValueToUseInEerste = defineFact[BigDecimal]

}
