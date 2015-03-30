package com.wind.data.DatabaseOperation;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 4, 2014  5:27:20 PM
 *@Description
 */
public class TestLength {

	@Test
	public void test() {
		String[] arrayStr={"com","cn","wind","www"};
		int len=0;
		for(String str:arrayStr)
			len++;
		System.out.println(len);
		System.out.println(arrayStr.length);
	}

}
