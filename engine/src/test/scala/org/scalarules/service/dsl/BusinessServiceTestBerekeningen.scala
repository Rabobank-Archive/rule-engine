package org.scalarules.service.dsl

import org.scalarules.dsl.nl.grammar._
import org.scalarules.service.dsl.BusinessServiceTestGlossary1._
import org.scalarules.service.dsl.BusinessServiceTestGlossary2._
import org.scalarules.service.dsl.BusinessServiceTestGlossary3._
import org.scalarules.service.dsl.NotABusinessServiceTestGlossary1._
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
