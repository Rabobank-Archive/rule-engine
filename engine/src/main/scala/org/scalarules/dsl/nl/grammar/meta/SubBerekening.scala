package org.scalarules.dsl.nl.grammar.meta

import scala.annotation.compileTimeOnly
import scala.meta._

//scalastyle:off

/**
  * Generates a companion object for the annotated subclass of LijstBerekening and makes it implement BerekeningReference.
  */
@compileTimeOnly("@MacroSubBerekening not expanded")
class SubBerekening extends scala.annotation.StaticAnnotation {
  def meta[T](thunk: => T): T = thunk

  inline def apply(defn: Any): Any = meta {
    defn match {
      case pat @ q"..$modsA class $tname[..$tparams] ..$modsB (...$paramss) extends $template" =>

        // Deconstruct the body to access the constructor calls, specifically the call to LijstBerekening
        var template"{ ..$earlyStats } with ..$ctorcalls { $param => ..$stats }" = template
        val lijstBerekeningTypeParametersOption = SubBerekening.extractLijstBerekeningTParams(ctorcalls)

        if (lijstBerekeningTypeParametersOption.isDefined) {
          val (in, out) = lijstBerekeningTypeParametersOption.get
          val annotatedTypeName = tname.value

          val companionDefinition = s"""
            object $annotatedTypeName extends FlowBerekeningReference[$in, $out] {
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
        println("Warning: @SubBerekening annotation was not added to a class definition. It is being ignored.")
        x
    }
  }
}

object SubBerekening {
  /**
    * Scans a list of constructor call ASTs to find the one associated with the FlowBerekening class and extracts its type parameters.
    */
  def extractLijstBerekeningTParams(ctorcalls: Seq[Ctor.Call]): Option[(String, String)] = {
    ctorcalls.collect {
      case q"$expr(..$aexprssnel)" => {
        println(s"Matched constructor application for ${expr.syntax}")
        expr match {
          case ctor"$ctorref[..$atpesnel]" => (ctorref.syntax, atpesnel.head.syntax, atpesnel.last.syntax)
          case ctor"${ctorname: Ctor.Name}" => {
            // Crap, we couldn't match against explicitly defined type parameters, we'll have to deduce them from the first two expressions
//            val targ"${inputType: Type}" = aexprssnel.head
//            val targ"${outputType: Type}" = aexprssnel.tail.head
//            println(s"Input: ${inputType.syntax}, output: ${outputType.syntax}")

            (ctorname.syntax, "Any", "Any")
          } // This one is probably not our constructor, but if so, we'll filter it out later
        }
      }
    }
    .find( x => x._1 == "FlowBerekening" )
    .map( x => (x._2, x._3) )
  }
}
