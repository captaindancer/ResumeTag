package com.wind.data.DatabaseOperation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.wind.data.utils.RegExp;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 31, 2014  6:43:52 PM
 *@Description
 */
public class TestService {

	@Test
	public void test() {
		RegExp regExp=new RegExp();
		List<String> testList=regExp.getGroupList("dfsdf", "");
		System.out.println(testList);
		Pattern pattern=Pattern.compile("");
		Matcher matcher=pattern.matcher(null);
	}

}
