package org.scalarules.dsl.core.operators

trait BinaryOperable[A, B, C] {
  def operation(a: A, b: B): C

  def identityLeft: A
  def identityRight: B

  def representation: String
}
