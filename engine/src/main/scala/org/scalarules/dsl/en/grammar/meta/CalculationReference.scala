package org.scalarules.dsl.en.grammar.meta

import scala.annotation.compileTimeOnly
import scala.meta._

//scalastyle:off

/**
  * Generates a companion object for the annotated subclass of LijstBerekening and makes it implement BerekeningReference.
  */
@compileTimeOnly("@CalculationReference not expanded")
class CalculationReference extends scala.annotation.StaticAnnotation {
  def meta[T](thunk: => T): T = thunk

  inline def apply(defn: Any): Any = meta {
    defn match {
      case pat @ q"..$modsA class $tname[..$tparams] ..$modsB (...$paramss) extends $template" =>

        // Deconstruct the body to access the constructor calls, specifically the call to LijstBerekening
        var template"{ ..$earlyStats } with ..$ctorcalls { $param => ..$stats }" = template
        val lijstBerekeningTypeParametersOption = CalculationReference.extractElementCalculationTParams(ctorcalls)

        if (lijstBerekeningTypeParametersOption.isDefined) {
          val (in, out) = lijstBerekeningTypeParametersOption.get
          val annotatedTypeName = tname.value

          val companionDefinition = s"""
            object $annotatedTypeName extends ElementCalculationReference[$in, $out] {
              def calculation: $annotatedTypeName = new $annotatedTypeName
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

object CalculationReference {
  /**
    * Scans a list of constructor call ASTs to find the one associated with the ElementCalculation class and extracts its type parameters.
    */
  def extractElementCalculationTParams(ctorcalls: Seq[Ctor.Call]): Option[(String, String)] = {
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
    .find( x => x._1 == "ElementCalculation" )
    .map( x => (x._2, x._3) )
  }
}
