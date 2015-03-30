package com.wind.mapreduce;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
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
 * @author liufeng E-mail:fliu.Dancer@wind.com.cn
 * @version Time:Sep 2, 2014 9:57:05 AM
 * @Description
 */
public class KeywordValueStatistics extends Configured implements Tool{

	public static class StatisticsMapper extends Mapper<Text, Text, Text, IntWritable> {
		private Text keywordText = new Text();
		private IntWritable one = new IntWritable(1);

		@Override
		public void map(Text keyin, Text valuein, Context context) throws IOException, InterruptedException {
			String keyword = keyin.toString().split("@")[1];
			if (!keyword.equals("") && !keyword.contains("&") && !keyword.contains("#") && !keyword.contains("(") && !keyword.contains(")")
					&& !keyword.contains("*") && !keyword.contains("..") && !keyword.startsWith("+") && !keyword.startsWith("＋")
					&& !keyword.startsWith("-") && !keyword.startsWith("－")) {
				if (keyword.startsWith("/") || keyword.endsWith("/")) {
					keyword = keyword.replaceAll("/", "");
				}
				if (keyword.startsWith("／") || keyword.endsWith("／")) {
					keyword = keyword.replaceAll("／", "");
				}
				if (keyword.contains("\\")) {
					keyword = keyword.replaceAll("\\\\", "");
				}
				if (keyword.startsWith(":") || keyword.endsWith(":")) {
					keyword = keyword.replaceAll(":", "");
				}
				if (keyword.startsWith(";") || keyword.endsWith(";")) {
					keyword = keyword.replaceAll(";", "");
				}
				if (keyword.startsWith("！")) {
					keyword = keyword.replaceAll("！", "");
				}
				if (keyword.startsWith("!")) {
					keyword = keyword.replaceAll("!", "");
				}
				if (keyword.startsWith("（")) {
					keyword = keyword.replaceAll("（", "");
				}
				if (keyword.startsWith("）")) {
					keyword = keyword.replaceAll("）", "");
				}
				if (keyword.endsWith("）")) {
					keyword = keyword.replaceAll("）", "");
				}
				if (keyword.startsWith(".") && !keyword.toUpperCase().contains("NET")) {
					keyword = keyword.replaceAll(".", "");
				}
				Pattern pattern = Pattern.compile("\\S*\\d+\\S*");
				Matcher matcher = pattern.matcher(keyword);
				if (!matcher.find()) {
					keywordText.set(keyword.toUpperCase());
					context.write(keywordText, one);
				}
			}
		}
	}

	public static class StatisticsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		JobConf jobConf=new JobConf(getConf(), KeywordValueStatistics.class);
		jobConf.setCompressMapOutput(true);
		jobConf.setMapOutputCompressorClass(GzipCodec.class);
//		jobConf.set("mapreduce.map.memory.mb", "2560");
//		jobConf.set("mapred.child.java.opts", "-Xmx2048m");
//		jobConf.set("mapreduce.reduce.memory.mb", "2048");
		Job job=Job.getInstance(jobConf, "statistics");
		job.setMapperClass(StatisticsMapper.class);
		job.setReducerClass(StatisticsReducer.class);
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		return (job.waitForCompletion(true) ? 0 : 1);
	}
	
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new KeywordValueStatistics(), args);
	}

}
