package org.scalarules.utils

import org.scalarules.finance.nl.{Bedrag, Per}
import org.scalarules.dsl.nl.grammar.{Aanwezigheid, Berekening}
import org.scalarules.engine.{Context, FactEngine}
import org.scalarules.facts.Fact
import org.scalatest.{FlatSpec, Matchers}

// TODO : Make into English and move the Dutch specific parts to a Dutch dsl package
class InternalBerekeningenTester(verplichteBerekening: Berekening, optioneleBerekeningen: Berekening*) extends FlatSpec with Matchers {

  val berekeningen = (verplichteBerekening :: optioneleBerekeningen.toList).flatMap(_.berekeningen)
  def waardes(factValues: FactValues*): FactValues = FactValues( factValues flatMap (_.tuples) )
  def test(description: String): ResultOfTest = new ResultOfTest(description, this)

  def runTest(description: String, context: Context, factValues: Seq[FactValues]): Unit = it should description in {
    val result = FactEngine.runNormalDerivations(context, berekeningen)

    val verwachteWaardes: Seq[(Fact[Any], Any)] = factValues flatMap (_.tuples)
    verwachteWaardes foreach { factValue => assert(result, factValue._1, factValue._2) }
  }

  def debugTest(description: String, context: Context, factValues: Seq[FactValues]): Unit = it should description in {
    val result = FactEngine.runDebugDerivations(context, berekeningen)
    println(PrettyPrinter.printSteps(result._2))

    val verwachteWaardes: Seq[(Fact[Any], Any)] = factValues flatMap (_.tuples)
    verwachteWaardes foreach { factValue => assert(result._1, factValue._1, factValue._2) }
  }

  protected def assert[A](result: Context, fact: Fact[A], value: A) = {
    fact.toEval(result) match {
      case Some(x) if value == None => fail(s"Feit ${fact.name} wordt verwacht niet aanwezig te zijn, maar heeft waarde $x")
      case Some(x) => assertValue(x, value)
      case None if value == None => // What to do?? --> Nothing.. You interpret Nil as 'expected not to be present', which is exactly what the None result means
      case _ => fail(s"Feit ${fact.name} is niet beschikbaar in het resultaat. Waarde $value werd verwacht")
    }
  }

  // TODO : Extract these into ValueInspectors which we can add new instances of to some map/list of inspectors (maybe even just integrate with ScalaTest)
  private def assertValue[A](actual: A, expected: A): Unit = {
    actual match {
      case x: List[Any] if expected != None => {
        assert(x.length == expected.asInstanceOf[List[Any]].length, "Lists sizes mismatch")
        x.zip(expected.asInstanceOf[List[Any]]).foreach( a => assertValue (a._1, a._2) )
      }
      case x: BigDecimal if expected != None => x.setScale(24, BigDecimal.RoundingMode.HALF_EVEN) should be (expected.asInstanceOf[BigDecimal].setScale(24, BigDecimal.RoundingMode.HALF_EVEN))
      case x: Bedrag if expected != None => x.afgerondOpCenten should be (expected.asInstanceOf[Bedrag].afgerondOpCenten)
      // TODO: This is a bit ugly ... maybe we have to create a Per-companion object with appropriate unapply methods
      case x: Per[_, _] if expected != None && x.waarde.isInstanceOf[Bedrag] => {
        val concreteExpected: Per[Bedrag, _] = expected.asInstanceOf[Per[Bedrag, _]]
        x.waarde.asInstanceOf[Bedrag].afgerondOpCenten should be (concreteExpected.waarde.afgerondOpCenten)
        x.termijn should be (concreteExpected.termijn)
      }
      case x: Any if expected != None => x should be (expected)
    }
  }

  implicit class FactToFactValues[A](fact: Fact[A]) {
    def is(value: A): FactValues = FactValues(List((fact, value)))
    def niet(aanwezigheid: Aanwezigheid): FactValues = FactValues(List((fact, None)))
  }
}

case class FactValues(tuples: Seq[(Fact[Any], Any)])

class ResultOfTest (description: String, tester: InternalBerekeningenTester) extends Matchers {
  def gegeven(factValues: FactValues*): ResultOfGegeven = new ResultOfGegeven(factValues.flatMap(_.tuples).toMap, description, tester)
}

class ResultOfGegeven(val context: Context, val description: String, val tester: InternalBerekeningenTester) {
  def verwacht(factValues: FactValues*): Unit = tester.runTest(description, context, factValues)
  def debug(factValues: FactValues*): Unit = tester.debugTest(description, context, factValues)
}


//scalastyle:off object.name
object lijst {
  def van(lengte: Int): listIntermediateResult = new listIntermediateResult(lengte)

  class listIntermediateResult(lengte: Int) {
    def waarde[A](value: A) : List[A] = List.fill(lengte)(value)
  }
}
//scalastyle:on object.name
