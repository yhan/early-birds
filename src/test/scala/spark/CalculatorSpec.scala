package spark

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import model.Rating
import org.apache.spark.sql.Dataset
import org.scalactic.Equality
import org.scalactic.TolerantNumerics.tolerantDoubleEquality
import org.scalatest.{FlatSpec, Matchers}

class CalculatorSpec extends FlatSpec with Matchers with DataFrameSuiteBase {
  "Calculator" should "compute result" in {
    import spark.implicits._

    implicit val doubleEq: Equality[Double] = tolerantDoubleEquality(1e-2)

    val ratings = Seq(
      Rating("user1", "item1", 0.1f, 1),
      Rating("user1", "item1", 0.2f, 1),
      Rating("user1", "item2", 0.4f, 1),
      Rating("user2", "item1", 0.5f, 1)
    )
    val ratingsDs: Dataset[Rating]       = spark.createDataset(ratings)
    val (usersDs, itemsDs, aggRatingsDs) = Calculator.compute(ratingsDs)

    usersDs.collect().map(_.userId) should contain theSameElementsAs Seq("user1", "user2")
    usersDs.collect().map(_.userIdAsInteger) should contain theSameElementsAs Seq(0, 1)

    itemsDs.collect().map(_.itemId) should contain theSameElementsAs Seq("item1", "item2")
    itemsDs.collect().map(_.itemIdAsInteger) should contain theSameElementsAs Seq(0, 1)

    val computedRatings = aggRatingsDs.collect().map(_.adjustedRating).sorted

    computedRatings(0) should equal(0.3d)
    computedRatings(1) should equal(0.4d)
    computedRatings(2) should equal(0.5d)
  }
}
