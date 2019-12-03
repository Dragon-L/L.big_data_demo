package sparkStreaming

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WordCountKafka {
  def main(args: Array[String]): Unit = {
    val HasState = true

    val conf: SparkConf = new SparkConf().setAppName("wordCountKafka").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(2))
    SparkSession.builder().getOrCreate().sparkContext.setLogLevel("ERROR")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "thoughtwork001",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )
    val dStream = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Array("my"), kafkaParams)
    )


    val wordCounts = dStream.flatMap(_.value().split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    if (HasState) {
      ssc.checkpoint("checkpoint")
      val wordCounts2 = wordCounts.updateStateByKey(updateFunc)
      wordCounts2.print()
    } else {
      wordCounts.print()
    }

    ssc.start()
    ssc.awaitTermination()
  }


  def updateFunc(count: Seq[Int], recordCount: Option[Int]): Option[Int] = {
    Option(count.sum + recordCount.getOrElse(0))
  }
}
