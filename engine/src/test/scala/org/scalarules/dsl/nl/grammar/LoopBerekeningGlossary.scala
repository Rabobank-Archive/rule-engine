package org.scalarules.dsl.nl.grammar

import org.scalarules.utils.Glossary

object LoopBerekeningGlossary extends Glossary {
  val loopInput = defineListFact[BigDecimal]
  val intermediateBigDecimal = defineFact[BigDecimal]
  val simpleLoopResult = defineListFact[BigDecimal]

  val enhancedLoopResult = defineListFact[BigDecimal]
  val innerLoopReturnValue = defineFact[BigDecimal]
  val innerLoopAdditionValue = defineFact[BigDecimal]

  val innerLoopInput = defineListFact[BigDecimal]
  val intermediateInnerLoopBigDecimal = defineFact[BigDecimal]
  val enhancedLoopListInListResult = defineListFact[List[BigDecimal]]
  val innerLoopListReturnValue = defineListFact[BigDecimal]
  val innerLoopAdditionListValue = defineListFact[BigDecimal]

  val filteredLoopResult = defineListFact[BigDecimal]

}
