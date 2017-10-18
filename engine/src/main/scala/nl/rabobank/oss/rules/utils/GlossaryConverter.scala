package nl.rabobank.oss.rules.utils

import nl.rabobank.oss.rules.dsl.core.glossaries.Glossary
import nl.rabobank.oss.rules.facts.Fact

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
