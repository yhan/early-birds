package raw

object Writer {
  def writeUsers(users: List[(String, Int)], writer: java.io.Writer): Unit =
    write(writer, users) {
      case (userId, userIdAsInteger) => s"$userId,$userIdAsInteger\n"
    }

  def writeItems(items: List[(String, Int)], writer: java.io.Writer): Unit =
    write(writer, items) {
      case (itemId, itemIdAsInteger) => s"$itemId,$itemIdAsInteger\n"
    }

  def writeAggRatings(aggRatings: List[((Int, Int), Float)], writer: java.io.Writer): Unit =
    write(writer, aggRatings) {
      case ((userIdAsInteger, itemIdAsInteger), rating) =>
        s"$userIdAsInteger,$itemIdAsInteger,$rating\n"
    }

  private def write[A](writer: java.io.Writer, l: List[A])(serializer: A => String): Unit = {
    l.foreach { a =>
      writer.write(serializer(a))
    }
    writer.close()
  }
}
