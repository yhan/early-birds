package raw

import model.Rating

import scala.io.BufferedSource

object Reader {
  def read(source: BufferedSource): Iterator[Rating] =
    source.getLines().map(parseLine)

  private def parseLine(line: String): Rating = {
    val Array(userId, itemId, rating, timestamp) = line.split(",")
    Rating(userId, itemId, rating.toFloat, timestamp.toLong)
  }
}
