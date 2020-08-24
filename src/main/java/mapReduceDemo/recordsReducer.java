package mapReduceDemo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class recordsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int maxTemperature = Integer.MIN_VALUE;
        for (IntWritable tem : values) {
            maxTemperature = Math.max(maxTemperature, tem.get());
        }
        context.write(key, new IntWritable(maxTemperature));
    }
}
