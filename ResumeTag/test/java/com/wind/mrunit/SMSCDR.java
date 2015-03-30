package com.wind.mrunit;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 21, 2014  8:51:47 AM
 *@Description
 */
public class SMSCDR {
	public static class SMSCDRMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		 
		  private Text status = new Text();
		  private final static IntWritable addOne = new IntWritable(1);
		 
		  /**
		   * Returns the SMS status code and its count
		   */
		  protected void map(LongWritable key, Text value, Context context)
		      throws java.io.IOException, InterruptedException {
		 
		    //655209;1;796764372490213;804422938115889;6 is the Sample record format
		    String[] line = value.toString().split(";");
		    // If record is of SMS CDR
		    if (Integer.parseInt(line[1]) == 1) {
		      status.set(line[4]);
		      context.write(status, addOne);
	    }
	  }
	}
	
	public static class SMSCDRReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	 
	  protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws java.io.IOException, InterruptedException {
	    int sum = 0;
	    for (IntWritable value : values) {
	      sum += value.get();
	    }
	    context.write(key, new IntWritable(sum));
	  }
	}
}
