package org.scalarules.dsl.core

import org.scalarules.finance.nl.{Bedrag, Percentage}
import org.scalarules.utils.MacroGlossary

object LijstBewerkingenGlossary$ extends MacroGlossary {

  val GetalA = defineFact[BigDecimal]
  val GetalB = defineFact[BigDecimal]
  val GetalC = defineFact[BigDecimal]
  val GetalD = defineFact[BigDecimal]
  val GetalE = defineFact[BigDecimal]
  val GetalF = defineFact[BigDecimal]

  val BedragA = defineFact[Bedrag]
  val BedragB = defineFact[Bedrag]

  val InvoerLijstA = defineListFact[BigDecimal]
  val InvoerLijstB = defineListFact[BigDecimal]
  val InvoerLijstEnkeleC = defineListFact[BigDecimal]
  val InvoerLijstEnkeleD = defineListFact[BigDecimal]
  val InvoerLijstSerieE = defineListFact[BigDecimal]
  val InvoerLijstF = defineListFact[BigDecimal]
  val InvoerLijstLeegG = defineListFact[BigDecimal]
  val InvoerLijstVanLijstA = defineListFact[List[BigDecimal]]

  val InvoerLijstBedragen = defineListFact[Bedrag]

  val OptellingLijstEnLijst = defineListFact[BigDecimal]
  val OptellingLijstEnGetal = defineListFact[BigDecimal]
  val OptellingGetalEnLijst = defineListFact[BigDecimal]
  val OptellingEnkeleLijstEnEnkeleLijst = defineListFact[BigDecimal]
  val OptellingEnkeleLijstEnGetal = defineListFact[BigDecimal]
  val OptellingGetalEnEnkeleLijst = defineListFact[BigDecimal]

  val SubtractieLijstVanLijstResultaat = defineListFact[BigDecimal]
  val SubtractieLijstVanLijst = defineListFact[List[BigDecimal]]

  val SubtractieLijstEnLijst = defineListFact[BigDecimal]
  val SubtractieLijstEnGetal = defineListFact[BigDecimal]
  val SubtractieGetalEnLijst = defineListFact[BigDecimal]
  val SubtractieEnkeleLijstEnEnkeleLijst = defineListFact[BigDecimal]
  val SubtractieEnkeleLijstEnGetal = defineListFact[BigDecimal]
  val SubtractieGetalEnEnkeleLijst = defineListFact[BigDecimal]

  val VermenigvuldigingLijstEnLijst = defineListFact[BigDecimal]
  val VermenigvuldigingLijstEnGetal = defineListFact[BigDecimal]
  val VermenigvuldigingGetalEnLijst = defineListFact[BigDecimal]
  val VermenigvuldigingEnkeleLijstEnEnkeleLijst = defineListFact[BigDecimal]
  val VermenigvuldigingEnkeleLijstEnGetal = defineListFact[BigDecimal]
  val VermenigvuldigingGetalEnEnkeleLijst = defineListFact[BigDecimal]

  val DelingLijstEnLijst = defineListFact[BigDecimal]
  val DelingLijstEnGetal = defineListFact[BigDecimal]
  val DelingGetalEnLijst = defineListFact[BigDecimal]
  val DelingEnkeleLijstEnEnkeleLijst = defineListFact[BigDecimal]
  val DelingEnkeleLijstEnGetal = defineListFact[BigDecimal]
  val DelingGetalEnEnkeleLijst = defineListFact[BigDecimal]

  val EersteElementVan = defineFact[BigDecimal]

  val LaagsteElementA = defineFact[BigDecimal]
  val LaagsteElementB = defineFact[BigDecimal]
  val LaagsteElementC = defineFact[BigDecimal]

  val HoogsteElementA = defineFact[BigDecimal]
  val HoogsteElementB = defineFact[BigDecimal]
  val HoogsteElementC = defineFact[BigDecimal]

  val GemiddeldeA = defineFact[BigDecimal]
  val GemiddeldeB = defineFact[BigDecimal]
  val GemiddeldeC = defineFact[BigDecimal]
  val GemiddeldeD = defineFact[BigDecimal]
  val GemiddeldeE = defineFact[BigDecimal]
  val GemiddeldeListA = defineListFact[BigDecimal]

  val SommatieA = defineFact[BigDecimal]
  val SommatieB = defineFact[BigDecimal]
  val SommatieC = defineFact[BigDecimal]
  val SommatieListA = defineListFact[BigDecimal]

  val AlsDanPerElementA = defineListFact[Percentage]

  val LijstInLijstA = defineListFact[List[BigDecimal]]
  val LijstInLijstB = defineListFact[List[BigDecimal]]
  val LijstInLijstC = defineListFact[List[BigDecimal]]

  val LijstInLijstOptellingA = defineListFact[BigDecimal]

  val LijstInLijstInLijstA = defineListFact[List[List[BigDecimal]]]
  val LijstInLijstBO = defineListFact[List[BigDecimal]]
}
