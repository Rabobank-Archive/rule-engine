package org.scalarules.utils

import org.scalarules.engine.{Fact, ListFact, SingularFact}
import org.scalatest.{FlatSpec, Matchers}

import scala.language.reflectiveCalls

class GlossaryTest extends FlatSpec with Matchers {

  it should "work with macros to define facts" in {

    val g = new Glossary {
      val factA = defineFact[String]("First fact")
      val factB = defineFact[String]("Second fact")
      val factC = defineListFact[String]("Third fact")
      val factD = defineListFact[String]("Fourth fact")
    }

    g.factA.name should be("factA")
    g.factB.name should be("factB")
    g.factC.name should be("factC")
    g.factD.name should be("factD")

    g.factA.isInstanceOf[SingularFact[String]] should be(true)
    g.factB.isInstanceOf[SingularFact[String]] should be(true)
    g.factC.isInstanceOf[ListFact[String]] should be(true)
    g.factD.isInstanceOf[ListFact[String]] should be(true)

  }

}
