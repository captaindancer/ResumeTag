package com.wind.mapreduce;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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

import com.wind.rank.AbstractExtractor;
import com.wind.rank.WordExtract;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 18, 2014  3:54:54 PM
 *@Description
 */
public class KeywordVector extends Configured implements Tool{
	private static final int number=50;
	//设定提取的特征的数目
	private static final int featurenum=100;
	
    public static class CollectionMapper extends Mapper<Text, Text, Text, Text>{
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
			/*Iterator<String> it = wordMap.keySet().iterator();
			while(it.hasNext())
			{
				String key = it.next();
				stringBuffer.append("("+key+","+wordMap.get(key)+")@");
			}*/
			for(Entry<String, Double> entry:wordMap.entrySet()){
				stringBuffer.append("("+entry.getKey()+","+entry.getValue()+")@");
			}
			String vectorLists=stringBuffer.toString();
			stringBuffer.setLength(0);
			if(vectorLists.endsWith("@")){
				vectorLists=vectorLists.substring(0, vectorLists.length()-1);
			}
			String keyword=keyin.toString().split("@")[1];
			if(!keyword.matches("\\s*")&&!keyword.equals("")&&!keyword.contains("&")&&!keyword.contains("#")&&!keyword.contains("(")&&!keyword.contains(")")&&!keyword.contains("*")&&!keyword.contains("..")&&!keyword.startsWith("+")&&!keyword.startsWith("＋")&&!keyword.startsWith("-")&&!keyword.startsWith("－")){
				if(keyword.startsWith("/")||keyword.endsWith("/")){
					keyword=keyword.replaceAll("/", "");
				}
				if(keyword.startsWith("／")||keyword.endsWith("／")){
					keyword=keyword.replaceAll("／", "");
				}
				if(keyword.contains("\\")){
					keyword=keyword.replaceAll("\\\\", "");
				}
				if(keyword.startsWith(":")||keyword.endsWith(":")){
					keyword=keyword.replaceAll(":", "");
				}
				if(keyword.startsWith(";")||keyword.endsWith(";")){
					keyword=keyword.replaceAll(";", "");
				}
				if(keyword.startsWith("！")){
					keyword=keyword.replaceAll("！", "");
				}
				if(keyword.startsWith("!")){
					keyword=keyword.replaceAll("!", "");
				}
				if(keyword.startsWith("（")){
					keyword=keyword.replaceAll("（", "");
				}
				if(keyword.startsWith("）")){
					keyword=keyword.replaceAll("）", "");
				}
				if(keyword.endsWith("）")){
					keyword=keyword.replaceAll("）", "");
				}
				if(keyword.startsWith(".")&&!keyword.toUpperCase().contains("NET")){
					keyword=keyword.replaceAll(".","");
				}
				Pattern pattern=Pattern.compile("\\S*\\d+\\S*");
				Matcher matcher=pattern.matcher(keyword);
				if(!matcher.find()){
					keywordText.set(keyword.toUpperCase());
					vectorText.set(vectorLists);
					context.write(keywordText, vectorText);
				}
			}
		}
    }
    
    public static class CollectionReducer extends Reducer<Text, Text, Text, Text>{
    	private Text keywordFreq=new Text();
		private Text vectorWeight=new Text();
		private StringBuffer stringBuffer=new StringBuffer();
    	
    	@Override
    	public void reduce(Text keyin,Iterable<Text> values,Context context) throws IOException, InterruptedException{
    		int sum=0;
    		Map<String, Double> vectorMap=new LinkedHashMap<String,Double>(1000);
    		for(Text text:values){
    			sum++;
    			for(String pair:text.toString().split("@")){
//    				System.out.println("***"+pair+"***");
    				if(pair.contains(",")){
    					String pairKey=pair.split(",")[0].replaceAll("\\(", "");
        				String pairValue=pair.split(",")[1].replaceAll("\\)", "");
        				double valueWeight=Double.valueOf(pairValue);
        				if(vectorMap.containsKey(pairKey)){
        					vectorMap.put(pairKey, vectorMap.get(pairKey)+valueWeight);
        				}else{
        					vectorMap.put(pairKey, valueWeight);
        				}
    				}
    			}
    		}
    		int length=0;
    		double norms=0;
    		for(Entry<String, Double> entry:vectorMap.entrySet()){
//				stringBuffer.append("("+entry.getKey()+","+entry.getValue()+")@");
    			length++;
    			if(length<=featurenum){
    				norms+=(entry.getValue()/sum)*(entry.getValue()/sum);
    				stringBuffer.append("("+entry.getKey()+","+entry.getValue()/sum+")@");
    			}else{
    				break;
    			}
			}
    		norms=Math.sqrt(norms);
    		stringBuffer.append("(norms,"+norms+")");
    		String vectorLists=stringBuffer.toString();
    		stringBuffer.setLength(0);
//			if(vectorLists.endsWith("@")){
//				vectorLists=vectorLists.substring(0, vectorLists.length()-1);
//			}
//    		keywordFreq.set(keyin.toString()+"="+sum);
			keywordFreq.set(keyin.toString());
    		vectorWeight.set(vectorLists);
    		stringBuffer.setLength(0);
    		context.write(keywordFreq, vectorWeight);
    	}
    }
    
    public static void main(String[] args) throws Exception  {
//		Configuration configuration=new Configuration();
//		JobConf jobConf=new JobConf(configuration, KeywordVector.class);
//		String[] otherArgs = new GenericOptionsParser(configuration, args).getRemainingArgs();
//		System.out.println(jobConf.get("yarn.scheduler.fair.max.assign"));
//		Job job=Job.getInstance(jobConf, "KeywordVector");
//		job.setMapperClass(CollectionMapper.class);
//		job.setReducerClass(CollectionReducer.class);
//		job.setInputFormatClass(SequenceFileInputFormat.class);
//		//默认的OutputFormat为TextOutputFormat
//		job.setOutputFormatClass(SequenceFileOutputFormat.class);
//		//默认的map输出结果的格式与最后reduce的一致
//		job.setMapOutputKeyClass(Text.class);
//		job.setMapOutputValueClass(Text.class);
//		job.setOutputKeyClass(Text.class);
//		job.setOutputValueClass(Text.class);
//		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
//		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
//		System.exit(job.waitForCompletion(true) ? 0 : 1);
    	ToolRunner.run(new Configuration(), new KeywordVector(), args);
	}

	@Override
	public int run(String[] args) throws Exception {
		JobConf jobConf=new JobConf(getConf(), KeywordVector.class);
		jobConf.setCompressMapOutput(true);
		jobConf.setMapOutputCompressorClass(GzipCodec.class);
		jobConf.set("mapreduce.map.memory.mb", "2560");
		jobConf.set("mapred.child.java.opts", "-Xmx2048m");
		jobConf.set("mapreduce.reduce.memory.mb", "2048");
		jobConf.set("mapreduce.input.fileinputformat.split.minsize", "2");
//		jobConf.set("mapred.mapper.new-api", "true");
//		jobConf.setUseNewMapper(true);
//		System.out.println(jobConf.get("mapreduce.map.memory.mb"));
		System.out.println(jobConf.get("mapreduce.input.fileinputformat.split.minsize"));
		System.out.println(jobConf.get("mapred.mapper.new-api"));
		System.out.println(jobConf.get("NUM_INPUT_FILES"));
		Job job=Job.getInstance(jobConf, "keywordvector");
		job.setMapperClass(CollectionMapper.class);
		job.setReducerClass(CollectionReducer.class);
		job.setInputFormatClass(SequenceFileInputFormat.class);
//		System.out.println(SequenceFileInputFormat.getMinSplitSize(job));
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
//		job.setNumReduceTasks(3);
		System.out.println(job.getConfiguration().get("mapreduce.input.fileinputformat.split.minsize"));
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileInputFormat.setMinInputSplitSize(job, 67108864);
		FileInputFormat.setMaxInputSplitSize(job, 67108864);
		System.out.println(job.getConfiguration().get("mapreduce.input.fileinputformat.split.minsize"));
//		System.out.println(jobConf.get("mapreduce.input.fileinputformat.inputdir"));
		System.out.println(job.getConfiguration().get("mapreduce.input.fileinputformat.inputdir"));
		return (job.waitForCompletion(true) ? 0 : 1);
	}
}
