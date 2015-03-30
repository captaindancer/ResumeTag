package com.wind.data.DatabaseOperation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 16, 2014  3:04:23 PM
 *@Description
 */
public class TestKeyword {

	@Test
	public void test() {
		Pattern p = Pattern.compile("(?<=<span id=\"spanTitled\">).+(?=</span>)");
		String str ="<span id=\"spanTitled\">软件工程师 IT支持工程师</span>";
		Matcher m = p.matcher(str);
		while(m.find())
			System.out.println(m.group());
		p=Pattern.compile("\\b\\w+(?=ing\\b)");
		m=p.matcher("just singing and dancing");
		while(m.find()){
			System.out.println(m.group());
		}
	}
}
