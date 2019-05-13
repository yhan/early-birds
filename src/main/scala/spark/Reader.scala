package spark

import model.Rating
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{FloatType, LongType, StringType, StructType}

object Reader {
  def read(spark: SparkSession): Dataset[Rating] = {
    import spark.implicits._

    val inputSchema = new StructType()
      .add("userId", StringType)
      .add("itemId", StringType)
      .add("rating", FloatType)
      .add("timestamp", LongType)

    spark.read
      .schema(inputSchema)
      .csv("data/input/xag.csv")
      .as[Rating]
  }

}
