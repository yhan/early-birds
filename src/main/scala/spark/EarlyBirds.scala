package spark

import org.apache.spark.sql.SparkSession

object EarlyBirds extends App {
  withSpark { spark =>
    val ratings                          = Reader.read(spark)
    val (usersDs, itemsDs, aggRatingsDs) = Calculator.compute(ratings)
    Writer.write(usersDs, "lookupuser.csv")
    Writer.write(itemsDs, "lookup_product.csv")
    Writer.write(aggRatingsDs, "aggratings.csv")
  }

  private def withSpark(f: SparkSession => Unit): Unit = {
    val spark = SparkSession
      .builder()
      .appName("EarlyBirds")
      .master("local[*]")
      .getOrCreate()
    try {
      f(spark)
    } finally {
      spark.close()
    }
  }
}
