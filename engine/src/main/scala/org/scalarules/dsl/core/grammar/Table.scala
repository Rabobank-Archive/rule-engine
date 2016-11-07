package org.scalarules.dsl.core.grammar

trait Table[O, X, Y] {
  def get(x: X, y: Y): O
}
