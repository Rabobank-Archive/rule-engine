package org.scalarules.dsl.core

import org.scalarules.finance.nl.{Bedrag, Percentage}
import org.scalarules.engine.SingularFact
import org.scalarules.utils.Glossary

object DivisibleValuesGlossary extends Glossary {
  val BedragA = SingularFact[Bedrag]("bedragA")
  val BedragB = SingularFact[Bedrag]("bedragB")

  val PercentageA = SingularFact[Percentage]("percentageA")

}
