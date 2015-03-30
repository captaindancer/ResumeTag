package com.wind.data.DatabaseOperation;

import java.util.ArrayList;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 4, 2014  11:10:14 AM
 *@Description
 */
public class TestDouble {

	@Test
	public void test() {
		ArrayList<Double> doubleList=new ArrayList<Double>();
		doubleList.add(1.0);
		System.out.println(doubleList);
		System.out.println(Double.MAX_VALUE);
		System.out.println(Double.MIN_VALUE);
		System.out.println(Double.MAX_EXPONENT);
		System.out.println(Long.MAX_VALUE);
		double number=0.0;
		if(number==0){
			System.out.println("OK");
		}
		String str=null;
		System.out.println(str="123");
		System.out.println(str);
	}

}
