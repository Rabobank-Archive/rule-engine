package org.scalarules.dsl.core

import org.scalarules.engine.SingularFact
import org.scalarules.finance.nl.{Bedrag, Percentage}
import org.scalarules.utils.MacroGlossary

object DivisibleValuesGlossary extends MacroGlossary {
  val BedragA = defineFact[Bedrag]
  val BedragB = defineFact[Bedrag]

  val PercentageA = defineFact[Percentage]

}
