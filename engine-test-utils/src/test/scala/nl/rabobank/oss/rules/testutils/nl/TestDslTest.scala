package nl.rabobank.oss.rules.testutils.nl

import nl.rabobank.oss.rules.finance.nl._
import nl.rabobank.oss.rules.facts.{ListFact, SingularFact}
import nl.rabobank.oss.rules.testutils.nl.TestDslTestGlossary._

class TestDslTest extends BerekeningenTester(new TestDslTestBerekening) {

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

  test("lege lijst wordt geïnterpreteerd als value") gegeven (
    LijstInt is List()
    ) verwacht (
    LijstInt is List()
    )

}

object TestDslTestGlossary {
  val BedragPerJaarA: SingularFact[Bedrag Per Jaar] = new SingularFact[Bedrag Per Jaar]("BedragPerJaarA")
  val BedragPerJaarB: SingularFact[Bedrag Per Jaar] = new SingularFact[Bedrag Per Jaar]("BedragPerJaarB")

  val LijstInt: ListFact[Int] = new ListFact[Int]("LijstInt")
}


