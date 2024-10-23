import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class PurchaseBehaviorDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: PurchaseBehaviorDriver <input path> <output path>");
            System.exit(-1);
        }
        
        // Create a new Job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "User Purchase Behavior Based on Time of Day");
        
        job.setJarByClass(PurchaseBehaviorDriver.class);
        
        // Specify Mapper and Reducer classes
        job.setMapperClass(PurchaseBehaviorMapper.class);
        job.setReducerClass(PurchaseBehaviorReducer.class);
        
        // Set output key and value types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        // Specify input and output paths
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        // Exit after the job completes
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}