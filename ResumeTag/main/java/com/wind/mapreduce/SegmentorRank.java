package com.wind.mapreduce;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.wind.rank.AbstractExtractor;
import com.wind.rank.WordExtract;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 26, 2014  3:53:47 PM
 *@Description
 */
public class SegmentorRank extends Configured implements Tool{
	private static final int number=50;
	
	public static class SegmentorRankMapper extends Mapper<Text, Text, Text, Text>{
		private AbstractExtractor extractor = null;
		private Map<String, Double> wordMap = null;
		private Text keywordText=new Text();
		private Text vectorText = new Text();
		StringBuffer stringBuffer = new StringBuffer();
		
		@Override
		public void setup(Context context){
			Properties properties = new Properties();
			InputStream inputStream = this.getClass().getResourceAsStream(
					"/segmentor.properties");
			try {
				properties.load(inputStream);
			} catch (IOException e1) {
				throw new ExceptionInInitializerError("cannot load the config file");
			}
			String dictionaryPath=null;
			String taggerPath=null;
//			String ansjDic=null;
			if(1==Integer.parseInt(properties.getProperty("segPath"))&&1==Integer.parseInt(properties.getProperty("dicPath"))){
				taggerPath = this.getClass().getResource("/seg.m").toString();
				taggerPath=taggerPath.substring(taggerPath.indexOf("/"));
				dictionaryPath = this.getClass().getResource("/dictionary/dictionary.txt").toString();
				dictionaryPath = dictionaryPath.substring(dictionaryPath.indexOf("/"));
			}
			if(1==Integer.parseInt(properties.getProperty("segPath"))&&1!=Integer.parseInt(properties.getProperty("dicPath"))){
				taggerPath = this.getClass().getResource("/seg.m").toString();
				taggerPath=taggerPath.substring(taggerPath.indexOf("/"));
			}
			//TODO
			if(1==Integer.parseInt(properties.getProperty("ansjDic"))){
				dictionaryPath = this.getClass().getResource("/ansj.dic").toString();
				dictionaryPath = dictionaryPath.substring(dictionaryPath.indexOf("/"));
			}
			String stopwordsPath = this.getClass().getResource("/stopwords").toString();
			stopwordsPath=stopwordsPath.substring(stopwordsPath.indexOf("/"));
			
			try {
				extractor=new WordExtract(taggerPath, dictionaryPath, stopwordsPath);
			} catch (Exception e) {
				throw new ExceptionInInitializerError("cannot load the resources");
			}
		}
		
		@Override
		public void map(Text keyin, Text valuein, Context context) throws IOException, InterruptedException{
			String content=valuein.toString();
			wordMap=extractor.extract(content, number);
			for(Entry<String, Double> entry:wordMap.entrySet()){
				stringBuffer.append("("+entry.getKey()+","+entry.getValue()+")@");
			}
			keywordText.set(keyin);
			vectorText.set(wordMap.toString());
			context.write(keywordText, vectorText);
		}
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new SegmentorRank(), args);
	}

	@Override
	public int run(String[] args) throws Exception {
		JobConf jobConf=new JobConf(getConf(), SegmentorRank.class);
//		jobConf.setCompressMapOutput(true);
//		jobConf.setMapOutputCompressorClass(GzipCodec.class);
//		jobConf.set("mapreduce.map.memory.mb", "2560");
//		jobConf.set("mapred.child.java.opts", "-Xmx2048m");
//		jobConf.set("mapreduce.reduce.memory.mb", "2048");
		Job job=Job.getInstance(jobConf, "keywordvector");
		job.setMapperClass(SegmentorRankMapper.class);
//		job.setReducerClass(CollectionReducer.class);
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
