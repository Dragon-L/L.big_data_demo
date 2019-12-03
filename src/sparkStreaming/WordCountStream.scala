package sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._

object WordCountStream {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[*]").setAppName("wordCountStreaming")
    val ssc = new StreamingContext(conf, Seconds(2))
    SparkSession.builder().getOrCreate().sparkContext.setLogLevel("ERROR")

    val lines = ssc.socketTextStream("localhost", 1234)
    val wordCounts = lines.flatMap(_.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }

}
