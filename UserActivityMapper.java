import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UserActivityMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text userId = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Split the CSV line by commas
        String[] fields = value.toString().split(",");

        // Assuming the second column (index 1) is the UserID
        if (fields.length > 1) {
            userId.set(fields[1]);  // Set UserID
            context.write(userId, one);  // Emit UserID and count 1
        }
    }
}