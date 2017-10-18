package nl.rabobank.oss.rules.utils

import nl.rabobank.oss.rules.dsl.nl.grammar._
import nl.rabobank.oss.rules.utils.TestDslTestGlossary._

class TestDslTestBerekening extends Berekening (
  Gegeven(altijd)
    Bereken
    BedragPerJaarB is BedragPerJaarA
)
