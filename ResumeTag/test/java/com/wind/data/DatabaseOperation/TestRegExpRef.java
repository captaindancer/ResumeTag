package com.wind.data.DatabaseOperation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 15, 2014  7:10:22 PM
 *@Description
 */
public class TestRegExpRef {

	@Test
	public void test() {
		//自动命名分组匹配
		String regExp="\\b(\\w+)\\b\\s+\\1\\b";
		//jdk自带的正则表达式引擎不支持命名分组匹配
//		regExp="\\b(?<Word>\\w+)\\b\\s+\\k<Word>\\b";
		System.out.println(regExp);
		Pattern pattern=Pattern.compile(regExp);
		Matcher matcher=pattern.matcher("go   go");
		if(matcher.find()){
			System.out.println("ok");
		}
		//零宽度正预测先行断言,匹配exp前面的位置
		regExp="\\b\\w+(?=ing\\b)";
		System.out.println(regExp);
		pattern=Pattern.compile(regExp);
		matcher=pattern.matcher("just singing and dancing");
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.end());
		}
		regExp="\\[(.*?)\\],";
		System.out.println(regExp);
		//零宽度正回顾后发断言
		regExp="(?<=re)\\w+\\b";
		pattern=Pattern.compile(regExp);
		matcher=pattern.matcher("reading a book");
		if(matcher.find()){
			System.out.println(matcher.group());
		}
		regExp="(?<=<span id=\"spanTitled\">)\\S+(?=</span>)";
		pattern=Pattern.compile(regExp);
		matcher=pattern.matcher("(?<=<span id=\"spanTitled\">).+(?=</span>)");
		if(matcher.find()){
			matcher.group();
		}
		//提取<>中的内容
		regExp="(?<=<)(.+?)(?=>)";
		pattern=Pattern.compile(regExp);
		matcher=pattern.matcher("<12>");
		if(matcher.find()){
			System.out.println(matcher.group());
		}
		//匹配不包含属性的简单html标签里的内容 零宽断言中不能含有+ * .
		regExp="(?<=<(\\w{1,10})>).*(?=<\\/\\1>)";
//		regExp="<(\\w{1,10})>.*<\\/\\1>";
		System.out.println(regExp);
		pattern=Pattern.compile(regExp);
		matcher=pattern.matcher("<b>html</b>");
		while(matcher.find()){
			System.out.println(matcher.group());
		}
		//匹配嵌套的div标签
		regExp="";
	}

}
