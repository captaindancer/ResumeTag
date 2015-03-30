package com.wind.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Oct 9, 2014  9:02:21 AM
 *@Description
 */
public class StartValue extends Configured implements Tool {

	public static class StartMapper extends Mapper<LongWritable, Text, LongWritable, IntWritable>{
//		private Text startvalue=new Text();
		private IntWritable flag=new IntWritable(1);
		
		@Override
		public void map(LongWritable keyin,Text valuein,Context context) throws IOException, InterruptedException{
//			startvalue.set(keyin.toString());
			context.write(keyin,flag);
		}
	}
	
	
	@Override
	public int run(String[] args) throws Exception {
		JobConf jobConf=new JobConf(getConf(),StartValue.class);
		Job job=Job.getInstance(jobConf, "startvalue");
		job.setMapperClass(StartMapper.class);
		job.setNumReduceTasks(3);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		return (job.waitForCompletion(true) ? 0 : 1);
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new StartValue(), args);
	}
}
