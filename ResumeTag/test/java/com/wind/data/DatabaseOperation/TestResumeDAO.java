package com.wind.data.DatabaseOperation;

import java.sql.SQLException;

import org.junit.Test;

import com.wind.dao.impl.ResumeDAO;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 1, 2014  11:31:44 AM
 *@Description
 */

public class TestResumeDAO {

	@Test
	public void test() throws SQLException {
		ResumeDAO resumeDAO=new ResumeDAO();
		String keywordSQL="select keyword from extraction where keyword is not null";
		int amount=resumeDAO.getField(keywordSQL).size();
		System.out.println(amount);
		for(String str:resumeDAO.getField(keywordSQL)){
			System.out.println(str);
		}
		System.out.println(resumeDAO.getMaxID());
	}

}
