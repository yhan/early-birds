package raw

import java.io.StringWriter

import org.scalatest.{FlatSpec, Matchers}

class WriterSpec extends FlatSpec with Matchers {
  "The Writer object" should "write users" in {
    val users = List("user1" -> 0, "user2" -> 1)
    val sw    = new StringWriter
    Writer.writeUsers(users, sw)
    sw.getBuffer.toString shouldEqual "user1,0\nuser2,1\n"
  }

  it should "write items" in {
    val items = List("item1" -> 0, "item2" -> 1)
    val sw    = new StringWriter
    Writer.writeItems(items, sw)
    sw.getBuffer.toString shouldEqual "item1,0\nitem2,1\n"
  }

  it should "write agg ratings" in {
    val aggRatings = List((0, 1) -> 0.2f, (3, 4) -> 0.5f)
    val sw         = new StringWriter
    Writer.writeAggRatings(aggRatings, sw)
    sw.getBuffer.toString shouldEqual "0,1,0.2\n3,4,0.5\n"
  }

}
