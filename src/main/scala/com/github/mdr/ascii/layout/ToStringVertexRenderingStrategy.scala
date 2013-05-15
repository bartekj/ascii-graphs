package com.github.mdr.ascii.layout

import com.github.mdr.ascii.parser.Dimension

object ToStringVertexRenderingStrategy extends VertexRenderingStrategy[Any] {

  def getPreferredSize(v: Any): Dimension = {
    val lines = splitLines(v.toString)
    Dimension(lines.size, if (lines.isEmpty) 0 else lines.map(_.size).max)
  }

  def getText(v: Any, allocatedSize: Dimension): List[String] = {
    val unpaddedLines =
      splitLines(v.toString).take(allocatedSize.height).map { line ⇒
        val discrepancy = allocatedSize.width - line.size
        val padding = " " * (discrepancy / 2)
        padding + line
      }
    val verticalDiscrepancy = Math.max(0, allocatedSize.height - unpaddedLines.size)
    val verticalPadding = List.fill(verticalDiscrepancy / 2)("")
    verticalPadding ++ unpaddedLines ++ verticalPadding
  }

  private def splitLines(s: String): List[String] =
    s.split("(\r)?\n").toList match {
      case Nil | List("") ⇒ Nil
      case xs             ⇒ xs
    }

}