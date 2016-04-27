package org.scalarules.dsl.core.projections

import org.scalarules.utils.Glossary

object ProjectableFieldsGlossary extends Glossary {
  val ComplexFact = defineFact[ComplexObject]("ComplexFact")
  val IntFact = defineFact[Int]("IntFact")
  val IntFact2 = defineFact[Int]("IntFact2")
}
