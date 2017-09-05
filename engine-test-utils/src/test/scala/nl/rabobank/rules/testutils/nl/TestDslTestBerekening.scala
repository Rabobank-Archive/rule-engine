package nl.rabobank.rules.testutils.nl

import nl.rabobank.rules.dsl.nl.grammar._
import nl.rabobank.rules.testutils.nl.TestDslTestGlossary._

class TestDslTestBerekening extends Berekening (
  Gegeven(altijd)
    Bereken
    BedragPerJaarB is BedragPerJaarA
)
