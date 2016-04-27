package org.scalarules.dsl.core

import org.scalarules.dsl.nl.finance._
import org.scalarules.dsl.nl.grammar._
import TableSelectorGlossary._
import org.scalarules.dsl.nl.grammar.DslCondition._
import org.scalarules.dsl.nl.grammar.DslTableSelector.prikken
import org.scalarules.engine._

class TableSelectorBerekening extends {
  } with Berekening (
  Gegeven (altijd)
  Bereken
    ResultString is (prikken in TableFact met waarde(IndexX, IndexY)) en
    ResultList is (prikken in TableFact met waardes(IndexXRange, IndexY))

)