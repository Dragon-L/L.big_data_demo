package mapReduceDemo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;

public class recordsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] split = line.split(",", 2);
        String year = split[0];
        Integer maxTemperature = Arrays.stream(split[1].split(","))
                .mapToInt(Integer::valueOf)
                .boxed()
                .max(Integer::compareTo)
                .get();
        context.write(new Text(year), new IntWritable(maxTemperature));
    }
}
