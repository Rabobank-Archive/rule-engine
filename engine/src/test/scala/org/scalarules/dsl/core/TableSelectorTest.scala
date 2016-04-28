package org.scalarules.dsl.core

import org.scalarules.dsl.core.TableSelectorGlossary._
import org.scalarules.dsl.core.grammar.Table
import org.scalarules.utils.{InternalBerekeningenTester}

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
