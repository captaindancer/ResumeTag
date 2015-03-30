package com.wind.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.wind.data.utils.JDBCUtils;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Oct 28, 2014  6:01:06 PM
 *@Description
 */
public class TestNull {

	@Test
	public void test() throws SQLException {
		Connection connection=JDBCUtils.getConnection();
		
	String sqlStatement="select id,resumeId,keyword,sex from test";
	PreparedStatement preparedStatement=connection.prepareStatement(sqlStatement);
	ResultSet resultSet=preparedStatement.executeQuery();
	while(resultSet.next()){
		if(resultSet.getString(3)!=null){
			System.out.println(resultSet.getInt(1));
			System.out.println(resultSet.getString(2));
			System.out.println(resultSet.getString(3));
			System.out.println(resultSet.getString(4));
		}
	}
	JDBCUtils.free(resultSet, preparedStatement, connection);
	}
}
