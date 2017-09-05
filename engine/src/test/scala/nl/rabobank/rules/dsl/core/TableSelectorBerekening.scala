package nl.rabobank.rules.dsl.core

import nl.rabobank.rules.dsl.core.TableSelectorGlossary._
import nl.rabobank.rules.dsl.nl.grammar.DslTableSelector.prikken
import nl.rabobank.rules.dsl.nl.grammar._

class TableSelectorBerekening extends {
  } with Berekening (
  Gegeven (altijd)
  Bereken
    ResultString is (prikken in TableFact met waarde(IndexX, IndexY)) en
    ResultList is (prikken in TableFact met waardes(IndexXRange, IndexY))

)
