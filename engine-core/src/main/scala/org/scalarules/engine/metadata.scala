package org.scalarules.engine

import org.scalarules.derivations.Derivation

trait Step

case class AlreadyExistsStep(initial: Context, derivation: Derivation, result: Context) extends Step
case class ConditionFalseStep(initial: Context, derivation: Derivation, result: Context) extends Step
case class EmptyResultStep(initial: Context, derivation: Derivation, result: Context) extends Step
case class EvaluatedStep(initial: Context, derivation: Derivation, result: Context) extends Step
case class IterationFinishedStep(initial: Context, derivation: Derivation, result: Context) extends Step
case class IterationStartedStep(initial: Context, derivation: Derivation, result: Context) extends Step

