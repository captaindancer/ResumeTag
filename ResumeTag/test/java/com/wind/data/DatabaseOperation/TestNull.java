package com.wind.data.DatabaseOperation;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 22, 2014  11:28:36 AM
 *@Description
 */
public class TestNull {

	@Test
	public void test() {
		String isNull="123456";
		if(isNull.contains("123")){
			System.out.println("****");
		}
		String contentNull=null;
		System.out.println(contentNull.trim());
	}

}
