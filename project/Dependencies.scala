import sbt._

object Dependencies {
  lazy val scalaTest        = "org.scalatest"    %% "scalatest"          % "3.0.5"
  lazy val sparkSql         = "org.apache.spark" %% "spark-sql"          % "2.4.3"
  lazy val sparkTestingBase = "com.holdenkarau"  %% "spark-testing-base" % "2.4.0_0.12.0"

}
