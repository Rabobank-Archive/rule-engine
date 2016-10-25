package org.scalarules.dsl.nl.grammar.macros

import org.scalarules.dsl.nl.grammar.{DslCondition, GegevenWord, ListBerekenStart, SingularBerekenStart}
import org.scalarules.engine.{Derivation, ListFact, SingularFact}
import org.scalarules.utils.FileSourcePosition

import scala.annotation.compileTimeOnly
import scala.language.experimental.macros
import scala.reflect.internal.util.SourceFile
import scala.reflect.macros.blackbox._

object DslMacros {

  @compileTimeOnly("This macro stores the source position of a 'Gegeven' during compile time, no use during runtime")
  def captureGegevenSourcePositionMacroImpl(c: Context)(condition: c.Expr[DslCondition]): c.Expr[GegevenWord] = {
    val (filename, line, column, start, length) = extractSourcePosition(c)

    c.universe.reify { new GegevenWord(condition.splice, FileSourcePosition(filename.splice, line.splice, column.splice, start.splice, length.splice)) }
  }

  @compileTimeOnly("This macro stores the source position of a 'Bereken' during compile time, no use during runtime")
  def captureSingularBerekenSourcePositionMacroImpl[A : c.WeakTypeTag](c: Context)(fact: c.Expr[SingularFact[A]]): c.Expr[SingularBerekenStart[A]] = {
    import c.universe._

    val (filename, line, column, start, length) = extractSourcePosition(c)

    val conditionExpr: c.Expr[DslCondition] = c.Expr[DslCondition](Select(c.prefix.tree, TermName("condition")))

    c.universe.reify { new SingularBerekenStart[A](conditionExpr.splice, fact.splice, List(), FileSourcePosition(filename.splice, line.splice, column.splice, start.splice, length.splice)) }
  }

  @compileTimeOnly("This macro stores the source position of a 'Bereken' during compile time, no use during runtime")
  def captureSingularBerekenSourcePositionWithAccumulatorMacroImpl[A : c.WeakTypeTag](c: Context)(fact: c.Expr[SingularFact[A]]): c.Expr[SingularBerekenStart[A]] = {
    import c.universe._

    val (filename, line, column, start, length) = extractSourcePosition(c)

    val conditionExpr: c.Expr[DslCondition] = c.Expr[DslCondition](Select(c.prefix.tree, TermName("condition")))
    val derivationsExpr: c.Expr[List[Derivation]] = c.Expr[List[Derivation]](Select(c.prefix.tree, TermName("derivations")))

    c.universe.reify { new SingularBerekenStart[A](conditionExpr.splice, fact.splice, derivationsExpr.splice, FileSourcePosition(filename.splice, line.splice, column.splice, start.splice, length.splice)) }
  }

  @compileTimeOnly("This macro stores the source position of a 'Bereken' during compile time, no use during runtime")
  def captureListBerekenSourcePositionMacroImpl[A : c.WeakTypeTag](c: Context)(fact: c.Expr[ListFact[A]]): c.Expr[ListBerekenStart[A]] = {
    import c.universe._

    val (filename, line, column, start, length) = extractSourcePosition(c)

    val conditionExpr: c.Expr[DslCondition] = c.Expr[DslCondition](Select(c.prefix.tree, TermName("condition")))

    c.universe.reify { new ListBerekenStart[A](conditionExpr.splice, fact.splice, List(), FileSourcePosition(filename.splice, line.splice, column.splice, start.splice, length.splice)) }
  }

  @compileTimeOnly("This macro stores the source position of a 'Bereken' during compile time, no use during runtime")
  def captureListBerekenSourcePositionWithAccumulatorMacroImpl[A : c.WeakTypeTag](c: Context)(fact: c.Expr[ListFact[A]]): c.Expr[ListBerekenStart[A]] = {
    import c.universe._

    val (filename, line, column, start, length) = extractSourcePosition(c)

    val conditionExpr: c.Expr[DslCondition] = c.Expr[DslCondition](Select(c.prefix.tree, TermName("condition")))
    val derivationsExpr: c.Expr[List[Derivation]] = c.Expr[List[Derivation]](Select(c.prefix.tree, TermName("derivations")))

    c.universe.reify { new ListBerekenStart[A](conditionExpr.splice, fact.splice, derivationsExpr.splice, FileSourcePosition(filename.splice, line.splice, column.splice, start.splice, length.splice)) }
  }

  def extractSourcePosition(c: Context): (c.Expr[String], c.Expr[Int], c.Expr[Int], c.Expr[Int], c.Expr[Int]) = {
    import c.universe._ // scalastyle:ignore

    val Apply(methodName, _) = c.macroApplication
    val position: c.Position = methodName.pos
    val source: SourceFile = position.source

    val line = c.Expr(Literal(Constant(position.line)))
    val column = c.Expr(Literal(Constant(position.column)))
    val start = c.Expr(Literal(Constant(position.focus.start)))
    val length = c.Expr(Literal(Constant(methodName.symbol.name.toString().length)))
    val filename = c.Expr(Literal(Constant(source.file.name)))

    (filename, line, column, start, length)
  }
}
