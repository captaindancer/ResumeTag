package com.wind.data.DatabaseOperation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 19, 2014  4:01:13 PM
 *@Description
 */
public class TestInstanceOf {

	@Test
	public void test() {
		Map<String, String> map=new HashMap<String, String>();
		if(map instanceof LinkedHashMap<?, ?>){
			System.out.println("yes");
		}else{
			System.out.println("no");
		}
	}

}
