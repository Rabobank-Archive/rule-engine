package org.scalarules.engine

import org.scalatest.{FlatSpec, Matchers}

class FactTest extends FlatSpec with Matchers {
  val context: Context = Map(SingularFact("naamAanwezig") -> 42, SingularFact("onverwachtObject") -> "String")

  it should "return a Some(Bedrag) when given a String that is present in the context" in {
    val optie = SingularFact("naamAanwezig").toEval
    optie(context) should be (Some(42))
  }

  it should "return a None when given a String that is not present in the context" in {
    val optie = SingularFact("naamNietAanwezig").toEval
    optie(context) should be (None)
  }

  it should "not throw an exception when given a context-String that results in a different type than expected" in {
    val optie = SingularFact[Int]("onverwachtObject").toEval
    optie(context) should be (Some("String"))
  }
}
