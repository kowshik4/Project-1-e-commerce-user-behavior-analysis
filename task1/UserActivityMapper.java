import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UserActivityMapper extends Mapper<Object, Text, Text, IntWritable> {

    private Text userId = new Text();
    private final static IntWritable one = new IntWritable(1);

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Split the input line by comma (assuming CSV format: UserID, ActivityType)
        String[] fields = value.toString().split(",");
        if (fields.length >= 2) {
            // Extract UserID (first field)
            userId.set(fields[0]);

            // Emit UserID with a count of 1 for each activity
            context.write(userId, one);
        }
    }
}