import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class UserActivityReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int totalInteractions = 0;

        // Sum all interactions for this user
        for (IntWritable value : values) {
            totalInteractions += value.get();
        }

        // Emit the UserID and total number of interactions
        context.write(key, new IntWritable(totalInteractions));
    }
}