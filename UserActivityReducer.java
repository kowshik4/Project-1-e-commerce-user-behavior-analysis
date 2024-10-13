import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UserActivityReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private Map<String, Integer> userActivityCountMap = new HashMap<>();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int totalCount = 0;
        for (IntWritable value : values) {
            totalCount += value.get();
        }
        userActivityCountMap.put(key.toString(), totalCount);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        // Sort users by activity count and get the top 10
        TreeMap<Integer, String> sortedMap = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : userActivityCountMap.entrySet()) {
            sortedMap.put(entry.getValue(), entry.getKey());
        }

        int count = 0;
        for (Map.Entry<Integer, String> entry : sortedMap.descendingMap().entrySet()) {
            if (count++ == 10) {
                break;
            }
            context.write(new Text(entry.getValue()), new IntWritable(entry.getKey()));
        }
    }
}