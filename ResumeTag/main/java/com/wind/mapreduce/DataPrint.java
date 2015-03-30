package com.wind.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 26, 2014  3:09:28 PM
 *@Description
 */
public class DataPrint extends Configured implements Tool{

	public static class PrintMapper extends Mapper<Text, Text, Text, Text>{
		
		private Text keywordText =new Text();
		private Text valueText=new Text();
		
		@Override
		public void map(Text keyin,Text valuein,Context context) throws IOException, InterruptedException{
			keywordText.set(keyin);
			valueText.set(valuein);
			context.write(keywordText, valueText);
		}
	}
	
	public static class PrintReducer extends Reducer<Text, Text, Text, Text>{
		@Override
		public void reduce(Text keyin,Iterable<Text> values,Context context) throws IOException, InterruptedException{
			for(Text text:values){
				context.write(keyin, text);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new DataPrint(), args);
	}

	@Override
	public int run(String[] args) throws Exception {
		JobConf jobConf=new JobConf(getConf(), DataPrint.class);
//		jobConf.setCompressMapOutput(true);
//		jobConf.setMapOutputCompressorClass(GzipCodec.class);
//		jobConf.set("mapreduce.map.memory.mb", "2560");
//		jobConf.set("mapred.child.java.opts", "-Xmx2048m");
//		jobConf.set("mapreduce.reduce.memory.mb", "2048");
		Job job=Job.getInstance(jobConf, "dataprint");
		job.setMapperClass(PrintMapper.class);
		job.setReducerClass(PrintReducer.class);
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		return (job.waitForCompletion(true) ? 0 : 1);
	}

}
