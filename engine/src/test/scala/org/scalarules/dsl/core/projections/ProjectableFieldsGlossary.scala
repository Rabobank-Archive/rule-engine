package org.scalarules.dsl.core.projections

import org.scalarules.utils.MacroGlossary

object ProjectableFieldsGlossary extends MacroGlossary {
  val ComplexFact = defineFact[ComplexObject]
  val IntFact = defineFact[Int]
  val IntFact2 = defineFact[Int]

  val ComplexFactList = defineListFact[ComplexObject]
  val StringFactList = defineListFact[String]
}
