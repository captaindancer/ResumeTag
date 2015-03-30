package com.wind.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 23, 2014  1:25:39 PM
 *@Description
 */
public interface GenericDAO <T>{

	public void save(T t) throws SQLException;
	
	public void save(List<T> resumeList) throws SQLException;
	
	public void add(T resume);
	
	public List<T> query(String sqlStatement,Object ... args);
	
	public List<String> getField(String sqlStatement) throws SQLException;
	
	public int getMaxID();
	
	public List<T> getDomain(int startId, int endId) throws SQLException;
	
	public T getORM(Object obj) throws SQLException;
}
