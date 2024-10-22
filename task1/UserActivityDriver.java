import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class UserActivityDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        
        if (otherArgs.length != 2) {
            System.err.println("Usage: UserActivityDriver <input path> <output path>");
            System.exit(2);
        }

        // Create a new Job
        Job job = Job.getInstance(conf, "Most Engaged Users");
        job.setJarByClass(UserActivityDriver.class);
        job.setMapperClass(UserActivityMapper.class);
        job.setReducerClass(UserActivityReducer.class);

        // Specify the types of output keys and values
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Set input and output paths
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        // Execute the job and wait for its completion
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}