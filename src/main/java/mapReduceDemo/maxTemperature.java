package mapReduceDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class maxTemperature {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Max temperature");
        job.setJarByClass(maxTemperature.class);

        job.setMapperClass(recordsMapper.class);
        job.setReducerClass(recordsReducer.class);

        FileInputFormat.addInputPath(job, new Path("/Users/glliao/WorkSpace/My_project/L.big_data_demo/src/main/java/mapReduceDemo/temperatureRecords.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/Users/glliao/WorkSpace/My_project/L.big_data_demo/src/main/java/mapReduceDemo/output"));

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
