package org.scalarules.utils

import org.scalarules.engine.SingularFact
import org.scalatest.{FlatSpec, Matchers}

class GlossaryTest extends FlatSpec with Matchers {

  "Glossary" should "not allow two facts with equal names to be added" in {

    intercept[IllegalArgumentException](new Glossary {
      val a = addAndCheckFacts(new SingularFact[String]("factA"))
      val b = addAndCheckFacts(new SingularFact[String]("factA"))
    })

  }

  it should "consider different casing of fact names as equal and not allow them to be added" in {

    intercept[IllegalArgumentException](new Glossary {
      val a = addAndCheckFacts(new SingularFact[String]("factA"))
      val b = addAndCheckFacts(new SingularFact[String]("fActa"))
    })

  }

  it should "allow facts with different names to be created" in {

    new Glossary {
      val a = addAndCheckFacts(new SingularFact[String]("factA"))
      val b = addAndCheckFacts(new SingularFact[String]("factB"))
    }

  }


}
