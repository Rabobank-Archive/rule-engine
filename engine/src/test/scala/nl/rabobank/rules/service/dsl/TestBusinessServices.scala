package nl.rabobank.rules.service.dsl

import nl.rabobank.rules.finance.nl._
import nl.rabobank.rules.service.dsl.BusinessServiceHelper._
import nl.rabobank.rules.service.dsl.BusinessServiceTestGlossary1._
import nl.rabobank.rules.service.dsl.BusinessServiceTestGlossary2._
import nl.rabobank.rules.service.dsl.BusinessServiceTestGlossary3._
import nl.rabobank.rules.service.dsl.NotABusinessServiceTestGlossary1._

class TestBusinessServiceAlles extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1,
    new BusinessServiceTestBerekening2,
    new BusinessServiceTestBerekening3
  ),

  glossaries = List(
    BusinessServiceTestGlossary1,
    BusinessServiceTestGlossary2,
    BusinessServiceTestGlossary3
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1,
    verplichteInvoer2,
    verplichteInvoer3
  ),

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1.euro,
    optioneleInvoer2 -> 2.euro,
    optioneleInvoer3 -> 3.euro
  ),

  uitvoerFacts = List(
    uitvoer1,
    uitvoer2,
    uitvoer3
  )
)

class TestBusinessServiceIllegaalFactVerplicht extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1
  ),

  glossaries = List(
    BusinessServiceTestGlossary1
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1,
    notABusinessServiceVerplichtFact
  ),

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1.euro
  ),

  uitvoerFacts = List(
    uitvoer1
  )
)

class TestBusinessServiceIllegaalFactOptioneel extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1
  ),

  glossaries = List(
    BusinessServiceTestGlossary1
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1
  ),

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1.euro,
    notABusinessServiceOptioneelFact -> 42.euro
  ),

  uitvoerFacts = List(
    uitvoer1
  )
)

class TestBusinessServiceIllegaalFactUitvoer extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1
  ),

  glossaries = List(
    BusinessServiceTestGlossary1
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1
  ),

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1
  ),

  uitvoerFacts = List(
    uitvoer1,
    notABusinessServiceUitvoer
  )
)

class TestBusinessServiceGeenBerekeningen extends BusinessService (
  berekeningen = Nil,

  glossaries = List(
    BusinessServiceTestGlossary1,
    BusinessServiceTestGlossary2,
    BusinessServiceTestGlossary3
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1,
    verplichteInvoer2,
    verplichteInvoer3
  ),

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1,
    optioneleInvoer2 -> 2,
    optioneleInvoer3 -> 3
  ),

  uitvoerFacts = List(
    uitvoer1,
    uitvoer2,
    uitvoer3
  )
)

class TestBusinessServiceGeenGlossaries extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1,
    new BusinessServiceTestBerekening2,
    new BusinessServiceTestBerekening3
  ),

  glossaries = Nil,

  verplichteInvoerFacts = List(
    verplichteInvoer1,
    verplichteInvoer2,
    verplichteInvoer3
  ),

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1,
    optioneleInvoer2 -> 2,
    optioneleInvoer3 -> 3
  ),

  uitvoerFacts = List(
    uitvoer1,
    uitvoer2,
    uitvoer3
  )
)

class TestBusinessServiceGeenVerplichteInvoer extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1,
    new BusinessServiceTestBerekening2,
    new BusinessServiceTestBerekening3
  ),

  glossaries = List(
    BusinessServiceTestGlossary1,
    BusinessServiceTestGlossary2,
    BusinessServiceTestGlossary3
  ),

  verplichteInvoerFacts = Nil,

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1,
    optioneleInvoer2 -> 2,
    optioneleInvoer3 -> 3
  ),

  uitvoerFacts = List(
    uitvoer1,
    uitvoer2,
    uitvoer3
  )
)

class TestBusinessServiceGeenOptioneel extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1,
    new BusinessServiceTestBerekening2,
    new BusinessServiceTestBerekening3
  ),

  glossaries = List(
    BusinessServiceTestGlossary1,
    BusinessServiceTestGlossary2,
    BusinessServiceTestGlossary3
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1,
    verplichteInvoer2,
    verplichteInvoer3,
    optioneleInvoer1,
    optioneleInvoer2,
    optioneleInvoer3
  ),

  optioneleInvoerFacts = Geen,

  uitvoerFacts = List(
    uitvoer1,
    uitvoer2,
    uitvoer3
  )
)

class TestBusinessServiceGeenUitvoer extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1,
    new BusinessServiceTestBerekening2,
    new BusinessServiceTestBerekening3
  ),

  glossaries = List(
    BusinessServiceTestGlossary1,
    BusinessServiceTestGlossary2,
    BusinessServiceTestGlossary3
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1,
    verplichteInvoer2,
    verplichteInvoer3
  ),

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1,
    optioneleInvoer2 -> 2,
    optioneleInvoer3 -> 3
  ),

  uitvoerFacts = Nil
)

class TestBusinessServiceOnlySpecifiedBerekeningen extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1,
    new BusinessServiceTestBerekening2,
    new BusinessServiceTestBerekening3
  ),

  glossaries = List(
    BusinessServiceTestGlossary1,
    BusinessServiceTestGlossary2,
    BusinessServiceTestGlossary3,
    NotABusinessServiceTestGlossary1
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1,
    verplichteInvoer2,
    verplichteInvoer3,
    notABusinessServiceVerplichtFact
  ),

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1,
    optioneleInvoer2 -> 2,
    optioneleInvoer3 -> 3,
    notABusinessServiceOptioneelFact -> 42
  ),

  uitvoerFacts = List(
    uitvoer1,
    uitvoer2,
    uitvoer3,
    notABusinessServiceUitvoer
  )
)

class TestBusinessServiceRedundantlySpecifiedBerekeningen extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1,
    new BusinessServiceTestBerekening2,
    new BusinessServiceTestBerekening1,
    new BusinessServiceTestBerekening3
  ),

  glossaries = List(
    BusinessServiceTestGlossary1,
    BusinessServiceTestGlossary2,
    BusinessServiceTestGlossary3
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1,
    verplichteInvoer2,
    verplichteInvoer3
  ),

  optioneleInvoerFacts = Map(
    optioneleInvoer1 -> 1,
    optioneleInvoer2 -> 2,
    optioneleInvoer3 -> 3
  ),

  uitvoerFacts = List(
    uitvoer1,
    uitvoer2,
    uitvoer3
  )
)

class TestBusinessServiceFactAsBothVerplichtAndOptioneel extends BusinessService (
  berekeningen = List(
    new BusinessServiceTestBerekening1,
    new BusinessServiceTestBerekening2,
    new BusinessServiceTestBerekening3
  ),

  glossaries = List(
    BusinessServiceTestGlossary1,
    BusinessServiceTestGlossary2,
    BusinessServiceTestGlossary3
  ),

  verplichteInvoerFacts = List(
    verplichteInvoer1,
    verplichteInvoer2,
    verplichteInvoer3
  ),

  optioneleInvoerFacts = Map(
    verplichteInvoer1 -> 1,
    optioneleInvoer1 -> 1,
    optioneleInvoer2 -> 2,
    optioneleInvoer3 -> 3
  ),

  uitvoerFacts = List(
    uitvoer1,
    uitvoer2,
    uitvoer3
  )
)
