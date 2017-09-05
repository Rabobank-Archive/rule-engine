package nl.rabobank.rules.service.dsl

import nl.rabobank.rules.dsl.core.glossaries.Glossary
import nl.rabobank.rules.finance.nl.Bedrag

object BusinessServiceTestGlossary1 extends Glossary {
  val verplichteInvoer1 = defineFact[Bedrag]
  val optioneleInvoer1 = defineFact[Bedrag]
  val uitvoer1 = defineFact[Bedrag]
}

object BusinessServiceTestGlossary2 extends Glossary {
  val verplichteInvoer2 = defineFact[Bedrag]
  val optioneleInvoer2 = defineFact[Bedrag]
  val uitvoer2 = defineFact[Bedrag]
}

object BusinessServiceTestGlossary3 extends Glossary {
  val verplichteInvoer3 = defineFact[Bedrag]
  val optioneleInvoer3 = defineFact[Bedrag]
  val uitvoer3 = defineFact[Bedrag]
}

object NotABusinessServiceTestGlossary1 extends Glossary {
  val notABusinessServiceVerplichtFact = defineFact[Bedrag]
  val notABusinessServiceOptioneelFact = defineFact[Bedrag]
  val notABusinessServiceUitvoer = defineFact[Bedrag]
}
