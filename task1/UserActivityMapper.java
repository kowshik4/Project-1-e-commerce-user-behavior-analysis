import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UserActivityMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text userID = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Split the input line by comma
        String[] fields = value.toString().split(",");

        if (fields.length > 1) {
            // Extract UserID (second column, index 1)
            userID.set(fields[1]);

            // Emit UserID and a count of 1
            context.write(userID, one);
        }
    }
}