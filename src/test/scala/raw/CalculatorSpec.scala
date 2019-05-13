package raw

import model.Rating
import org.scalatest.{FlatSpec, Matchers}

class CalculatorSpec extends FlatSpec with Matchers {
  "The Calculator object" should "compute result" in {
    val ratings = Seq(
      Rating("user1", "item1", 0.1f, 1),
      Rating("user1", "item1", 0.2f, 1),
      Rating("user1", "item2", 0.4f, 1),
      Rating("user2", "item1", 0.5f, 1)
    )
    val result = Calculator.compute(ratings.iterator, 1)
    result.users should contain theSameElementsAs Seq(
      "user1" -> 0,
      "user2" -> 1
    )

    result.items should contain theSameElementsAs Seq(
      "item1" -> 0,
      "item2" -> 1
    )

    result.aggRatings should contain theSameElementsAs Seq(
      (0, 0) -> 0.3f,
      (0, 1) -> 0.4f,
      (1, 0) -> 0.5f
    )
  }
}
