package com.wind.data.keywordvector;

import static org.junit.Assert.*;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 26, 2014  2:35:26 PM
 *@Description
 */
public class KeywordVectorTest {

	MapDriver<Text, Text, Text, Text> mapDriver;
	ReduceDriver<Text, Text, Text, Text> reduceDriver;
	MapReduceDriver<Text, Text, Text, Text, Text, Text> mapreduceDriver;
	
	@Before
	public void setup(){
		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
