package org.scalarules.dsl.core.projections

import org.scalarules.dsl.nl.grammar.{DslCondition, DslEvaluation}
import org.scalarules.engine._

/**
  * Domain objects in the DSL can allow access to their fields through projections.
  * This trait signals the support of field projections and provides a convenience
  * method to create these projections.
  *
  * Here's an example:
  *
  * {{{
  *   case class Person(val name: String)
  *
  *   class PersonFieldProjections(personFact: SingularFact[Person]) extends ProjectableFields[Person] {
  *     def outerFact: Fact[Person] = personFact
  *
  *     def name: DslEvaluation[String] = projectField( _.name )
  *   }
  *
  *   object PersonImplicits {
  *     implicit def toPersonFieldProjections(personFact: SingularFact[Person]): PersonFieldProjections = new PersonFieldProjections(personFact)
  *   }
  * }}}
  *
  * With these elements in place, you can import the PersonImplicits._ where you want to use it in your
  * DSL and you can refer to the `name` field of any `Fact` of type `Person`.
  *
  * @tparam C type from which the field(s) can be projected.
  * @author Jan-Hendrik Kuperus (jan-hendrik@scala-rules.org)
  */
trait ProjectableFields[C] {

  /**
    * Any implementing class should provide the fact from which to project the fields through this method.
    *
    * @return the Fact of type C from which fields will be projected.
    */
  protected def outerFact: Fact[C]

  /**
    * Provides a utility method to construct the DslEvaluation which entails the field projection.
    *
    * @param f the function which projects the Fact's value to the corresponding field of type F.
    * @tparam F type of the projected field, which will also be the type of the resulting DslEvaluation.
    * @return a DslEvaluation of the same type as the projected field. This evaluation will at runtime
    *         provide the value of the projected field of the accompanying Fact.
    */
  def projectField[F](f: C => F): DslEvaluation[F] = DslEvaluation(
    DslCondition.factFilledCondition(outerFact),
    new ProjectionEvaluation[C, F](new SingularFactEvaluation[C](outerFact), f)
  )

}

/**
  * Domain objects in the DSL can allow access to their fields through projections.
  * This trait signals the support of field projections and provides a convenience
  * method to create these projections. This trait is meant for Lists of objects to project
  * a List of traits back.
  *
  * Here's an example:
  *
  * {{{
  *   case class Person(val name: String)
  *
  *   class PersonFieldListProjections(personFact: ListFact[Person]) extends ProjectableListFields[Person] {
  *     def outerFact: Fact[Person] = personFact
  *
  *     def name: DslEvaluation[String] = projectField( _.name )
  *   }
  *
  *   object PersonImplicits {
  *     implicit def toPersonFieldListProjections(personFact: ListFact[Person]): PersonListFieldProjections = new PersonListFieldProjections(personFact)
  *   }
  * }}}
  *
  * With these elements in place, you can import the PersonImplicits._ where you want to use it in your
  * DSL and you can refer to the `name` field of any `Fact` of type `Person`.
  *
  * @tparam C type from which the field(s) can be projected.
  * @author Vincent Zorge (vincent@scala-rules.org)
  */
trait ProjectableListFields[C] {

  /**
    * Any implementing class should provide the fact from which to project the fields through this method.
    *
    * @return the Fact of type List[C] from which fields will be projected.
    */
  protected def outerFact: Fact[List[C]]

  /**
    * Provides a utility method to construct the DslEvaluation which entails the field projection.
    *
    * @param f the function which projects the Fact's value to the corresponding field of type F.
    * @tparam F type of the projected field, which will also be the type of the resulting DslEvaluation.
    * @return a DslEvaluation of the same type as the projected field. This evaluation will at runtime
    *         provide the value of the projected field of the accompanying Fact.
    */
  def projectField[F](f: C => F): DslEvaluation[List[F]] = DslEvaluation(
    DslCondition.factFilledCondition(outerFact),
    new ProjectionListEvaluation[C, F](outerFact.toEval, f)
  )

}
