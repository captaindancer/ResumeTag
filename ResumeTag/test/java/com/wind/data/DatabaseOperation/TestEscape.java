package com.wind.data.DatabaseOperation;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 1, 2014  2:17:14 PM
 *@Description
 */
public class TestEscape {

	@Test
	public void test() {
		String escape="。";
		System.out.println(escape);
		if(escape.matches("。"))
			System.out.println(escape);
		String join=" ";
		String assessment=null;
		join=join+assessment;
		System.out.println(join);
		String skill="[MS Office, PC/Desktop, Java, J2EE, C/C++, Flash, Adobe Photoshop, C#]";
		System.out.println(skill);
		System.out.println(skill.replaceAll("\\[|\\]", ""));
	}

}
