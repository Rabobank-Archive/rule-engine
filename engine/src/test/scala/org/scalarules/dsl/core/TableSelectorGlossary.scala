package org.scalarules.dsl.core

import org.scalarules.dsl.core.grammar.Table
import org.scalarules.utils.Glossary

object TableSelectorGlossary extends Glossary {

  val IndexX = defineFact[Int]
  val IndexY = defineFact[Int]
  val ResultString = defineFact[String]
  val TableFact = defineFact[Table[String, Int, Int]]

  val IndexXRange = defineListFact[Int]
  val ResultList = defineListFact[String]
}
