package org.scalarules.dsl.nl.grammar.macros

import org.scalarules.dsl.core.utils.SourcePosition
import org.scalarules.dsl.nl.grammar.{DslCondition, GegevenWord}

import scala.annotation.compileTimeOnly
import scala.language.experimental.macros
import scala.reflect.internal.util.SourceFile
import scala.reflect.macros.blackbox._

object DslMacros {

  @compileTimeOnly("This macro stores the source position of a 'Gegeven' during compile time, no use during runtime")
  def captureGegevenSourcePositionMacroImpl(c: Context)(condition: c.Expr[DslCondition]): c.Expr[GegevenWord] = {
    import c.universe._ // scalastyle:ignore

    val Apply(methodName, _) = c.macroApplication
    val position: c.Position = methodName.pos
    val source: SourceFile = position.source

    val line = c.Expr(Literal(Constant(position.line)))
    val column = c.Expr(Literal(Constant(position.column)))
    val start = c.Expr(Literal(Constant(position.start)))
    val length = c.Expr(Literal(Constant(methodName.symbol.name.toString().length)))
    val filename = c.Expr(Literal(Constant(source.file.name)))
    //val rebuilt = (position.start until length).map( idx => source.content(idx) ).mkString("")

//    c.abort(c.enclosingPosition, s"Found Gegeven, pos: ${line}:${column}, start: ${start}, stop: ${position.end}, chars: '${rebuilt}'")

    c.universe.reify { new GegevenWord(condition.splice, SourcePosition(filename.splice, line.splice, column.splice, start.splice, length.splice)) }
  }

}
