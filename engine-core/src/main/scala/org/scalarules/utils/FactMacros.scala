package org.scalarules.utils

import org.scalarules.facts.{ListFact, SingularFact}

import scala.annotation.compileTimeOnly
import scala.language.experimental.macros
import scala.reflect.macros.blackbox._

/**
  * `FactMacros` contains two compile-time macros which allow the easy creation of a `Fact`, without having to explicitly repeat
  * the name of the `Fact` as an argument. This makes it easier to define Facts for use in a DSL, as the name of the val to
  * which it is assigned, becomes the name of the `Fact` itself.
  *
  * Without these macros, you would need to create `Fact`s as follows:
  *
  * `val MyFact = new SingularFact[Int]("MyFact")`
  *
  * This redundancy of the `Fact`'s name is not just a nuisance to type, it actually causes trouble when renaming the `Fact`,
  * or trying to copy its declaration. In both situations, you *must* also alter the name parameter manually. The compiler
  * and IDE tooling cannot help you track these errors down and so you are bound to find out about them at runtime, with
  * potentially uninformative error messages.
  *
  * If instead, you use these macros, these troubles will be taken away. Define your `Fact`s like this and have the IDE and
  * compiler warn you about duplicate names etc:
  *
  * `val MyFact = FactMacros.defineFact[Int]("Description")`
  *
  */
object FactMacros {

  // Macro definitions

  /**
    * Defines a `Fact`, using the name of the `val` it is assigned to as the name of the `Fact`. Note: the value of this
    * macro *must* be assigned to a `val`, otherwise a compiler error will be raised.
    *
    * @tparam A the value type of the resulting `Fact`.
    * @return a `SingularFact` initialized with the name of the val declaration.
    */
  def defineFact[A](): SingularFact[A] = macro FactMacros.defineFactMacroImpl[A]

  /**
    * Defines a `Fact`, using the name of the `val` it is assigned to as the name of the `Fact`. Note: the value of this
    * macro *must* be assigned to a `val`, otherwise a compiler error will be raised.
    *
    * @tparam A the value type of the resulting `Fact`.
    * @param description description of the `Fact`, to be passed along to the `Fact`'s constructor.
    * @return a `SingularFact` initialized with the name of the val declaration.
    */
  def defineFactWithDescription[A](description: String): SingularFact[A] = macro FactMacros.defineFactMacroWithDescriptionImpl[A]

  /**
    * Defines a `Fact`, using the name of the `val` it is assigned to as the name of the `Fact`. Note: the value of this
    * macro *must* be assigned to a `val`, otherwise a compiler error will be raised.
    *
    * @tparam A the value type of the resulting `Fact`.
    * @return a `ListFact` initialized with the name of the val declaration.
    */
  def defineListFact[A](): ListFact[A] = macro FactMacros.defineListFactMacroImpl[A]

  /**
    * Defines a `Fact`, using the name of the `val` it is assigned to as the name of the `Fact`. Note: the value of this
    * macro *must* be assigned to a `val`, otherwise a compiler error will be raised.
    *
    * @tparam A the value type of the resulting `Fact`.
    * @param description description of the `Fact`, to be passed along to the `Fact`'s constructor.
    * @return a `ListFact` initialized with the name of the val declaration.
    */
  def defineListFactWithDescription[A](description: String): ListFact[A] = macro FactMacros.defineListFactMacroWithDescriptionImpl[A]


  // Macro implementations

  @compileTimeOnly("This is a compile-time macro implementation, do not call it at runtime")
  def defineFactMacroImpl[A : c.WeakTypeTag](c: Context)(): c.Expr[SingularFact[A]] =
  {
    val factNameExpr = extractDeclaredValName(c)
    val valueType = extractValueType[A](c)
    c.universe.reify { new SingularFact[A](factNameExpr.splice, "", valueType.splice) }
  }

  @compileTimeOnly("This is a compile-time macro implementation, do not call it at runtime")
  def defineFactMacroWithDescriptionImpl[A : c.WeakTypeTag](c: Context)(description: c.Expr[String]): c.Expr[SingularFact[A]] =
  {
    val factNameExpr = extractDeclaredValName(c)
    val valueType = extractValueType[A](c)
    c.universe.reify { new SingularFact[A](factNameExpr.splice, description.splice, valueType.splice) }
  }

  @compileTimeOnly("This is a compile-time macro implementation, do not call it at runtime")
  def defineListFactMacroImpl[A : c.WeakTypeTag](c: Context)(): c.Expr[ListFact[A]] =
  {
    val factNameExpr = extractDeclaredValName(c)
    val valueType = extractValueType[A](c)
    c.universe.reify { new ListFact[A](factNameExpr.splice, "", valueType.splice) }
  }

  @compileTimeOnly("This is a compile-time macro implementation, do not call it at runtime")
  def defineListFactMacroWithDescriptionImpl[A : c.WeakTypeTag](c: Context)(description: c.Expr[String]): c.Expr[ListFact[A]] =
  {
    val factNameExpr = extractDeclaredValName(c)
    val valueType = extractValueType[A](c)
    c.universe.reify { new ListFact[A](factNameExpr.splice, description.splice, valueType.splice) }
  }

  /**
    * Helper function to extract the name of the declaring val from the macro `Context`.
    *
    * @param c `Context` provided by the compiler at the time of macro invocation.
    * @return an `Expr[String]` containing the name of the declaring val.
    */
  private def extractDeclaredValName(c: Context): c.Expr[String] = {
    import c.universe._ // scalastyle:ignore

    val enclosingOwner: _root_.scala.reflect.macros.blackbox.Context#Symbol = c.internal.enclosingOwner
    if (!enclosingOwner.isTerm || !enclosingOwner.asInstanceOf[TermSymbol].isVal) {
      c.abort(c.enclosingPosition, "Facts should be assigned directly to a val")
    }

    val fullName = enclosingOwner.name.decodedName.toString().trim()

    c.Expr[String](Literal(Constant(fullName)))
  }

  private def extractValueType[A : c.WeakTypeTag](c: Context): c.Expr[String] = {
    import c.universe._ // scalastyle:ignore

    c.Expr[String](Literal(Constant(c.weakTypeOf[A].toString)))
  }

}

