package nl.rabobank.oss.rules.dsl.nl.datum

import nl.rabobank.oss.rules.dsl.nl.grammar._
import nl.rabobank.oss.rules.engine.FactEngine
import nl.rabobank.oss.rules.engine.Context
import nl.rabobank.oss.rules.facts.SingularFact
import org.scalatest.{FlatSpec, Matchers}

class DatumImplicitsTest extends FlatSpec with Matchers {

  val sutDatumA = new SingularFact[Datum]("sutDatumA")
  val sutDatumB = new SingularFact[Datum]("sutDatumB")
  val result = new SingularFact[Boolean]("result")
  val expected = new SingularFact[Boolean]("expected")

  it should "compile" in {
    Gegeven(sutDatumA < sutDatumB)
    Gegeven(sutDatumA <= sutDatumB)
    Gegeven(sutDatumA > sutDatumB)
    Gegeven(sutDatumA >= sutDatumB)
    Gegeven(sutDatumA is sutDatumB)
  }

  it should "compare dates" in {
    check( Gegeven(sutDatumA < sutDatumB), true, false, false )
  }

  def check(gegeven: GegevenWord, expectSmaller: Boolean, expectEqual: Boolean, expectGreater: Boolean): Unit = {
    val accumulator: BerekeningAccumulator = gegeven Bereken( result ) is( expected )

    val resultContext: Context = FactEngine.runNormalDerivations(Map(sutDatumA -> "01-01-2015".datum, sutDatumB -> "02-01-2015".datum), accumulator.derivations)
    val evaluatedResult: Boolean = result.toEval(resultContext).getOrElse(false)

  }

}
