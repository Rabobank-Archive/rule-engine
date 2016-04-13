package org.scalarules.dsl.core

import org.scalarules.dsl.nl.finance._
import LijstBewerkingenGlossary$._
import org.scalarules.dsl.nl.grammar.aanwezig
import org.scalarules.utils.{InternalBerekeningenTester, lijst}

class LijstBewerkingenTest extends InternalBerekeningenTester(new NietLijstBewerkingen, new LijstOptellingen, new LijstSubtracties, new LijstVermenigvuldigingen,
  new LijstDelingen, new LijstElementKeuzes, new LijstGemiddelden, new LijstSommaties, new LijstConditionals, new LijstInLijstOptelling) {

  val standaardInvoer = waardes(
    InvoerLijstA is List(1, 2, 3, 4, 5, 6, 7, 8),
    InvoerLijstB is List(1, 2, 3, 4, 5, 6, 7, 8),
    InvoerLijstEnkeleC is List(1),
    InvoerLijstEnkeleD is List(10),
    InvoerLijstSerieE is (lijst van 10 waarde 10),
    InvoerLijstF is List(8, 7, 6, 5, 4, 3, 2, 1),
    InvoerLijstLeegG is List(),
    GetalA is 10
  )

  test("eenvoudige getal-bewerkingen") gegeven (
    GetalA is 10,
    GetalB is 20
  ) verwacht (
    GetalC is 200,
    GetalD is 30,
    GetalE is -10,
    GetalF is 0.5
  )

  test("optellen van lijsten") gegeven (
    standaardInvoer
  ) verwacht (
    OptellingLijstEnLijst is List(2, 4, 6, 8, 10, 12, 14, 16),
    OptellingLijstEnGetal is List(11, 12, 13, 14, 15, 16, 17, 18),
    OptellingGetalEnLijst is List(11, 12, 13, 14, 15, 16, 17, 18),
    OptellingEnkeleLijstEnEnkeleLijst is List(11),
    OptellingEnkeleLijstEnGetal is List(11),
    OptellingGetalEnEnkeleLijst is List(20)
  )

  test("aftrekken van lijsten") gegeven (
    standaardInvoer
  ) verwacht (
    SubtractieLijstEnLijst is List(0, 0, 0, 0, 0, 0, 0, 0),
    SubtractieLijstEnGetal is List(-9, -8, -7, -6, -5, -4, -3, -2),
    SubtractieGetalEnLijst is List(9, 8, 7, 6, 5, 4, 3, 2),
    SubtractieEnkeleLijstEnEnkeleLijst is List(-9),
    SubtractieEnkeleLijstEnGetal is List(-9),
    SubtractieGetalEnEnkeleLijst is List(0)
  )

  test("aftrekken van lijsten van lijsten") gegeven (
    SubtractieLijstVanLijst is List(List(10,10,10,10,10), List(5,4,3,2,1), List(1,2,3,4,5), List(4, 4, 4, 4, 4))
  ) verwacht (
    SubtractieLijstVanLijstResultaat is List(0, 0, 0, 0, 0)
  )

  test("vermenigvuldigen van lijsten") gegeven (
    standaardInvoer
    ) verwacht (
    VermenigvuldigingLijstEnLijst is List(1, 4, 9, 16, 25, 36, 49, 64),
    VermenigvuldigingLijstEnGetal is List(10, 20, 30, 40, 50, 60, 70, 80),
    VermenigvuldigingGetalEnLijst is List(10, 20, 30, 40, 50, 60, 70, 80),
    VermenigvuldigingEnkeleLijstEnEnkeleLijst is List(10),
    VermenigvuldigingEnkeleLijstEnGetal is List(10),
    VermenigvuldigingGetalEnEnkeleLijst is List(100)
  )

  test("deling van lijsten") gegeven (
    standaardInvoer
  ) verwacht (
    DelingLijstEnLijst is List(1, 1, 1, 1, 1, 1, 1, 1),
    DelingLijstEnGetal is List(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8),
    DelingGetalEnLijst is List(10, 5, BigDecimal(10) / 3, 2.5, 2, BigDecimal(10) / 6, BigDecimal(10) / 7, BigDecimal(10) / 8),
    DelingEnkeleLijstEnEnkeleLijst is List(0.1),
    DelingEnkeleLijstEnGetal is List(0.1),
    DelingGetalEnEnkeleLijst is List(1)
  )

  test("element selectie uit lijst met vaste index") gegeven (
    standaardInvoer
  ) verwacht (
    EersteElementVan is 1
  )

  test("lijst van X waarde Y werkt in test DSL") gegeven (
    standaardInvoer
  ) verwacht (
    InvoerLijstSerieE is List(10, 10, 10, 10, 10, 10, 10, 10, 10, 10)
  )

  test("bepaal laagste element uit lijst") gegeven (
    standaardInvoer
  ) verwacht (
    LaagsteElementA is 1,
    LaagsteElementB is 10,
    LaagsteElementC is 1
  )

  test("bepaal hoogste element uit lijst") gegeven (
    standaardInvoer
  ) verwacht (
    HoogsteElementA is 8,
    HoogsteElementB is 10,
    HoogsteElementC is 8
  )

  test("bereken gemiddelde") gegeven (
    standaardInvoer
  ) verwacht (
    GemiddeldeA is 4.5,
    GemiddeldeB is 10,
    GemiddeldeC is 4.5,
    GemiddeldeD is 1,
    GemiddeldeE niet aanwezig
  )

  test("bereken sommaties") gegeven (
    standaardInvoer
  ) verwacht (
    SommatieA is 36,
    SommatieB is 1,
    SommatieC niet aanwezig
  )

  test("bepalen inkomen wegingsfactor") gegeven (
    InvoerLijstBedragen is List(10.euro, "-4".euro, 100.euro)
  ) verwacht (
    AlsDanPerElementA is List(33.procent, 100.procent, 33.procent)
  )

  test("optellen van Lijst in Lijst") gegeven (
    LijstInLijstA is List(List(1, 2), List(10, 20)),
    LijstInLijstB is List(List(1, 2), List(10, 20))
  ) verwacht (
    LijstInLijstC is List(List(2, 4), List(20, 40))
  )

  test("sommeren van Lijst in Lijst") gegeven (
    LijstInLijstA is List(List(1, 2), List(10, 20))
  ) verwacht (
    LijstInLijstOptellingA is List(11, 22)
  )

  test("sommeren van Lijst in Lijst in Lijst") gegeven (
    LijstInLijstInLijstA is List(
      List(List(1, 2, 3), List(1, 2, 3)),
      List(List(10, 20, 30), List(10, 20, 30))
    )
  ) verwacht (
    LijstInLijstBO is List(
      List(11, 22, 33), List(11, 22, 33)
    )
  )

  behavior of ("lijsten met verschillende lengtes")

  test("of lijsten van verschillende lengte kunnen worden opgeteld") gegeven (
    InvoerLijstA is List[BigDecimal](1, 2, 3, 4),
    InvoerLijstB is List[BigDecimal](1, 2)
  ) verwacht (
    OptellingLijstEnLijst is List(2, 4, 3, 4)
  )

  test("of lijsten van verschillende lengte kunnen worden afgetrokken") gegeven (
    InvoerLijstA is List[BigDecimal](1, 2, 3, 4),
    InvoerLijstB is List[BigDecimal](1, 2)
  ) verwacht (
    SubtractieLijstEnLijst is List(0, 0, 3, 4)
  )


  test("of lijsten van verschillende lengte kunnen worden vermenigvuldigd") gegeven (
    InvoerLijstA is List[BigDecimal](1, 2, 3, 4),
    InvoerLijstB is List[BigDecimal](1, 2)
  ) verwacht (
    VermenigvuldigingLijstEnLijst is List(1, 4)
  )

  test("of lijsten van verschillende lengte kunnen worden gedeeld") gegeven (
    InvoerLijstA is List[BigDecimal](1, 2, 3, 4),
    InvoerLijstB is List[BigDecimal](1, 2)
  ) verwacht (
    DelingLijstEnLijst is List(1, 1)
  )

}
