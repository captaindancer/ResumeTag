package com.wind.data.DatabaseOperation;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 24, 2014  6:57:33 PM
 *@Description
 */
public class TestNewline {

	@Test
	public void test() {
		String newLine="<html>dfafdf\ndffs</html>";
		String regExp="(?<=<html>)[\\s\\S]+?(?=</html>)";
		Pattern pattern=Pattern.compile(regExp);
		Matcher matcher=pattern.matcher(newLine);
		while(matcher.find()){
			System.out.println("提取的值:"+matcher.group());
		}
		System.out.println("********");
		System.out.println(newLine);
		System.out.println("The resume is :\nresumeId");
	}

}
