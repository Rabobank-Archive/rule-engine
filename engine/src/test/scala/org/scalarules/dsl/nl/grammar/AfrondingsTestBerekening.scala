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

)
