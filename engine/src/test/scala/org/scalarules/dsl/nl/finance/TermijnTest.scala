package org.scalarules.dsl.nl.finance

import org.scalatest.{FlatSpec, Matchers}

class TermijnTest extends FlatSpec with Matchers {

  behavior of "month"

  it should "be 1 month" in {
    Maand should be (1.maand)
    Maand.toString should be ("maand")
  }


  behavior of "quarter"

  it should "be 3 months" in {
    Kwartaal should be (3.maanden)
    Kwartaal.toString should be ("kwartaal")
  }


  behavior of "halfYear"

  it should "be 6 months" in {
    Halfjaar should be (6.maanden)
    Halfjaar.toString should be ("halfjaar")
  }


  behavior of "year"

  it should "be 12 months" in {
    Jaar should be (12.maanden)
    Jaar.toString should be ("jaar")
  }
}
