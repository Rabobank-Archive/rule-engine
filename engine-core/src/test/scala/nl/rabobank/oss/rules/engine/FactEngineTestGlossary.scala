package nl.rabobank.oss.rules.engine

import nl.rabobank.oss.rules.facts.{ListFact, SingularFact}

object FactEngineTestGlossary {
  val Costs = SingularFact[BigDecimal]("Costsp")
  val EstimatedNewValue = SingularFact[BigDecimal]("EstimatedNewValue")
  val EstimatedValue = SingularFact[BigDecimal]("EstimatedValue")
  val InterestValue = SingularFact[BigDecimal]("InterestValue")
  val PurchaseAmount = SingularFact[BigDecimal]("PurchaseAmount")
  val LumpSum = SingularFact[BigDecimal]("LumpSum")
  val GoodsValue = SingularFact[BigDecimal]("GoodsValue")
  val MarketValue = SingularFact[BigDecimal]("MarketValue")
  val MarketValueAlternative = SingularFact[BigDecimal]("MarketValueAlternative")
  val MarketValueAdded = SingularFact[BigDecimal]("MarketValueAdded")
  val MarketValueReduced = SingularFact[BigDecimal]("MarketValueReduced")
  val BuildingValue = SingularFact[BigDecimal]("BuildingValue")
  val PropertyValue = SingularFact[BigDecimal]("PropertyValue")
  val ConstructionValue = SingularFact[BigDecimal]("ConstructionValue")
  val ConstructionCost = SingularFact[BigDecimal]("ConstructionCost")
  val BuildingCost = SingularFact[BigDecimal]("BuildingCost")
  val TaxationValue = SingularFact[BigDecimal]("TaxationValue")
  val SubRunInput = ListFact[BigDecimal]("SubRunInput")
  val SubRunOutput = ListFact[BigDecimal]("SubRunOutput")
  val SubRunIntermediateInput = SingularFact[BigDecimal]("SubRunIntermediateInput")
  val SubRunIntermediateResult = SingularFact[BigDecimal]("SubRunIntermediateResult")

  val SpeedOfLightMetresPerSecondConstant = SingularFact[BigDecimal]("SpeedOfLightMetresPerSecondConstant")
  val TimeLightTravelsToEarthInSecondsConstant = SingularFact[BigDecimal]("TimeLightTravelsToEarthInSecondsConstant")
  val DistanceBetweenEarthAndSunInMetres = SingularFact[BigDecimal]("DistanceBetweenEarthAndSunInMetres")
}
