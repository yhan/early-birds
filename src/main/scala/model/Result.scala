package model

final case class Result(
    users: List[(String, Int)],
    items: List[(String, Int)],
    aggRatings: List[((Int, Int), Float)]
)
