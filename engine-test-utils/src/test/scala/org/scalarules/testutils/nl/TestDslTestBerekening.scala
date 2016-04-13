package org.scalarules.testutils.nl

import org.scalarules.dsl.nl.grammar._
import org.scalarules.testutils.nl.TestDslTestGlossary._

class TestDslTestBerekening extends Berekening (
  Gegeven(altijd)
    Bereken
    BedragPerJaarB is BedragPerJaarA
)
