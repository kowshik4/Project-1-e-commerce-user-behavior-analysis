public class UserActivityMapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(",");
        
        String activityType = fields[2];  // ActivityType (browse, add_to_cart, purchase)
        String productCategory = fields[3];  // Assuming product category is the third field
        
        context.write(new Text(productCategory), new Text(activityType));  // Emit ProductCategory, ActivityType
    }
}