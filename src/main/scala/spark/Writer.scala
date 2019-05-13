package spark

import org.apache.spark.sql.{Dataset, SaveMode}

object Writer {
  def write[A](df: Dataset[A], name: String): Unit = {
    df.repartition(1)
      .write
      .mode(SaveMode.Overwrite)
      .csv(s"data/output/spark/$name")

  }

}
