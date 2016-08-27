package org.scalarules.dsl.nl.datum

import org.scalarules.utils.Glossary

object DatumTestGlossary extends Glossary {

  val InvoerDatum = defineFact[Datum]

  val EerderDan = defineFact[String]
  val EerderDanGelijk = defineFact[String]
  val LaterDan = defineFact[String]
  val LaterDanGelijk = defineFact[String]
  val GelijkAan = defineFact[String]

}
