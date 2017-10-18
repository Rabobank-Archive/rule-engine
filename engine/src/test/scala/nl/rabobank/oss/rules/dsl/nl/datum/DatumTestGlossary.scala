package nl.rabobank.oss.rules.dsl.nl.datum

import nl.rabobank.oss.rules.dsl.core.glossaries.Glossary

object DatumTestGlossary extends Glossary {

  val InvoerDatum = defineFact[Datum]

  val EerderDan = defineFact[String]
  val EerderDanGelijk = defineFact[String]
  val LaterDan = defineFact[String]
  val LaterDanGelijk = defineFact[String]
  val GelijkAan = defineFact[String]

}
