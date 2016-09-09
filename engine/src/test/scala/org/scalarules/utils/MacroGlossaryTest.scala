package org.scalarules.utils

import org.scalarules.engine.{ListFact, SingularFact}
import org.scalatest.{FlatSpec, Matchers}

import scala.language.reflectiveCalls

class MacroGlossaryTest extends FlatSpec with Matchers {

  it should "work with macros to define facts" in {

    val firstDescription = "First fact"
    val secondDescription = "Second fact"
    val thirdDescription = "Third fact"
    val fourthDescription = "Fourth fact"


    val g = new Glossary {
      val factA = defineFact[String](firstDescription)
      val factB = defineFact[String](secondDescription)
      val factC = defineListFact[String](thirdDescription)
      val factD = defineListFact[String](fourthDescription)
    }

    g.factA.name should be("factA")
    g.factB.name should be("factB")
    g.factC.name should be("factC")
    g.factD.name should be("factD")

    g.factA.isInstanceOf[SingularFact[String]] should be(true)
    g.factB.isInstanceOf[SingularFact[String]] should be(true)
    g.factC.isInstanceOf[ListFact[String]] should be(true)
    g.factD.isInstanceOf[ListFact[String]] should be(true)

    g.facts.size should be(4)

    g.facts.get("factA").get should be(g.factA)
    g.facts.get("factB").get should be(g.factB)
    g.facts.get("factC").get should be(g.factC)
    g.facts.get("factD").get should be(g.factD)

    g.facts.get("factA").get.description should be(firstDescription)
    g.facts.get("factB").get.description should be(secondDescription)
    g.facts.get("factC").get.description should be(thirdDescription)
    g.facts.get("factD").get.description should be(fourthDescription)
  }

  it should "store concrete value type in the Fact on creation" in {
    val g = new Glossary {
      val stringFact = defineFact[String]
      val intFact = defineFact[Int]
      val intListFact = defineListFact[Int]
    }

    g.stringFact.valueType should be("String")
    g.intFact.valueType should be("Int")
    g.intListFact.valueType should be("Int")
  }

}
