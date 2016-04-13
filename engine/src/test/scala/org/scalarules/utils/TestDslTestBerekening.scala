package org.scalarules.utils

import org.scalarules.dsl.nl.grammar._
import org.scalarules.utils.TestDslTestGlossary._

class TestDslTestBerekening extends Berekening (
  Gegeven(altijd)
    Bereken
    BedragPerJaarB is BedragPerJaarA
)
