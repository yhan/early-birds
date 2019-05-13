package raw

import model.Rating
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class ReaderSpec extends FlatSpec with Matchers {
  "The Reader object" should "read a file" in {
    val ratings = Reader.read(Source.fromResource("2lines.csv"))
    ratings.toSeq shouldEqual Seq(
      Rating("5e432220-8991-11e6-ac16-6945cf2d3541", "p_500315456", 1f, 1476686549844L),
      Rating("ddfd8b10-9434-11e6-b081-b792acbf679e", "p_504922691", 1f, 1476686550002L)
    )
  }
}
