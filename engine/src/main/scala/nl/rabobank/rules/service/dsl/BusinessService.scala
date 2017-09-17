package nl.rabobank.rules.service.dsl

import nl.rabobank.rules.derivations.Derivation
import nl.rabobank.rules.dsl.core.glossaries.Glossary
import nl.rabobank.rules.dsl.nl.grammar.Berekening
import nl.rabobank.rules.facts.Fact
import nl.rabobank.rules.engine.{Context, Step}

import scala.util.{Failure, Success, Try}

/**
  * Een BusinessService stelt de gebruiker in staat om te specificeren (en te ontsluiten):
  * - dat bepaalde berekeningen bij elkaar gegroepeerd zijn (dit wordt afgedwongen),
  * - dat sommige invoerfeiten verplicht zijn om te vullen (dit wordt afgedwongen),
  * - welke defaults er gelden voor niet-verplichte invoer,
  * - welke feiten specifiek dienen als uitvoer: de "resultaten" van de berekening,
  * - welke feiten Uberhaupt beschikbaar worden gesteld aan de buitenwereld (glossaries).
  *
  * Iedere BusinessService heeft een run-methode die afdwingt dat er eerst gevalideerd wordt of alle verplichte invoer aanwezig is.
  */
case class BusinessService(berekeningen: List[Berekening],
                      glossaries: List[Glossary],
                      verplichteInvoerFacts: List[Fact[Any]],
                      optioneleInvoerFacts: Map[Fact[Any], Any],
                      uitvoerFacts: List[Fact[Any]]) {

  type ErrorMessage = String

  val optioneleInvoerFactsList: List[Fact[Any]] = optioneleInvoerFacts.keys.toList

  def validate(inputContext: Context): List[ErrorMessage] =
    validateSpecifications ::: validateInvoer(inputContext)

  def validateSpecifications: List[ErrorMessage] =
    validateBerekeningen ::: validateGlossaries ::: validateAllFactsInGlossary ::: validateNoFactsBothOptionalAndMandatoryInvoer ::: validateUitvoer

  def validateBerekeningen: List[ErrorMessage] = berekeningen match {
    case Nil => List("no Berekeningen specified!")
    case list: List[Berekening] => validateNoRedundancies(list)
  }

  private def validateNoRedundancies(list: List[Any]) =
    list.map(_.getClass)
      .groupBy(identity)
      .collect{
        case (berekening, occurrences) if occurrences.length > 1 => berekening.toString + " was specified more than once!"
      }.toList

  def validateGlossaries() : List[ErrorMessage] = glossaries match {
    case Nil => List("no Glossaries specified!")
    case list: List[Glossary] => validateNoRedundancies(list)
  }

  def validateAllFactsInGlossary() : List[ErrorMessage] = {
    val factsInScope: List[Fact[Any]] = glossaries.foldLeft(List():List[Fact[Any]])((a: List[Fact[Any]], b: Glossary) => a ++ b.facts.values)
    val definedBusinessServiceFacts: List[Fact[Any]] = optioneleInvoerFactsList ::: verplichteInvoerFacts ::: uitvoerFacts

    definedBusinessServiceFacts.filterNot(factsInScope.contains(_)).map(
      notFound => s"$notFound specified, but not found in specified glossaries so not in scope!")
  }

  def validateNoFactsBothOptionalAndMandatoryInvoer(): List[ErrorMessage] =
    verplichteInvoerFacts.intersect(optioneleInvoerFactsList).map(
      doubleFact => s"$doubleFact specified as both verplichteInvoer and optioneleInvoer")

  def validateInvoer(inputContext: Context): List[ErrorMessage] = verplichteInvoerFacts match {
    case Nil => List("no verplichteInvoer specified, but this is mandatory!")
    case list: List[Fact[Any]] => list.collect { case fact: Fact[Any] if !inputContext.contains(fact) => s"$fact not provided in context, but mandatory!"}
  }

  def validateUitvoer(): List[ErrorMessage] = uitvoerFacts match {
    case Nil => List("no uitvoerFacts specified, but this is mandatory!")
    case _ => Nil
  }

  def run(inputContext: Context, runFunction: (Context, List[Derivation]) => Context): Try[Context] =
    validate(inputContext) match {
      case Nil => Success(runFunction(optioneleInvoerFacts ++ inputContext, berekeningen.flatMap(_.berekeningen)))
      case list: List[ErrorMessage] => Failure(
        new IllegalStateException(list.reduceLeft((a: ErrorMessage, b: ErrorMessage) => a + " / " + b))
      )
    }

  def runDebug(inputContext: Context, runFunction: (Context, List[Derivation]) => (Context, List[Step])): Try[(Context, List[Step])] =
    validate(inputContext) match {
      case Nil => Success(runFunction(optioneleInvoerFacts ++ inputContext, berekeningen.flatMap(_.berekeningen)))
      case list: List[ErrorMessage] => Failure(
        new IllegalStateException(list.reduceLeft((a: ErrorMessage, b: ErrorMessage) => a + " / " + b))
      )
    }
}

object BusinessServiceHelper {
  val Geen = Map.empty[Fact[Any], String]
}
