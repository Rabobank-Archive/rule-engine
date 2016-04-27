package org.scalarules.dsl.core

import org.scalarules.dsl.core.TableSelectorGlossary._
import org.scalarules.dsl.nl.finance._
import org.scalarules.dsl.nl.grammar.{Table, aanwezig}
import org.scalarules.utils.{InternalBerekeningenTester, lijst}

class TableSelectorTest extends InternalBerekeningenTester(new TableSelectorBerekening) {

  val simpleTable = new Table[String, Int, Int] {
    override def get(x: Int, y: Int): String = "Hello World"
  }

  test("eenvoudige Table Test") gegeven (
    IndexX is 1,
    IndexY is 1,
    TableFact is simpleTable
  ) verwacht (
    ResultString is "Hello World"
  )

}
