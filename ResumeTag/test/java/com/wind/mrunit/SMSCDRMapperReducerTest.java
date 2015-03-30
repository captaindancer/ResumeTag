package com.wind.mrunit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import com.wind.mrunit.SMSCDR.SMSCDRMapper;
import com.wind.mrunit.SMSCDR.SMSCDRReducer;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 21, 2014  10:14:07 AM
 *@Description
 */
public class SMSCDRMapperReducerTest {

	MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
	ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
	MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;
	
	@Before
	public void setUp() {
	  SMSCDRMapper mapper = new SMSCDRMapper();
	  SMSCDRReducer reducer = new SMSCDRReducer();
	  mapDriver = MapDriver.newMapDriver(mapper);;
	  reduceDriver = ReduceDriver.newReduceDriver(reducer);
	  mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
	}
	
	@Test
	public void testMapper() throws IOException {
	  mapDriver.withInput(new LongWritable(), new Text("655209;1;796764372490213;804422938115889;6"));
	  mapDriver.withOutput(new Text("6"), new IntWritable(1));
	  mapDriver.runTest();
	}
	
	@Test
	public void testReducer() throws IOException {
	  List<IntWritable> values = new ArrayList<IntWritable>();
	  values.add(new IntWritable(1));
	  values.add(new IntWritable(1));
	  reduceDriver.withInput(new Text("6"), values);
	  reduceDriver.withOutput(new Text("6"), new IntWritable(2));
	  reduceDriver.runTest();
	}

}
