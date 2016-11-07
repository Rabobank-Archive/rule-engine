package org.scalarules.dsl.core

import org.scalarules.finance.nl._
import org.scalarules.dsl.nl.grammar._
import TableSelectorGlossary._
import org.scalarules.dsl.core.grammar.DslCondition._
import org.scalarules.dsl.nl.grammar.DslTableSelector.prikken
import org.scalarules.engine._

class TableSelectorBerekening extends {
  } with Berekening (
  Gegeven (altijd)
  Bereken
    ResultString is (prikken in TableFact met waarde(IndexX, IndexY)) en
    ResultList is (prikken in TableFact met waardes(IndexXRange, IndexY))

)