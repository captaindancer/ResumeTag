package com.wind.data.DatabaseOperation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 30, 2014  4:46:42 PM
 *@Description
 */
public class TestMap {

	@Test
	public void test() {
		Map<Integer, String> numMap=new HashMap<Integer, String>();
		numMap.put(1, "l");
		numMap.put(2, "i");
		numMap.put(3, "u");
		numMap.put(4, "f");
		for(Entry<Integer, String> entry:numMap.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		String regExp="liufeng||wind||com";
		System.out.println(regExp);
		for(String str:regExp.split("||")){
			System.out.println(str);
		}
	}

}
