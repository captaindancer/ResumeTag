package com.wind.data.DatabaseOperation;

import java.sql.SQLException;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 15, 2014  11:07:21 AM
 *@Description
 */
public class TestDataFetch {

	@Test
	public void test() throws SQLException {
		long start_time=System.currentTimeMillis();
		DataFetch dataFetch=new DataFetch();
//		String sqlStatement="select * from resume_tag where keyword is not null limit 1000";
//		dataFetch.readData(sqlStatement);
		String pageContent=dataFetch.getPagecontent(9);
		System.out.println(pageContent);
		System.out.println((System.currentTimeMillis()-start_time)+" ms");
		System.out.println(dataFetch.getMaxID());
	}

}
