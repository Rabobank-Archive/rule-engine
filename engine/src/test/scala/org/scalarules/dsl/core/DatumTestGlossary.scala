package org.scalarules.dsl.core

import org.scalarules.dsl.core.grammar.date.DslDate
import org.scalarules.dsl.nl.finance.{Bedrag, Percentage}
import org.scalarules.engine.SingularFact
import org.scalarules.utils.Glossary

object DatumTestGlossary extends Glossary {

  val DatumA = SingularFact[DslDate]("DatumA")
  val DatumB = SingularFact[DslDate]("DatumB")

  val EerderDan = SingularFact[String]("KleinerDan")
  val EerderDanGelijk = SingularFact[String]("KleinerDanGelijk")
  val LaterDan = SingularFact[String]("GroterDan")
  val LaterDanGelijk = SingularFact[String]("GroterDanGelijk")
  val GelijkAan = SingularFact[String]("GelijkAan")

}
