package org.scalarules.dsl.core

import org.scalarules.finance.nl.{Bedrag, Percentage}
import org.scalarules.dsl.nl.grammar.Table
import org.scalarules.engine.Fact
import org.scalarules.utils.Glossary

object TableSelectorGlossary extends Glossary {

  val IndexX = defineFact[Int]("IndexX")
  val IndexY = defineFact[Int]("IndexY")
  val ResultString = defineFact[String]("ResultString")
  val TableFact = defineFact[Table[String, Int, Int]]("TableFact")

  val IndexXRange = defineListFact[Int]("IndexXRange")
  val ResultList = defineListFact[String]("ResultList")
}
