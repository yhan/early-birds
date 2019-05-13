package spark

import model.{AggRating, Item, Rating, User}
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions.{datediff, from_unixtime, lit, max, pow, sum}

object Calculator {
  def compute(ratings: Dataset[Rating]): (Dataset[User], Dataset[Item], Dataset[AggRating]) = {
    import ratings.sparkSession.implicits._

    val usersDs = users(ratings).cache()
    val itemsDs = items(ratings).cache()

    val aggRatingsDs = ratings
      .join(usersDs, "userId")
      .join(itemsDs, "itemId")
      .withColumn(
        "datediff",
        datediff(
          from_unixtime(lit(maxTimestamp(ratings)) / 1000),
          from_unixtime($"timestamp" / 1000)
        )
      )
      .withColumn("adjustedRating", $"rating" * pow(lit(0.95), $"datediff"))
      .where($"adjustedRating" > 0.01)
      .groupBy("userIdAsInteger", "itemIdAsInteger")
      .agg(sum("adjustedRating") as "adjustedRating")
      .as[AggRating]

    (usersDs, itemsDs, aggRatingsDs)
  }

  private def maxTimestamp(ratings: Dataset[Rating]): Long =
    ratings
      .agg(max("timestamp"))
      .head()
      .getLong(0)

  private def users(ratings: Dataset[Rating]): Dataset[User] = {
    import ratings.sparkSession.implicits._
    ratings
      .select("userId")
      .distinct()
      .rdd
      .zipWithIndex()
      .map { case (row, index) => (row.getString(0), index) }
      .toDF("userId", "userIdAsInteger")
      .as[User]
  }

  private def items(ratings: Dataset[Rating]): Dataset[Item] = {
    import ratings.sparkSession.implicits._
    ratings
      .select("itemId")
      .distinct()
      .rdd
      .zipWithIndex()
      .map { case (row, index) => (row.getString(0), index) }
      .toDF("itemId", "itemIdAsInteger")
      .as[Item]
  }

}
