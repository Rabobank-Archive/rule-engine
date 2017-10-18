package nl.rabobank.oss.rules.service.dsl

import nl.rabobank.oss.rules.dsl.nl.grammar._
import BusinessServiceTestGlossary1._
import BusinessServiceTestGlossary2._
import BusinessServiceTestGlossary3._
import NotABusinessServiceTestGlossary1._
import nl.rabobank.oss.rules.dsl.nl.grammar.Berekening

import scala.language.postfixOps


class BusinessServiceTestBerekening1 extends Berekening (

  Gegeven(altijd) Bereken
    uitvoer1 is (verplichteInvoer1 + optioneleInvoer1)

)

class BusinessServiceTestBerekening2 extends Berekening (

  Gegeven(altijd) Bereken
    uitvoer2 is (verplichteInvoer2 + optioneleInvoer2)

)

class BusinessServiceTestBerekening3 extends Berekening (

  Gegeven(altijd) Bereken
    uitvoer3 is (verplichteInvoer3 + optioneleInvoer3)

)

class NotABusinessServiceTestBerekening1 extends Berekening (

  Gegeven(altijd) Bereken
    notABusinessServiceUitvoer is (notABusinessServiceVerplichtFact + notABusinessServiceOptioneelFact)

)
