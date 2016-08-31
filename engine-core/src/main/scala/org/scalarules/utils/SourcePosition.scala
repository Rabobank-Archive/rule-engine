package org.scalarules.utils


sealed trait SourcePosition {
  val offset: Int
}

case class SourceUnknown() extends SourcePosition {
  override val offset: Int = 0
}
case class FileSourcePosition(fileName: String, line: Int, column: Int, offset: Int, length: Int) extends SourcePosition
