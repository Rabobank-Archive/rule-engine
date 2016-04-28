package org.scalarules.dsl.core

import org.scalarules.dsl.nl.finance._
import org.scalarules.dsl.nl.grammar._
import LijstBewerkingenGlossary$._
import org.scalarules.dsl.core.grammar.DslEvaluation
import org.scalarules.dsl.core.grammar.DslCondition._
import org.scalarules.engine._

class NietLijstBewerkingen extends Berekening (
  Gegeven (altijd)
  Bereken
    GetalC is GetalA * GetalB en
    GetalD is GetalA + GetalB en
    GetalE is GetalA - GetalB en
    GetalF is GetalA / GetalB
)

class LijstOptellingen extends Berekening (
  Gegeven (altijd)
  Bereken
    OptellingLijstEnLijst is InvoerLijstA + InvoerLijstB en
    OptellingLijstEnGetal is InvoerLijstA + GetalA en
    OptellingGetalEnLijst is GetalA + InvoerLijstA en
    OptellingEnkeleLijstEnEnkeleLijst is InvoerLijstEnkeleC + InvoerLijstEnkeleD en
    OptellingEnkeleLijstEnGetal is InvoerLijstEnkeleC + GetalA en
    OptellingGetalEnEnkeleLijst is GetalA + InvoerLijstEnkeleD
)

class LijstSubtracties extends Berekening (
  Gegeven (altijd)
  Bereken
    SubtractieLijstEnLijst is InvoerLijstA - InvoerLijstB en
    SubtractieLijstEnGetal is InvoerLijstA - GetalA en
    SubtractieGetalEnLijst is GetalA - InvoerLijstA en
    SubtractieEnkeleLijstEnEnkeleLijst is InvoerLijstEnkeleC - InvoerLijstEnkeleD en
    SubtractieEnkeleLijstEnGetal is InvoerLijstEnkeleC - GetalA en
    SubtractieGetalEnEnkeleLijst is GetalA - InvoerLijstEnkeleD en
    SubtractieLijstVanLijstResultaat is (substractie van SubtractieLijstVanLijst)
)

class LijstVermenigvuldigingen extends Berekening (
  Gegeven (altijd)
  Bereken
    VermenigvuldigingLijstEnLijst is InvoerLijstA * InvoerLijstB en
    VermenigvuldigingLijstEnGetal is InvoerLijstA * GetalA en
    VermenigvuldigingGetalEnLijst is GetalA * InvoerLijstA en
    VermenigvuldigingEnkeleLijstEnEnkeleLijst is InvoerLijstEnkeleC * InvoerLijstEnkeleD en
    VermenigvuldigingEnkeleLijstEnGetal is InvoerLijstEnkeleC * GetalA en
    VermenigvuldigingGetalEnEnkeleLijst is GetalA * InvoerLijstEnkeleD
)

class LijstDelingen extends Berekening (
  Gegeven (altijd)
  Bereken
    DelingLijstEnLijst is InvoerLijstA / InvoerLijstB en
    DelingLijstEnGetal is InvoerLijstA / GetalA en
    DelingGetalEnLijst is GetalA / InvoerLijstA en
    DelingEnkeleLijstEnEnkeleLijst is InvoerLijstEnkeleC / InvoerLijstEnkeleD en
    DelingEnkeleLijstEnGetal is InvoerLijstEnkeleC / GetalA en
    DelingGetalEnEnkeleLijst is GetalA / InvoerLijstEnkeleD
)

class LijstElementKeuzes extends Berekening (
  Gegeven (altijd)
  Bereken
    EersteElementVan is (element(0) van InvoerLijstA) en
    LaagsteElementA is (laagste van InvoerLijstB) en
    LaagsteElementB is (laagste van InvoerLijstSerieE) en
    LaagsteElementC is (laagste van InvoerLijstF) en
    HoogsteElementA is (hoogste van InvoerLijstB) en
    HoogsteElementB is (hoogste van InvoerLijstSerieE) en
    HoogsteElementC is (hoogste van InvoerLijstF)
)

class LijstGemiddelden extends Berekening (
  Gegeven (altijd)
  Bereken
    GemiddeldeA is (gemiddelde van InvoerLijstB) en
    GemiddeldeB is (gemiddelde van InvoerLijstSerieE) en
    GemiddeldeC is (gemiddelde van InvoerLijstF) en
    GemiddeldeD is (gemiddelde van InvoerLijstEnkeleC) en
    GemiddeldeE is (gemiddelde van InvoerLijstLeegG)

)

class LijstSommaties extends Berekening (
  Gegeven (altijd)
  Bereken
    SommatieA is (totaal van InvoerLijstA) en
    SommatieB is (totaal van InvoerLijstEnkeleC) en
    SommatieC is (totaal van InvoerLijstLeegG)
)

class LijstConditionals extends Berekening (
  Gegeven (altijd)
  Bereken
    AlsDanPerElementA is DummyFunction(InvoerLijstBedragen, 33.procent, 100.procent)
)

class LijstInLijstOptelling extends Berekening (
  Gegeven (altijd)
  Bereken
    LijstInLijstC is LijstInLijstA + LijstInLijstB en
    LijstInLijstOptellingA is (totaal van LijstInLijstA) en
    LijstInLijstBO is (totaal van LijstInLijstInLijstA)
)

object DummyFunction {
  def apply[T](input: DslEvaluation[List[Bedrag]], wegingPositiefInkomen: DslEvaluation[Percentage], wegingNegatiefInkomen: DslEvaluation[Percentage]): DslEvaluation[List[Percentage]] = {
    val newCondition = andCombineConditions(input.condition, wegingPositiefInkomen.condition, wegingNegatiefInkomen.condition)

    DslEvaluation(newCondition, new Evaluation[List[Percentage]] {
      override def apply(c: Context): Option[List[Percentage]] = {
        val wegingsFactorP: Percentage = wegingPositiefInkomen.evaluation(c).get
        val wegingsFactorN: Percentage = wegingNegatiefInkomen.evaluation(c).get

        input.evaluation(c) match {
          case Some(x) => Some(x.map ( a => if (a < 0.euro) wegingsFactorN else wegingsFactorP ))
          case _ => None
        }
      }
    })
  }
}