package org.scalarules.dsl.core

import org.scalarules.dsl.core.grammar.date.DslDate
import org.scalarules.dsl.nl.finance.{Bedrag, Percentage}
import org.scalarules.engine.SingularFact
import org.scalarules.utils.Glossary

object DatumTestGlossary extends Glossary {

  val DatumA = SingularFact[DslDate]("DatumA")
  val DatumB = SingularFact[DslDate]("DatumB")

  val KleinerDan = SingularFact[String]("KleinerDan")
  val KleinerDanGelijk = SingularFact[String]("KleinerDanGelijk")
  val GroterDan = SingularFact[String]("GroterDan")
  val GroterDanGelijk = SingularFact[String]("GroterDanGelijk")
  val GelijkAan = SingularFact[String]("GelijkAan")

}
