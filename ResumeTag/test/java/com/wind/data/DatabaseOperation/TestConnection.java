package com.wind.data.DatabaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.wind.data.utils.JDBCUtils;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 31, 2014  7:31:36 PM
 *@Description
 */
public class TestConnection {

	@Test
	public void test() throws SQLException {
		Connection connection=JDBCUtils.getLocalConnection();
		String insertStatement="insert into customers(first_name,last_name) values(?,?)";
		PreparedStatement preparedStatement=connection.prepareStatement(insertStatement);
		preparedStatement.setString(1, "liu");
		preparedStatement.setString(2, "feng");
		System.out.println(preparedStatement.executeUpdate());;
		preparedStatement.close();
	}

}
