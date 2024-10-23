import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PurchaseBehaviorMapper extends Mapper<Object, Text, Text, IntWritable> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Text outKey = new Text();
    private IntWritable outValue = new IntWritable(1);  // Each record is one purchase

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(",");
        if (fields.length == 7) {  // Ensure that there are enough fields
            try {
                String transactionTimestamp = fields[6];
                Date transactionDate = dateFormat.parse(transactionTimestamp);
                // Extract the hour from the transaction timestamp
                SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
                String hour = hourFormat.format(transactionDate);
                String productCategory = fields[2];  // Extract the ProductCategory

                // Emit the hour and product category as key, with a count of 1
                outKey.set(hour + "\t" + productCategory);
                context.write(outKey, outValue);
            } catch (ParseException e) {
                // Log and skip if parsing fails
                System.err.println("Unparseable date: " + fields[6]);
            }
        }
    }
}