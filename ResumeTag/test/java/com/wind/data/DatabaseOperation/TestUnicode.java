package com.wind.data.DatabaseOperation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 31, 2014  4:18:03 PM
 *@Description
 */
public class TestUnicode {

	@Test
	public void test() {
		System.out.println(UnicodeToString("\u51B3"));
	}

	public String UnicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4 }))");    
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");    
        }
        return str;
    }
}
