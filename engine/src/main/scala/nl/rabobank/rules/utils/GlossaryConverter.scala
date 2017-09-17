package nl.rabobank.rules.utils

import nl.rabobank.rules.dsl.core.glossaries.Glossary
import nl.rabobank.rules.facts.Fact

import scala.reflect.ClassTag

object GlossaryConverter {

  def toJson(g: Glossary): String = {
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
