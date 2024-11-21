import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class UserActivityReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private static boolean headerWritten = false; // Flag to ensure header is written only once

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // Emit the header as the first output
        if (!headerWritten) {
            context.write(new Text("User ID"), new IntWritable(0)); // Placeholder for the header
            context.write(new Text("Activity Count"), null);        // Second column header
            headerWritten = true;
        }
    }

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        context.write(key, new IntWritable(sum));
    }
}