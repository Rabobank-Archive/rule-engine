package org.scalarules.utils

import org.scalarules.engine.Fact

import scala.reflect.ClassTag

object GlossaryConverter {

  @deprecated(message = "Glossary has been replaced with MacroGlossary", since = "0.3.0")
  def toJson(g: Glossary): String = {
    g.getFacts.map( factToJson ).mkString("{", ", ", "}")
  }

  def toJson(g: MacroGlossary): String = {
    g.facts.map( factToJson ).mkString("{", ", ", "}")
  }

  private def factToJson[A : ClassTag](fe: (String, Fact[A])): String = {
    val (name, fact) = fe
    val classTag = implicitly[ClassTag[A]]
    s""" "${name}": {
        |  "name": "${name}",
        |  "description": "${fact.description}",
        |}
       """.stripMargin
  }

}
