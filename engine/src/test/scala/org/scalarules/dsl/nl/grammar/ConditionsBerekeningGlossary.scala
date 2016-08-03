package org.scalarules.dsl.nl.grammar

import org.scalarules.utils.Glossary

object ConditionsBerekeningGlossary extends Glossary {
  val availableInput = defineFact[BigDecimal]
  val unavailableInput = defineFact[BigDecimal]

  val outputAlwaysAvailable = defineFact[BigDecimal]
  val outputShouldBeAvailableIfInputIsAvailable = defineFact[BigDecimal]
  val outputShouldBeAvailableIfInputIsNotAvailable = defineFact[BigDecimal]
}
