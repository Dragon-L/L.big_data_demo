package sparkCompute

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[1]").setAppName("wordCount")
    val sc = SparkContext.getOrCreate(conf)

    val file = sc.textFile("data/word.txt")
    val wordCounts = file.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .sortBy(wordCount => wordCount._2)

    wordCounts.saveAsTextFile("output")

    sc.stop()
  }
}
