package com.wind.data.DatabaseOperation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 27, 2014  10:49:38 AM
 *@Description
 */
public class TestComma {

	@Test
	public void test() {
		String content="(驾照)";
		if(content.contains("(")){
			System.out.println("exist!");
		}
		content="linux/unix";
		if(content.contains("/")){
			System.out.println("/");
		}
		content="/AMP";
		if(content.startsWith("/")){
			System.out.println(content.replaceAll("/", ""));
		}
		System.out.println(content.replaceAll("/", ""));
		System.out.println("\\+");
		Pattern pattern=Pattern.compile("\\+\\w+");
		Matcher matcher=pattern.matcher("12+dfa");
		if(matcher.find()){
			System.out.println("干掉");
		}
		content="-98977";
		System.out.println(content);
		System.out.println(content.replaceAll("-", ""));
		content="（简历）";
		System.out.println(content);
		System.out.println(content.replaceAll("（", ""));
		pattern=Pattern.compile("\\S*\\d+\\S*");
		matcher=pattern.matcher("绘图员");
		if(matcher.find()){
			System.out.println("干掉");
		}
	}

}
