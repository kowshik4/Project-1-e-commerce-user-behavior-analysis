import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TransactionsMapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Assuming CSV format: TransactionID,UserID,ProductCategory,ProductID,QuantitySold,RevenueGenerated,TransactionTimestamp
        String[] fields = value.toString().split(",");
        String productCategory = fields[2]; // ProductCategory column
        context.write(new Text(productCategory), new Text("purchase"));
    }
}