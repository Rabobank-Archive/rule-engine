package org.scalarules.utils

import org.scalarules.dsl.nl.finance._
import org.scalarules.engine.SingularFact
import org.scalarules.utils.TestDslTestGlossary.{BedragPerJaarA, BedragPerJaarB}

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
  val BedragPerJaarA: SingularFact[Bedrag Per Jaar] = new SingularFact[Bedrag Per Jaar]("BedragPerJaarA")
  val BedragPerJaarB: SingularFact[Bedrag Per Jaar] = new SingularFact[Bedrag Per Jaar]("BedragPerJaarB")
}


