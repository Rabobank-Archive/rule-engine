package nl.rabobank.rules.dsl.nl.grammar

import nl.rabobank.rules.dsl.core.glossaries.Glossary
import nl.rabobank.rules.finance.nl.{Bedrag, Percentage}

object AfrondingsTestBerekeningGlossary extends Glossary {

  val afrondingsType = defineFact[String]()

  val startBigDecimal = defineFact[BigDecimal]()
  val afgerondBigDecimalHalfEven = defineFact[BigDecimal]()
  val afgerondBigDecimalHalfNaarNulToe = defineFact[BigDecimal]()
  val afgerondBigDecimalNaarBeneden = defineFact[BigDecimal]()
  val afgerondBigDecimalNaarBoven = defineFact[BigDecimal]()
  val afgerondBigDecimalNaarNulToe = defineFact[BigDecimal]()
  val afgerondBigDecimalRekenkundig = defineFact[BigDecimal]()
  val afgerondBigDecimalVanNulAf = defineFact[BigDecimal]()
  
  val startPercentage = defineFact[Percentage]()
  val afgerondPercentageHalfEven = defineFact[Percentage]()
  val afgerondPercentageHalfNaarNulToe = defineFact[Percentage]()
  val afgerondPercentageNaarBeneden = defineFact[Percentage]()
  val afgerondPercentageNaarBoven = defineFact[Percentage]()
  val afgerondPercentageNaarNulToe = defineFact[Percentage]()
  val afgerondPercentageRekenkundig = defineFact[Percentage]()
  val afgerondPercentageVanNulAf = defineFact[Percentage]()

  val startBedrag = defineFact[Bedrag]()
  val afgerondBedragHalfEven = defineFact[Bedrag]()
  val afgerondBedragHalfNaarNulToe = defineFact[Bedrag]()
  val afgerondBedragNaarBeneden = defineFact[Bedrag]()
  val afgerondBedragNaarBoven = defineFact[Bedrag]()
  val afgerondBedragNaarNulToe = defineFact[Bedrag]()
  val afgerondBedragRekenkundig = defineFact[Bedrag]()
  val afgerondBedragVanNulAf = defineFact[Bedrag]()

}
