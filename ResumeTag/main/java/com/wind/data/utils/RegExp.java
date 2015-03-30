package com.wind.data.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 18, 2014  2:09:17 PM
 *@Description
 */
public class RegExp {

	private Pattern pattern;
	private Matcher matcher;
	private static final String LEFT_ASSERTION="(?<=";
	private static final String MIDDLE_ASSERTION=").+?(?=";
	private static final String MIDDLE_GREEDY_ASSERTION=").+(?=";
	private static final String RIGHT_ASSERTION=")";
	private List<String> groupList;
	
	public String getGroup(String regexp_Str,String content){
		pattern=Pattern.compile(regexp_Str);
		matcher=pattern.matcher(content);
		if(matcher.find()){
			return matcher.group();
		}else{
			return null;
		}
	}
	
	public List<String> getGroupList(String regexp_Str,String content){
		pattern=Pattern.compile(regexp_Str);
		matcher=pattern.matcher(content);
		groupList=new ArrayList<String>();
		while(matcher.find()){
			groupList.add(matcher.group());
		}
		return groupList;
	}
	
	public String getAssertion(String openTag,String closeTag){
		return LEFT_ASSERTION+openTag+MIDDLE_ASSERTION+closeTag+RIGHT_ASSERTION;
	}
	
	public String getGreedyAssertion(String openTag,String closeTag){
		return LEFT_ASSERTION+openTag+MIDDLE_GREEDY_ASSERTION+closeTag+RIGHT_ASSERTION;
	}
	
}
