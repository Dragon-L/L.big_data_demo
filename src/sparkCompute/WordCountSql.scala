package sparkCompute

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}

object WordCountSql {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("wordCountSql").master("local[1]").getOrCreate()

    spark.read
      .text("data/word.txt")
      .withColumn("word", explode(split(col("value"), pattern = " ")))
      .select("word")
      .groupBy("word")
      .count()
      .orderBy("count")
      .coalesce(1)
      .write
      .mode(SaveMode.Overwrite)
      .option("quoteAll", false)
      .option("quote", " ")
      .csv("output")

    spark.close()
  }
}
