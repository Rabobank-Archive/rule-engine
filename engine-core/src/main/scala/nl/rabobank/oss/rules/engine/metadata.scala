package nl.rabobank.oss.rules.engine

import nl.rabobank.oss.rules.derivations.Derivation

trait Step {
  val initialContext: Context
  val derivation: Derivation
  val resultContext: Context
}

trait NoChangesStep extends Step {
  override lazy val resultContext: Context = initialContext
}

case class AlreadyExistsStep(initialContext: Context, derivation: Derivation) extends NoChangesStep
case class ConditionFalseStep(initialContext: Context, derivation: Derivation) extends NoChangesStep
case class EmptyResultStep(initialContext: Context, derivation: Derivation) extends NoChangesStep

trait ChangesStep extends Step

case class EvaluatedStep(initialContext: Context, derivation: Derivation, resultContext: Context) extends ChangesStep
case class IterationFinishedStep(initialContext: Context, derivation: Derivation, resultContext: Context) extends ChangesStep
case class IterationStartedStep(initialContext: Context, derivation: Derivation, resultContext: Context) extends ChangesStep
