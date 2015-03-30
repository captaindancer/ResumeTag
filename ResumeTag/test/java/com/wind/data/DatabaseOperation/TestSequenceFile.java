package com.wind.data.DatabaseOperation;

import org.junit.Test;


/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 28, 2014  6:29:10 PM
 *@Description
 */
public class TestSequenceFile {

	@Test
	public void test() {
		String uri="/home/liufeng/data/extraction.dat";
		long start_time=System.currentTimeMillis();
		try {
			TestSequenceFileCreation.createSequenceFile(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis()-start_time);
	}

}
