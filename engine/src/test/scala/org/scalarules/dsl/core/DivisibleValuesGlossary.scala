package org.scalarules.dsl.core

import org.scalarules.engine.SingularFact
import org.scalarules.finance.nl.{Bedrag, Percentage}
import org.scalarules.utils.Glossary

object DivisibleValuesGlossary extends Glossary {
  val BedragA = defineFact[Bedrag]
  val BedragB = defineFact[Bedrag]

  val PercentageA = defineFact[Percentage]

}
