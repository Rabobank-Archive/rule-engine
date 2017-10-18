package nl.rabobank.oss.rules.dsl.nl.grammar

import nl.rabobank.oss.rules.dsl.nl.grammar.AfrondingsTestBerekeningGlossary._

import scala.language.postfixOps

class AfrondingsTestBerekening extends Berekening (

  /* BigDecimal test calculations*/
  Gegeven(afrondingsType is "halfNaarEven") Bereken
    afgerondBigDecimalHalfEven is (startBigDecimal halfNaarEven afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "halfNaarNulToe") Bereken
    afgerondBigDecimalHalfNaarNulToe is (startBigDecimal halfNaarNulToe afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "naarBeneden") Bereken
    afgerondBigDecimalNaarBeneden is (startBigDecimal naarBeneden afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "naarBoven") Bereken
    afgerondBigDecimalNaarBoven is (startBigDecimal naarBoven afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "naarNulToe") Bereken
    afgerondBigDecimalNaarNulToe is (startBigDecimal naarNulToe afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "rekenkundig") Bereken
    afgerondBigDecimalRekenkundig is (startBigDecimal rekenkundig afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "vanNulAf") Bereken
    afgerondBigDecimalVanNulAf is (startBigDecimal vanNulAf afgerond op 0 decimalen)
  ,


  /* Percentage test calculations*/
  Gegeven(afrondingsType is "halfNaarEven") Bereken
    afgerondPercentageHalfEven is (startPercentage halfNaarEven afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "halfNaarNulToe") Bereken
    afgerondPercentageHalfNaarNulToe is (startPercentage halfNaarNulToe afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "naarBeneden") Bereken
    afgerondPercentageNaarBeneden is (startPercentage naarBeneden afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "naarBoven") Bereken
    afgerondPercentageNaarBoven is (startPercentage naarBoven afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "naarNulToe") Bereken
    afgerondPercentageNaarNulToe is (startPercentage naarNulToe afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "rekenkundig") Bereken
    afgerondPercentageRekenkundig is (startPercentage rekenkundig afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "vanNulAf") Bereken
    afgerondPercentageVanNulAf is (startPercentage vanNulAf afgerond op 0 decimalen)
  ,


  /* Bedrag test calculations*/
  Gegeven(afrondingsType is "halfNaarEven") Bereken
    afgerondBedragHalfEven is (startBedrag halfNaarEven afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "halfNaarNulToe") Bereken
    afgerondBedragHalfNaarNulToe is (startBedrag halfNaarNulToe afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "naarBeneden") Bereken
    afgerondBedragNaarBeneden is (startBedrag naarBeneden afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "naarBoven") Bereken
    afgerondBedragNaarBoven is (startBedrag naarBoven afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "naarNulToe") Bereken
    afgerondBedragNaarNulToe is (startBedrag naarNulToe afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "rekenkundig") Bereken
    afgerondBedragRekenkundig is (startBedrag rekenkundig afgerond op 0 decimalen)
  ,

  Gegeven(afrondingsType is "vanNulAf") Bereken
    afgerondBedragVanNulAf is (startBedrag vanNulAf afgerond op 0 decimalen)

)
