package com.wind.data.DatabaseOperation;

import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.wind.dao.impl.ResumeDAO;
import com.wind.information.model.ResumeKeyword;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 11, 2014  2:48:09 PM
 *@Description
 */
public class TestResultMap {

	private static int dataLimit=500;
	
	@Test
	public void test() throws SQLException {
		ResumeDAO resumeDAO=new ResumeDAO();
		int maxID=resumeDAO.getMaxID();
		Map<ResumeKeyword, String> testMap;
		System.out.println(maxID);
		for(int index=0;index<=maxID;index+=dataLimit){
			testMap=resumeDAO.getNullTestResult(index, index+dataLimit);
			System.out.println(testMap.size());
			for(Entry<ResumeKeyword, String> entry:testMap.entrySet()){
				System.out.println(entry.getKey().getResumeId());
			}
		}
		System.out.println("end");
	}

}
