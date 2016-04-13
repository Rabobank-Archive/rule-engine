package org.scalarules.engine

case class Step(initial: Context, derivation: Derivation, status: String, result: Context)

