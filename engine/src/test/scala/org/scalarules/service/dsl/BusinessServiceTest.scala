package org.scalarules.service.dsl

import org.scalarules.engine.{Context, FactEngine}
import org.scalarules.finance.nl._
import org.scalarules.service.dsl.BusinessServiceTestGlossary1._
import org.scalarules.service.dsl.BusinessServiceTestGlossary2._
import org.scalarules.service.dsl.BusinessServiceTestGlossary3._
import org.scalarules.service.dsl.NotABusinessServiceTestGlossary1._
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try


class BusinessServiceTest extends FlatSpec with Matchers {
  behavior of "provide insight into specifications"

  it should "provide insight into what berekeningen are part of the service" in {
    val businessService: BusinessService = new TestBusinessServiceAlles()

    businessService.berekeningen.map(_.getClass) should be (
      List(new BusinessServiceTestBerekening1, new BusinessServiceTestBerekening2, new BusinessServiceTestBerekening3).map(_.getClass))
  }

  it should "provide insight into what glossaries are part of the service" in {
    val businessService: BusinessService = new TestBusinessServiceAlles()

    businessService.glossaries should be (List(BusinessServiceTestGlossary1, BusinessServiceTestGlossary2, BusinessServiceTestGlossary3))
  }

  it should "provide insight into what is verplichte invoer" in {
    val businessService: BusinessService = new TestBusinessServiceAlles()

    businessService.verplichteInvoerFacts should be (List(verplichteInvoer1, verplichteInvoer2, verplichteInvoer3))
  }

  it should "provide insight into what is optionele invoer" in {
    val businessService: BusinessService = new TestBusinessServiceAlles()

    businessService.optioneleInvoerFactsList should be (List(optioneleInvoer1, optioneleInvoer2, optioneleInvoer3))
  }

  it should "provide insight into what is uitvoer" in {
    val businessService: BusinessService = new TestBusinessServiceAlles()

    businessService.uitvoerFacts should be (List(uitvoer1, uitvoer2, uitvoer3))
  }



  behavior of "throw no errors when used as intended"

  it should "perform succesful derivations when given proper input" in {
    val result: Try[Context] = new TestBusinessServiceAlles().run(Map(
      verplichteInvoer1 -> BigDecimal(1).euro,
      verplichteInvoer2 -> BigDecimal(2).euro,
      verplichteInvoer3 -> BigDecimal(3).euro
    ), FactEngine.runNormalDerivations)

    result.isSuccess
  }

  it should "perform the proper derivations when given proper input" in {
    val result: Context = new TestBusinessServiceAlles().run(Map(
      verplichteInvoer1 -> BigDecimal(1).euro,
      verplichteInvoer2 -> BigDecimal(2).euro,
      verplichteInvoer3 -> BigDecimal(3).euro
    ), FactEngine.runNormalDerivations).get

    result(uitvoer1) should be ((1 + 1).euro)
    result(uitvoer2) should be ((2 + 2).euro)
    result(uitvoer3) should be ((3 + 3).euro)
  }

  it should "overwrite optional invoer when given proper input" in {
    val result: Context = new TestBusinessServiceAlles().run(Map(
      verplichteInvoer1 -> 1.euro,
      verplichteInvoer2 -> 2.euro,
      verplichteInvoer3 -> 3.euro,
      optioneleInvoer1 -> 11.euro,
      optioneleInvoer2 -> 22.euro,
      optioneleInvoer3 -> 33.euro
    ), FactEngine.runNormalDerivations).get

    result(uitvoer1) should be ((1 + 11).euro)
    result(uitvoer2) should be ((2 + 22).euro)
    result(uitvoer3) should be ((3 + 33).euro)
  }

  it should "have no trouble with the absence of optionalInvoer specifications" in {
    val result: Context = new TestBusinessServiceGeenOptioneel().run(Map(
      verplichteInvoer1 -> 1.euro,
      verplichteInvoer2 -> 2.euro,
      verplichteInvoer3 -> 3.euro,
      //geen optionalInvoer specified, but the facts are still required by the underlying derivations!
      optioneleInvoer1 -> 11.euro,
      optioneleInvoer2 -> 22.euro,
      optioneleInvoer3 -> 33.euro
    ), FactEngine.runNormalDerivations).get

    result(uitvoer1) should be ((1 + 11).euro)
    result(uitvoer2) should be ((2 + 22).euro)
    result(uitvoer3) should be ((3 + 33).euro)
  }

  behavior of "throw errors when improperly specified"

  it should "fail with a clear message when no berekeningen are specified" in {
    val exception = intercept[IllegalStateException] {
      val result: Try[Context] = new TestBusinessServiceGeenBerekeningen().run(Map(
        verplichteInvoer1 -> 1.euro,
        verplichteInvoer2 -> 2.euro,
        verplichteInvoer3 -> 3.euro,
        optioneleInvoer1 -> 11.euro,
        optioneleInvoer2 -> 22.euro,
        optioneleInvoer3 -> 33.euro
      ), FactEngine.runNormalDerivations)

      result.get
    }
    exception.getMessage should be ("no Berekeningen specified!")
  }

