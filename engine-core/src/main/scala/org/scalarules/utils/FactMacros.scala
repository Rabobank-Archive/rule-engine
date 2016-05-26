package org.scalarules.utils

import org.scalarules.engine.{ListFact, SingularFact}

import scala.annotation.compileTimeOnly
import scala.language.experimental.macros
import scala.reflect.macros.blackbox._

object FactMacros {

  @compileTimeOnly("This is a compile-time macro implementation, do not call it at runtime")
  def defineFactMacroImpl[A : c.WeakTypeTag](c: Context)(omschrijving: c.Expr[String]): c.Expr[SingularFact[A]] =
  {
    import c.universe._ // scalastyle:ignore

    val enclosingOwner: _root_.scala.reflect.macros.blackbox.Context#Symbol = c.internal.enclosingOwner
    if (!enclosingOwner.isTerm || !enclosingOwner.asInstanceOf[TermSymbol].isVal) {
      c.abort(c.enclosingPosition, "Facts should be assigned directly to a val")
    }

    val factNameExpr = c.Expr[String](Literal(Constant(enclosingOwner.name.decodedName.toString().trim())))
    reify { new SingularFact[A](factNameExpr.splice, false, omschrijving.splice) }
  }

  @compileTimeOnly("This is a compile-time macro implementation, do not call it at runtime")
  def defineListFactMacroImpl[A : c.WeakTypeTag](c: Context)(omschrijving: c.Expr[String]): c.Expr[ListFact[A]] =
  {
    import c.universe._ // scalastyle:ignore

    val enclosingOwner: _root_.scala.reflect.macros.blackbox.Context#Symbol = c.internal.enclosingOwner
    if (!enclosingOwner.isTerm || !enclosingOwner.asInstanceOf[TermSymbol].isVal) {
      c.abort(c.enclosingPosition, "Facts should be assigned directly to a val")
    }

    val factNameExpr = c.Expr[String](Literal(Constant(enclosingOwner.name.decodedName.toString().trim())))
    reify { new ListFact[A](factNameExpr.splice, false, omschrijving.splice) }
  }

  def defineFact[A](omschrijving: String): SingularFact[A] = macro FactMacros.defineFactMacroImpl[A]
  def defineListFact[A](omschrijving: String): ListFact[A] = macro FactMacros.defineListFactMacroImpl[A]
}

