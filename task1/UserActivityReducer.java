import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class UserActivityReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private PriorityQueue<Map.Entry<String, Integer>> topUsers;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        topUsers = new PriorityQueue<>((a, b) -> a.getValue().compareTo(b.getValue()));
    }

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }

        // Replace with AbstractMap.SimpleEntry to ensure Java 8 compatibility
        Map.Entry<String, Integer> currentUser = new AbstractMap.SimpleEntry<>(key.toString(), sum);
        topUsers.add(currentUser);

        if (topUsers.size() > 10) {
            topUsers.poll();
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Map.Entry<String, Integer> entry : topUsers) {
            context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
        }
    }
}