package org.scalarules.dsl.nl.grammar

import org.scalarules.dsl.nl.grammar.AfrondingsWords._
import org.scalarules.dsl.nl.grammar.AfrondingsTestBerekeningGlossary._

import scala.language.postfixOps

class AfrondingsTestBerekening extends Berekening (

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
