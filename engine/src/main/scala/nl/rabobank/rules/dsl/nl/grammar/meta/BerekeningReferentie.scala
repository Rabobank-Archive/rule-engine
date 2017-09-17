package nl.rabobank.rules.dsl.nl.grammar.meta

import scala.annotation.compileTimeOnly
import scala.meta._

//scalastyle:off

/**
  * Generates a companion object for the annotated subclass of LijstBerekening and makes it implement BerekeningReference.
  */
@compileTimeOnly("@MacroElementBerekening not expanded")
class BerekeningReferentie extends scala.annotation.StaticAnnotation {
  def meta[T](thunk: => T): T = thunk

  inline def apply(defn: Any): Any = meta {
    defn match {
      case pat @ q"..$modsA class $tname[..$tparams] ..$modsB (...$paramss) extends $template" =>

        // Deconstruct the body to access the constructor calls, specifically the call to LijstBerekening
        var template"{ ..$earlyStats } with ..$ctorcalls { $param => ..$stats }" = template
        val lijstBerekeningTypeParametersOption = BerekeningReferentie.extractLijstBerekeningTParams(ctorcalls)

        if (lijstBerekeningTypeParametersOption.isDefined) {
          val (in, out) = lijstBerekeningTypeParametersOption.get
          val annotatedTypeName = tname.value

          val companionDefinition = s"""
            object $annotatedTypeName extends ElementBerekeningReference[$in, $out] {
              def berekening: $annotatedTypeName = new $annotatedTypeName
            }
          """.parse[Stat].get

          q"""
             $pat

             $companionDefinition
          """
        }
        else {
          println(s"Warning: Failed to extract type parameters from LijstBerekening-initializer. This means there is no accompanying BerekeningReference for $tname.")
          pat
        }
      case x =>
        println("Warning: @ElementBerekening annotation was not added to a class definition. It is being ignored.")
        x
    }
  }
}

object BerekeningReferentie {
  /**
    * Scans a list of constructor call ASTs to find the one associated with the ElementBerekening class and extracts its type parameters.
    */
  def extractLijstBerekeningTParams(ctorcalls: Seq[Ctor.Call]): Option[(String, String)] = {
    ctorcalls.collect {
      case q"$expr(..$aexprssnel)" => {
        println(s"Matched constructor application for ${expr.syntax}")
        expr match {
          case ctor"$ctorref[..$atpesnel]" => (ctorref.syntax, atpesnel.head.syntax, atpesnel.last.syntax)
          case ctor"${ctorname: Ctor.Name}" => {
            // Crap, we couldn't match against explicitly defined type parameters, we'll have to deduce them from the first two expressions.
            // The required functionality is on the roadmap of Scala Meta, so perhaps with some new release in the future.

            (ctorname.syntax, "Any", "Any")
          }
        }
      }
    }
    .find( x => x._1 == "ElementBerekening" )
    .map( x => (x._2, x._3) )
  }
}
