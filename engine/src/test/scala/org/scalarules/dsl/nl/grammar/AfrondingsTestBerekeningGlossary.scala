package org.scalarules.dsl.nl.grammar

import org.scalarules.finance.nl.Percentage
import org.scalarules.utils.Glossary

object AfrondingsTestBerekeningGlossary extends Glossary {

  val aantal = defineFact[Integer]()
  val startPercentage = defineFact[Percentage]()
  val afgerondPercentageHalfEven = defineFact[Percentage]()
  val afgerondPercentageHalfNaarNulToe = defineFact[Percentage]()
  val afgerondPercentageNaarBeneden = defineFact[Percentage]()
  val afgerondPercentageNaarBoven = defineFact[Percentage]()
  val afgerondPercentageNaarNulToe = defineFact[Percentage]()
  val afgerondPercentageRekenkundig = defineFact[Percentage]()
  val afgerondPercentageVanNulAf = defineFact[Percentage]()
  val afrondingsType = defineFact[String]()

}
