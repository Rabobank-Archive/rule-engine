package nl.rabobank.oss.rules.testutils.nl

import nl.rabobank.oss.rules.dsl.nl.grammar._
import nl.rabobank.oss.rules.testutils.nl.TestDslTestGlossary._

class TestDslTestBerekening extends Berekening (
  Gegeven(altijd)
    Bereken
    BedragPerJaarB is BedragPerJaarA
)
