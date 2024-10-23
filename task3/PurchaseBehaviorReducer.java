import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class PurchaseBehaviorReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        
        // Sum up all the purchases for a given hour-productCategory key
        for (IntWritable value : values) {
            sum += value.get();
        }
        
        // Emit the hour-productCategory and the sum of purchases
        result.set(sum);
        context.write(key, result);
    }
}