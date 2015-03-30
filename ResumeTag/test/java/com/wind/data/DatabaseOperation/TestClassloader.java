package com.wind.data.DatabaseOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 8, 2014  10:34:33 AM
 *@Description
 */
public class TestClassloader {

	@Test
	public void test() throws IOException {
//		InputStream inputStream=TestClassloader.class.getClassLoader().getResourceAsStream("stopword.dic");
		InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("stopword.dic");
		BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while((line=reader.readLine())!=null){
			System.out.println(line);
		}
		ClassLoader cl1=TestClassloader.class.getClassLoader();
		ClassLoader cl2=TestClassloader.class.getClassLoader();
		System.out.println(cl1==cl2);
		String a="a";
		String b="b";
		String ab="ab";
		System.out.println((a+b).intern()==ab);
		System.out.println(("a"+"b")==ab);
		StringBuffer buffer=new StringBuffer();
		buffer.append("a");
		buffer.append("b");
		System.out.println(ab==buffer.toString());
	}
}
