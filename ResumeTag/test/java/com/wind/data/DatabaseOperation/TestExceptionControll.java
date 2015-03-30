package com.wind.data.DatabaseOperation;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 28, 2014  11:09:21 AM
 *@Description
 */
public class TestExceptionControll {

	@Test
	public void test() {
		for(int i=0;i<10;i++){
			try {
				System.out.println(i);
				throw new ExceptionInInitializerError("haha");
			} catch (ExceptionInInitializerError e) {
				System.out.println("in catch block");
//				e.printStackTrace();
			}
		}
		System.out.println(print());;
	}
	
	public String print(){
		String value="print";
		try {
			System.out.println("I'm in try");
			value="try";
			return value;
		} catch (ExceptionInInitializerError e) {
			e.printStackTrace();
			return "";
		}finally{
			System.out.println("finally");
			return value;
//			return "finally";
		}
	}

}
