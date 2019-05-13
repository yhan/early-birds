package raw

import java.util.concurrent.TimeUnit

import model.{Rating, Result}

import scala.collection.mutable

object Calculator {
  def maxTimestamp(ratings: Iterator[Rating]): Long =
    ratings.maxBy(_.timestamp).timestamp

  def compute(ratings: Iterator[Rating], maxTimestamp: Long): Result = {
    var userCounter = 0
    var itemCounter = 0
    val users       = mutable.Map.empty[String, Int]
    val items       = mutable.Map.empty[String, Int]
    val aggRatings  = mutable.Map.empty[(Int, Int), Float]

    ratings.foreach { rating =>
      val userIdAsInteger = users.getOrElseUpdate(rating.userId, {
        userCounter += 1; userCounter - 1
      })

      val itemIdAsInteger = items.getOrElseUpdate(rating.itemId, {
        itemCounter += 1; itemCounter - 1
      })

      val currentRating: Float =
        aggRatings.getOrElse((userIdAsInteger, itemIdAsInteger), 0)

      val diffInDays        = TimeUnit.MILLISECONDS.toDays(maxTimestamp - rating.timestamp)
      val ratingWithPenalty = rating.rating * math.pow(0.95, diffInDays.toDouble).toFloat

      if (ratingWithPenalty > 0.01)
        aggRatings.update(
          (userIdAsInteger, itemIdAsInteger),
          currentRating + ratingWithPenalty
        )
    }
    Result(users.toList, items.toList, aggRatings.toList)
  }

}
