package com.wind.singleton;
/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 13, 2014  10:33:06 AM
 *@Description
 */
public class SingletonInstance {

	private SingletonInstance(){
		
	}
	
	public static SingletonInstance instance=new SingletonInstance();
	
	
	public static void main(String[] args) {
		SingletonInstance instance1=SingletonInstance.instance;
		SingletonInstance instance2=SingletonInstance.instance;
		System.out.println(instance1==instance2);
	}
}
