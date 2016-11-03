package org.scalarules.dsl.core

import org.scalarules.facts.SingularFact
import org.scalarules.finance.nl.{Bedrag, Percentage}
import org.scalarules.utils.Glossary

object DivisibleValuesGlossary extends Glossary {
  val BedragA = defineFact[Bedrag]
  val BedragB = defineFact[Bedrag]
  val BedragC = defineFact[Bedrag]
  val BedragD = defineFact[Bedrag]

  val PercentageA = defineFact[Percentage]
  val PercentageB = defineFact[Percentage]

}
