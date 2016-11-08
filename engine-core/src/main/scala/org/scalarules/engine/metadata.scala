package org.scalarules.engine

import org.scalarules.derivations.Derivation

case class Step(initial: Context, derivation: Derivation, status: String, result: Context)

