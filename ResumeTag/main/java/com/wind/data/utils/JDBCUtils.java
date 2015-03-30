package com.wind.data.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 15, 2014  10:24:48 AM
 *@Description
 */

public final class JDBCUtils {

//	可以通过在配置文件中设置驱动来达到自由使用不同数据库
	private static String driverClassName="com.mysql.jdbc.Driver";
	private static String url;
	private static String username;
	private static String password;
	private static String localurl;
	private static String localusername;
	private static String localpassword;

	static {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private JDBCUtils() {
		
		try {
			Properties properties = new Properties();

			InputStream inputStream =JDBCUtils.class.getResourceAsStream(
					"/jdbc.properties");
			properties.load(inputStream);
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			localurl=properties.getProperty("localurl");
			localusername=properties.getProperty("localusername");
			localpassword=properties.getProperty("localpassword");
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		new JDBCUtils();
		return DriverManager.getConnection(url, username, password);
	}
	
	public static Connection getLocalConnection() throws SQLException{
		new JDBCUtils();
		return DriverManager.getConnection(localurl, localusername, localpassword);
	}

	public static void free(ResultSet resultSet, Statement statement,
			Connection connection) {
		try {
			if(resultSet!=null){
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null){
					statement.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try {
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
