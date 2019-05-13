package raw

import java.io.{File, PrintWriter}

import model.Result

import scala.io.Source

object EarlyBirds extends App {
  val ratings                          = Reader.read(Source.fromFile("data/input/xag.csv"))
  val ratings2                         = Reader.read(Source.fromFile("data/input/xag.csv"))
  val maxTimestamp                     = Calculator.maxTimestamp(ratings2)
  val Result(users, items, aggRatings) = Calculator.compute(ratings, maxTimestamp)
  Writer.writeUsers(users, new PrintWriter(new File("data/output/raw/lookupuser.csv")))
  Writer.writeItems(items, new PrintWriter(new File("data/output/raw/lookup_product.csv")))
  Writer.writeAggRatings(aggRatings, new PrintWriter(new File("data/output/raw/aggratings.csv")))
}
