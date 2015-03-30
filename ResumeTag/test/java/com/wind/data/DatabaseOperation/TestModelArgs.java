package com.wind.data.DatabaseOperation;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.wind.data.utils.FileUtils;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 28, 2014  2:50:47 PM
 *@Description
 */
public class TestModelArgs {

	@Test
	public void test() throws IOException {
		BufferedReader reader=FileUtils.getFileReader(new File("/home/liufeng/dataset/model.dat"));
		BufferedWriter writer=FileUtils.getFileWriter(new File("/home/liufeng/dataset/keyword.dat"));
		String str;
		int sum=0;
		while ((str = reader.readLine()) != null){
			String values[]=str.split("\\s+");
//			System.out.println(values[0]);
//			System.out.println(values[1]);
//			assertEquals(2, values.length);
			if(values.length==2){
				sum++;
				System.out.println(values[0]);
				writer.write(values[0]);
				writer.newLine();
			}
		}
		reader.close();
		writer.close();
		System.out.println(sum);
	}

}
