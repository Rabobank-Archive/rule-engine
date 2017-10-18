import nl.rabobank.oss.rules.dsl.core.glossaries.Glossary

val universe = scala.reflect.runtime.universe

import universe._

import scala.language.experimental.macros

//val tree = q" class Test(val outer:String) { def doIt() = println(outer) } "


//val rawCrap = showRaw(q" class Test(val outer:String) { def doIt() = println(outer) } ")

object G extends Glossary {

  val Bla = defineFact[String]
  val Blaaat = defineListFact[String]

}

val bereken: Any = showRaw(q"Gegeven(altijd).Bereken(Bla)")


val b2 = showRaw(q"Gegeven(altijd).Bereken(Blaaat)")


/*



ClassDef(
  Modifiers(),
  TypeName("Test"),
  List(),
  Template(
    List(
      Select(
        Ident(scala),
        TypeName("AnyRef")
      )
    ),
    noSelfType,
    List(
      ValDef(
        Modifiers(PARAMACCESSOR),
        TermName("outer"),
        Ident(TypeName("String")),
        EmptyTree
      ),
      DefDef(
        Modifiers(),
        termNames.CONSTRUCTOR,
        List(),
        List(
          List(
            ValDef(Modifiers(PARAM | PARAMACCESSOR),
            TermName("outer"),
            Ident(TypeName("String")),
            EmptyTree
          )
        )
      ),
      TypeTree(),
      Block(
        List(pendingSuperCall),
        Literal(Constant(()))
      )
    ),
    DefDef(
      Modifiers(),
      TermName("doIt"),
      List(),
      List(
        List()
      ),
      TypeTree(),
      Apply(
        Ident(TermName("println")),
        List(
          Ident(TermName("outer"))
        )
      )
    )
  )
)




)




 */
