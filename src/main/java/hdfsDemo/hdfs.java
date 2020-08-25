package hdfsDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.IOException;

public class hdfs {
    public static void main(String[] args) throws IOException {
        String hdfsPath = "/temp/dataFromJava.txt";

        // 1. init FS instance
        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(conf);

        // 2. upload data: create()
        FSDataOutputStream outputStream = fileSystem.create(new Path(hdfsPath), new Progressable() {
            @Override
            public void progress() {
                System.out.println("upload success!");
            }
        });
        outputStream.writeChars("this is a test message written in java code!");

        // 3. flush data to download
        outputStream.hflush();

        // 4. download data: open()
        FSDataInputStream inputStream = fileSystem.open(new Path(hdfsPath));
        IOUtils.copyBytes(inputStream, System.out, 4096, true);
    }
}
