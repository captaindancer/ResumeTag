package com.wind.data.DatabaseOperation;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Sep 3, 2014  3:59:03 PM
 *@Description
 */
public class TestOutput {

	@Test
	public void test() {
		String content="\\content";
		if(content.contains("\\")){
			System.out.println(content);
		}
		System.out.println(content.replaceAll("\\\\", ""));
	}

}
