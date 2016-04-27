package org.scalarules.dsl.nl.grammar.date

import org.scalarules.dsl.nl.finance.{Bedrag, Percentage}
import org.scalarules.engine.{Context, FactEngine, SingularFact}
import org.scalatest.{FlatSpec, Matchers}
import org.scalarules.dsl.nl.grammar._

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
