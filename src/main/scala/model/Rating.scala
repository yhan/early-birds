package model

final case class Rating(userId: String, itemId: String, rating: Float, timestamp: Long)
