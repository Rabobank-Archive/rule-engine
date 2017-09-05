package nl.rabobank.rules.utils

import nl.rabobank.rules.dsl.core.glossaries.Glossary
import nl.rabobank.rules.utils.TestDslTestGlossary.{BedragPerJaarA, BedragPerJaarB}
import nl.rabobank.rules.finance.nl._

class TestDslTest extends InternalBerekeningenTester(new TestDslTestBerekening) {

  test("omlaag afronden van Bedrag Per Jaar") gegeven (
    BedragPerJaarA is ("50.123456789".euro per Jaar)
  ) verwacht (
    BedragPerJaarB is ("50.12".euro per Jaar)
  )

  test("omhoog afronden van Bedrag Per Jaar") gegeven (
    BedragPerJaarA is ("50.126456789".euro per Jaar)
  ) verwacht (
    BedragPerJaarB is ("50.13".euro per Jaar)
  )

  test("niet afronden van Bedrag Per Jaar") gegeven (
    BedragPerJaarA is ("50.12".euro per Jaar)
  ) verwacht (
    BedragPerJaarB is ("50.12".euro per Jaar)
  )

}

object TestDslTestGlossary extends Glossary {
  val BedragPerJaarA = defineFact[Bedrag Per Jaar]
  val BedragPerJaarB = defineFact[Bedrag Per Jaar]
}