  it should "fail with a clear message when any berekeningen are specified more than once" in {
    val exception = intercept[IllegalStateException] {
      val result: Try[Context] = new TestBusinessServiceRedundantlySpecifiedBerekeningen().run(Map(
        verplichteInvoer1 -> 1.euro,
        verplichteInvoer2 -> 2.euro,
        verplichteInvoer3 -> 3.euro,
        optioneleInvoer1 -> 11.euro,
        optioneleInvoer2 -> 22.euro,
        optioneleInvoer3 -> 33.euro
      ), FactEngine.runNormalDerivations)

      result.get
    }
    exception.getMessage should be ("class org.scalarules.service.dsl.BusinessServiceTestBerekening1 was specified more than once!")
  }

  it should "fail with a clear message when no Glossary provided" in {
    val exception = intercept[IllegalStateException] {
      val result: Try[Context] = new TestBusinessServiceGeenGlossaries().run(Map(
        verplichteInvoer1 -> 1.euro,
        verplichteInvoer2 -> 2.euro,
        verplichteInvoer3 -> 3.euro,
        notABusinessServiceVerplichtFact -> 6.euro,
        optioneleInvoer1 -> 11.euro,
        optioneleInvoer2 -> 22.euro,
        optioneleInvoer3 -> 33.euro
      ), FactEngine.runNormalDerivations)

      result.get
    }
    exception.getMessage.contains("no Glossaries specified!") should be (true)
  }

  it should "fail with a clear message when no verplichteInvoer specified" in {
    val exception = intercept[IllegalStateException] {
      val result: Try[Context] = new TestBusinessServiceGeenVerplichteInvoer().run(Map(
        optioneleInvoer1 -> 11.euro,
        optioneleInvoer2 -> 22.euro,
        optioneleInvoer3 -> 33.euro
      ), FactEngine.runNormalDerivations)

      result.get
    }
    exception.getMessage should be ("no verplichteInvoer specified, but this is mandatory!")
  }

  it should "fail with a clear message when no uitvoer specified" in {
    val exception = intercept[IllegalStateException] {
      val result: Try[Context] = new TestBusinessServiceGeenUitvoer().run(Map(
        verplichteInvoer1 -> 1.euro,
        verplichteInvoer2 -> 2.euro,
        verplichteInvoer3 -> 3.euro,
        optioneleInvoer1 -> 11.euro,
        optioneleInvoer2 -> 22.euro,
        optioneleInvoer3 -> 33.euro
      ), FactEngine.runNormalDerivations)

      result.get
    }
    exception.getMessage should be ("no uitvoerFacts specified, but this is mandatory!")
  }

  it should "fail with a clear message when verplichteInvoer is specified but not in provided Glossaries" in {
    val exception = intercept[IllegalStateException] {
      val result: Try[Context] = new TestBusinessServiceIllegaalFactVerplicht().run(Map(
        verplichteInvoer1 -> 1.euro,
        verplichteInvoer2 -> 2.euro,
        verplichteInvoer3 -> 3.euro,
        notABusinessServiceVerplichtFact -> 6.euro,
        optioneleInvoer1 -> 11.euro,
        optioneleInvoer2 -> 22.euro,
        optioneleInvoer3 -> 33.euro
      ), FactEngine.runNormalDerivations)

      result.get
    }
    exception.getMessage should be ("notABusinessServiceVerplichtFact specified, but not found in specified glossaries so not in scope!")
  }

  it should "fail with a clear message when optioneleInvoer is specified but not in provided Glossaries" in {
    val exception = intercept[IllegalStateException] {
      val result: Try[Context] = new TestBusinessServiceIllegaalFactOptioneel().run(Map(
        verplichteInvoer1 -> 1.euro,
        verplichteInvoer2 -> 2.euro,
        verplichteInvoer3 -> 3.euro,
        notABusinessServiceOptioneelFact -> 6.euro,
        optioneleInvoer1 -> 11.euro,
        optioneleInvoer2 -> 22.euro,
        optioneleInvoer3 -> 33.euro
      ), FactEngine.runNormalDerivations)

      result.get
    }
    exception.getMessage should be ("notABusinessServiceOptioneelFact specified, but not found in specified glossaries so not in scope!")
  }

it should "fail with a clear message when uitvoer is specified but not in provided Glossaries" in {
    val exception = intercept[IllegalStateException] {
      val result: Try[Context] = new TestBusinessServiceIllegaalFactUitvoer().run(Map(
        verplichteInvoer1 -> 1.euro,
        verplichteInvoer2 -> 2.euro,
        verplichteInvoer3 -> 3.euro,
        optioneleInvoer1 -> 11.euro,
        optioneleInvoer2 -> 22.euro,
        optioneleInvoer3 -> 33.euro
      ), FactEngine.runNormalDerivations)

      result.get
    }
    exception.getMessage should be ("notABusinessServiceUitvoer specified, but not found in specified glossaries so not in scope!")
  }

it should "fail with a clear message when a fact is is specified as verplichteInvoer en optioneleInvoer" in {
    val exception = intercept[IllegalStateException] {
      val result: Try[Context] = new TestBusinessServiceFactAsBothVerplichtAndOptioneel().run(Map(
        verplichteInvoer1 -> 1.euro,
        verplichteInvoer2 -> 2.euro,
        verplichteInvoer3 -> 3.euro,
        optioneleInvoer1 -> 11.euro,
        optioneleInvoer2 -> 22.euro,
        optioneleInvoer3 -> 33.euro
      ), FactEngine.runNormalDerivations)

      result.get
    }
    exception.getMessage should be ("verplichteInvoer1 specified as both verplichteInvoer and optioneleInvoer")
  }

}
