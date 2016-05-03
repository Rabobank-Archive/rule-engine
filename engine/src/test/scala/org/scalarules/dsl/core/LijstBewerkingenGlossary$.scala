package org.scalarules.dsl.core

import org.scalarules.finance.nl.{Bedrag, Percentage}
import org.scalarules.utils.Glossary

object LijstBewerkingenGlossary$ extends Glossary {

  val GetalA = defineFact[BigDecimal]("GetalA")
  val GetalB = defineFact[BigDecimal]("GetalB")
  val GetalC = defineFact[BigDecimal]("GetalC")
  val GetalD = defineFact[BigDecimal]("GetalD")
  val GetalE = defineFact[BigDecimal]("GetalE")
  val GetalF = defineFact[BigDecimal]("GetalF")

  val BedragA = defineFact[Bedrag]("BedragA")
  val BedragB = defineFact[Bedrag]("BedragB")

  val InvoerLijstA = defineListFact[BigDecimal]("InvoerLijstA")
  val InvoerLijstB = defineListFact[BigDecimal]("InvoerLijstB")
  val InvoerLijstEnkeleC = defineListFact[BigDecimal]("InvoerLijstEnkeleC")
  val InvoerLijstEnkeleD = defineListFact[BigDecimal]("InvoerLijstEnkeleD")
  val InvoerLijstSerieE = defineListFact[BigDecimal]("InvoerLijstSerieE")
  val InvoerLijstF = defineListFact[BigDecimal]("InvoerLijstF")
  val InvoerLijstLeegG = defineListFact[BigDecimal]("InvoerLijstLeegG")
  val InvoerLijstVanLijstA = defineListFact[List[BigDecimal]]("InvoerLijstVanLijstA")

  val InvoerLijstBedragen = defineListFact[Bedrag]("InvoerLijstBedragen")

  val OptellingLijstEnLijst = defineListFact[BigDecimal]("OptellingLijstEnLijst")
  val OptellingLijstEnGetal = defineListFact[BigDecimal]("OptellingLijstEnGetal")
  val OptellingGetalEnLijst = defineListFact[BigDecimal]("OptellingGetalEnLijst")
  val OptellingEnkeleLijstEnEnkeleLijst = defineListFact[BigDecimal]("OptellingEnkeleLijstEnEnkeleLijst")
  val OptellingEnkeleLijstEnGetal = defineListFact[BigDecimal]("OptellingEnkeleLijstEnGetal")
  val OptellingGetalEnEnkeleLijst = defineListFact[BigDecimal]("OptellingGetalEnEnkeleLijst")

  val SubtractieLijstVanLijstResultaat = defineListFact[BigDecimal]("SubtractieLijstVanLijstResultaat")
  val SubtractieLijstVanLijst = defineListFact[List[BigDecimal]]("SubtractieLijstVanLijst")

  val SubtractieLijstEnLijst = defineListFact[BigDecimal]("SubtractieLijstEnLijst")
  val SubtractieLijstEnGetal = defineListFact[BigDecimal]("SubtractieLijstEnGetal")
  val SubtractieGetalEnLijst = defineListFact[BigDecimal]("SubtractieGetalEnLijst")
  val SubtractieEnkeleLijstEnEnkeleLijst = defineListFact[BigDecimal]("SubtractieEnkeleLijstEnEnkeleLijst")
  val SubtractieEnkeleLijstEnGetal = defineListFact[BigDecimal]("SubtractieEnkeleLijstEnGetal")
  val SubtractieGetalEnEnkeleLijst = defineListFact[BigDecimal]("SubtractieGetalEnEnkeleLijst")

  val VermenigvuldigingLijstEnLijst = defineListFact[BigDecimal]("VermenigvuldigingLijstEnLijst")
  val VermenigvuldigingLijstEnGetal = defineListFact[BigDecimal]("VermenigvuldigingLijstEnGetal")
  val VermenigvuldigingGetalEnLijst = defineListFact[BigDecimal]("VermenigvuldigingGetalEnLijst")
  val VermenigvuldigingEnkeleLijstEnEnkeleLijst = defineListFact[BigDecimal]("VermenigvuldigingEnkeleLijstEnEnkeleLijst")
  val VermenigvuldigingEnkeleLijstEnGetal = defineListFact[BigDecimal]("VermenigvuldigingEnkeleLijstEnGetal")
  val VermenigvuldigingGetalEnEnkeleLijst = defineListFact[BigDecimal]("VermenigvuldigingGetalEnEnkeleLijst")

  val DelingLijstEnLijst = defineListFact[BigDecimal]("DelingLijstEnLijst")
  val DelingLijstEnGetal = defineListFact[BigDecimal]("DelingLijstEnGetal")
  val DelingGetalEnLijst = defineListFact[BigDecimal]("DelingGetalEnLijst")
  val DelingEnkeleLijstEnEnkeleLijst = defineListFact[BigDecimal]("DelingEnkeleLijstEnEnkeleLijst")
  val DelingEnkeleLijstEnGetal = defineListFact[BigDecimal]("DelingEnkeleLijstEnGetal")
  val DelingGetalEnEnkeleLijst = defineListFact[BigDecimal]("DelingGetalEnEnkeleLijst")

  val EersteElementVan = defineFact[BigDecimal]("EersteElementVan")

  val LaagsteElementA = defineFact[BigDecimal]("LaagsteElementA")
  val LaagsteElementB = defineFact[BigDecimal]("LaagsteElementB")
  val LaagsteElementC = defineFact[BigDecimal]("LaagsteElementC")

  val HoogsteElementA = defineFact[BigDecimal]("HoogsteElementA")
  val HoogsteElementB = defineFact[BigDecimal]("HoogsteElementB")
  val HoogsteElementC = defineFact[BigDecimal]("HoogsteElementC")

  val GemiddeldeA = defineFact[BigDecimal]("GemiddeldeA")
  val GemiddeldeB = defineFact[BigDecimal]("GemiddeldeB")
  val GemiddeldeC = defineFact[BigDecimal]("GemiddeldeC")
  val GemiddeldeD = defineFact[BigDecimal]("GemiddeldeD")
  val GemiddeldeE = defineFact[BigDecimal]("GemiddeldeE")
  val GemiddeldeListA = defineListFact[BigDecimal]("GemiddeldeListA")

  val SommatieA = defineFact[BigDecimal]("SommatieA")
  val SommatieB = defineFact[BigDecimal]("SommatieB")
  val SommatieC = defineFact[BigDecimal]("SommatieC")
  val SommatieListA = defineListFact[BigDecimal]("SommatieListA")

  val AlsDanPerElementA = defineListFact[Percentage]("AlsDanPerElementA")

  val LijstInLijstA = defineListFact[List[BigDecimal]]("LijstInLijstA")
  val LijstInLijstB = defineListFact[List[BigDecimal]]("LijstInLijstB")
  val LijstInLijstC = defineListFact[List[BigDecimal]]("LijstInLijstC")

  val LijstInLijstOptellingA = defineListFact[BigDecimal]("LijstInLijstOptellingA")

  val LijstInLijstInLijstA = defineListFact[List[List[BigDecimal]]]("LijstInLijstInLijstA")
  val LijstInLijstBO = defineListFact[List[BigDecimal]]("LijstInLijstBO")
}
