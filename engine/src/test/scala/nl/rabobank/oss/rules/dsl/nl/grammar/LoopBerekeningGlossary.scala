package nl.rabobank.oss.rules.dsl.nl.grammar

import nl.rabobank.oss.rules.dsl.core.glossaries.Glossary

object LoopBerekeningGlossary extends Glossary {
  val loopInput = defineListFact[BigDecimal]
  val nestedTestInput = defineListFact[List[BigDecimal]]

  val innerLoopIteratee = defineFact[BigDecimal]
  val innerLoopAdditionValue = defineFact[BigDecimal]
  val innerLoopReturnValue = defineFact[BigDecimal]

  val nestedOuterLoopInput = defineListFact[BigDecimal]
  val nestedOuterLoopResult = defineListFact[BigDecimal]

  val simpleLoopResult = defineListFact[BigDecimal]
  val filteredLoopResult = defineListFact[BigDecimal]
  val nestedTestOutput = defineListFact[List[BigDecimal]]

}
