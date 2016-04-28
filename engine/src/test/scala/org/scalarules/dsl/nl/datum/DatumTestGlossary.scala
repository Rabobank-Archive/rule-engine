package org.scalarules.dsl.nl.datum

import org.scalarules.engine.SingularFact
import org.scalarules.utils.Glossary

object DatumTestGlossary extends Glossary {

  val InvoerDatum = SingularFact[Datum]("DatumA")

  val EerderDan = SingularFact[String]("EerderDan")
  val EerderDanGelijk = SingularFact[String]("EerderDanGelijk")
  val LaterDan = SingularFact[String]("LaterDan")
  val LaterDanGelijk = SingularFact[String]("LaterDanGelijk")
  val GelijkAan = SingularFact[String]("GelijkAan")

}
