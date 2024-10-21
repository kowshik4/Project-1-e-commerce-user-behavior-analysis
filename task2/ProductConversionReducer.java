import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ProductConversionReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int interactionCount = 0;
        int purchaseCount = 0;

        // Count interactions and purchases
        for (Text value : values) {
            if (value.toString().equals("interaction")) {
                interactionCount++;
            } else if (value.toString().equals("purchase")) {
                purchaseCount++;
            }
        }

        // Calculate conversion rate
        double conversionRate = 0.0;
        if (interactionCount > 0) {
            conversionRate = (double) purchaseCount / interactionCount;
        }

        // Emit product category and conversion rate
        context.write(key, new Text(String.format("%.2f", conversionRate)));
    }
}