import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;

public class ProductConversionDriver {
    public static void main(String[] args) throws Exception {
        // Ensure three arguments are passed
        if (args.length != 3) {
            System.err.println("Usage: ProductConversionDriver <user_activity input path> <transactions input path> <output path>");
            System.exit(-1);
        }

        // Create a new Job
        Job job = Job.getInstance();
        job.setJarByClass(ProductConversionDriver.class);
        job.setJobName("Product Purchase Conversion Rate");

        // Set multiple input paths and their corresponding mappers
        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, UserActivityMapper.class);  // First input
        MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, TransactionsMapper.class);   // Second input

        // Set the output path
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        // Specify the reducer class
        job.setReducerClass(ProductConversionReducer.class);

        // Set the output key and value types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Exit after job completion
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}