package org.scalarules.dsl.en.grammar

import org.scalarules.utils.Glossary

object ConditionsDerivationGlossary extends Glossary {
  val availableInput = defineFact[BigDecimal]
  val unavailableInput = defineFact[BigDecimal]

  val outputAlwaysAvailable = defineFact[BigDecimal]
  val outputShouldBeAvailableIfInputIsAvailable = defineFact[BigDecimal]
  val outputShouldBeAvailableIfInputIsNotAvailable = defineFact[BigDecimal]
}
