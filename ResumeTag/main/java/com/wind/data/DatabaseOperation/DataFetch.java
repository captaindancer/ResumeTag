package com.wind.data.DatabaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import com.wind.data.utils.JDBCUtils;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 15, 2014  10:41:57 AM
 *@Description
 */

public class DataFetch {

	private static Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	static{
		try {
			connection=JDBCUtils.getConnection();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public void readData(String sqlStatement) throws SQLException{
		try {
			preparedStatement=connection.prepareStatement(sqlStatement);
			resultSet=preparedStatement.executeQuery();
			int id;
			String year;
			String industry;
			String position;
			String resumeid;
			String keyword;
			String pageContent;
			while(resultSet.next()){
				id=resultSet.getInt("id");
				year=resultSet.getString("year");
				industry=resultSet.getString("industry");
				position=resultSet.getString("position");
				resumeid=resultSet.getString("resumeid");
				keyword=resultSet.getString("keyword");
				pageContent=resultSet.getString("pageContent");
				System.out.println(id);
				System.out.println(year);
				System.out.println(industry);
				System.out.println(position);
				System.out.println(resumeid);
				System.out.println(keyword);
				System.out.println(pageContent);
				System.out.println("-------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtils.free(resultSet, preparedStatement, connection);
		}
	}
	
	public String getPagecontent(int id) throws SQLException{
		String sqlStatement="select pageContent from resume_tag where id=?";
		preparedStatement=connection.prepareStatement(sqlStatement);
//		占位符从1开始而不是0
		preparedStatement.setInt(1, id);
		resultSet=preparedStatement.executeQuery();
		String pageContent=null;
		while(resultSet.next()){
			pageContent=resultSet.getString("pageContent");
		}
		JDBCUtils.free(resultSet, preparedStatement, null);
		return pageContent;
	}
	
	public Map<Integer, String> getPagecontentMap(int start_id,int end_id) throws SQLException{
		Map<Integer,String> pagecontentMap=new TreeMap<Integer, String>();
		String sqlStatement="select id,pageContent from resume_tag where id >= ? and id < ?";
		connection.setAutoCommit(false);
		preparedStatement=connection.prepareStatement(sqlStatement);
		preparedStatement.setInt(1, start_id);
		preparedStatement.setInt(2, end_id);
		resultSet=preparedStatement.executeQuery();
		connection.commit();
		while(resultSet.next()){
			pagecontentMap.put(resultSet.getInt("id"), resultSet.getString("pageContent"));
		}
		JDBCUtils.free(resultSet, preparedStatement, null);
		return pagecontentMap;
	}
	
	public String getResumeId(int id) throws SQLException{
		String sqlStatement="select resumeid from resume_tag where id=?";
		preparedStatement=connection.prepareStatement(sqlStatement);
		preparedStatement.setInt(1, id);
		resultSet=preparedStatement.executeQuery();
		String resumeId=null;
		while(resultSet.next()){
			resumeId=resultSet.getString("resumeid");
		}
		JDBCUtils.free(resultSet, preparedStatement, null);
		return resumeId;
	}
	
	public int getMaxID() throws SQLException{
		int maxID=0;
		String sqlStatement="select max(id) from resume_tag";
		preparedStatement=connection.prepareStatement(sqlStatement);
		resultSet=preparedStatement.executeQuery();
		if(resultSet.next()){
			maxID=resultSet.getInt(1);
		}
		return maxID;
	}
	
	public void free(){
		JDBCUtils.free(resultSet, preparedStatement, connection);
	}
}
