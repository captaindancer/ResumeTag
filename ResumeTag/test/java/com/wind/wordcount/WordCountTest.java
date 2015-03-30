package com.wind.wordcount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import com.wind.wordcount.WordCount.IntSumReducer;
import com.wind.wordcount.WordCount.TokenizerMapper;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 21, 2014  11:16:39 AM
 *@Description
 */
public class WordCountTest {

	MapDriver<Object, Text, Text, IntWritable> mapDriver; //MapDriver的参数要与Mapper一致
	ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver; //ReduceDriver的参数要与Reducer一致
	MapReduceDriver<Object, Text, Text, IntWritable, Text, IntWritable> mapreduceDriver;  //MapReduceDriver的参数是map的输入,map的输出(也是reduce的输入)和reduce的输出
	
	@Before
	public void setUp(){
		TokenizerMapper mapper=new TokenizerMapper();
		IntSumReducer reducer=new IntSumReducer();
		mapDriver=MapDriver.newMapDriver(mapper);
		reduceDriver=ReduceDriver.newReduceDriver(reducer);
		mapreduceDriver=MapReduceDriver.newMapReduceDriver(mapper, reducer);
	}
	
	@Test
	public void testMapper() throws IOException {
		mapDriver.withInput(new IntWritable(1), new Text("abc abc ab"));
//		mapDriver.withInput(new IntWritable(2), new Text("ab"));
//		mapDriver.withInput(new IntWritable(3), new Text("abc"));
//		mapDriver.withOutput(new Text("ab"), new IntWritable(1));
//		mapDriver.withOutput(new Text("abc"), new IntWritable(1));
		mapDriver.withOutput(new Text("abc"), new IntWritable(1));
		mapDriver.withOutput(new Text("abc"), new IntWritable(1));
		mapDriver.withOutput(new Text("ab"), new IntWritable(1));
		mapDriver.runTest();
	}
	
	@Test
	public void testReducer() throws IOException{
		List<IntWritable> values=new ArrayList<IntWritable>();
		values.add(new IntWritable(1));
		values.add(new IntWritable(1));
		reduceDriver.withInput(new Text("abc"),	values);
		reduceDriver.withOutput(new Text("abc"), new IntWritable(2));
		reduceDriver.runTest();
	}

	@Test
	public void testMapReduce() throws IOException{
		mapreduceDriver.withInput(new IntWritable(1), new Text("abc abc ab"));
		mapreduceDriver.withOutput(new Text("ab"), new IntWritable(1));
		mapreduceDriver.withOutput(new Text("abc"), new IntWritable(2));
		mapreduceDriver.runTest();
	}
}
