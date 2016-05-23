package org.scalarules.dsl.nl.grammar

import org.scalarules.utils.Glossary

object LoopBerekeningGlossary extends Glossary {
  val loopInput = defineListFact[BigDecimal]("loopInput")
  val intermediateBigDecimal = defineFact[BigDecimal]("intermediateBigDecimal")
  val simpleLoopResult = defineListFact[BigDecimal]("simpleLoopResult")

  val enhancedLoopResult = defineListFact[BigDecimal]("enhancedLoopResult")
  val innerLoopReturnValue = defineFact[BigDecimal]("innerLoopReturnValue")
  val innerLoopAdditionValue = defineFact[BigDecimal]("innerLoopAdditionValue")

  val innerLoopInput = defineListFact[BigDecimal]("innerLoopInput")
  val intermediateInnerLoopBigDecimal = defineFact[BigDecimal]("intermediateInnerLoopBigDecimal")
  val enhancedLoopListInListResult = defineListFact[List[BigDecimal]]("enhancedLoopListInListResult")
  val innerLoopListReturnValue = defineListFact[BigDecimal]("innerLoopListReturnValue")
  val innerLoopAdditionListValue = defineListFact[BigDecimal]("innerLoopAdditionListValue")
}
