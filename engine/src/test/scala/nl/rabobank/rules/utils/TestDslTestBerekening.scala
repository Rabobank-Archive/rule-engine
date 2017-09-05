package nl.rabobank.rules.utils

import nl.rabobank.rules.dsl.nl.grammar._
import nl.rabobank.rules.utils.TestDslTestGlossary._

class TestDslTestBerekening extends Berekening (
  Gegeven(altijd)
    Bereken
    BedragPerJaarB is BedragPerJaarA
)
