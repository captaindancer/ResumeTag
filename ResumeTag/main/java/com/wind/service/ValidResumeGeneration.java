package com.wind.service;

import java.sql.SQLException;
import java.util.List;

import com.wind.dao.impl.ResumeDAO;
import com.wind.information.model.Resume;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 11, 2014  10:38:53 AM
 *@Description
 */
public class ValidResumeGeneration {

	private ResumeDAO resumeDAO=new ResumeDAO();
	private static final int dataLimit=500;
	
	public void generateValidResume() throws SQLException{
		int maxID=resumeDAO.getMaxID();
		List<Resume> resumeList;
		for(int index=0;index<=maxID;index+=dataLimit){
			resumeList=resumeDAO.getDomain(index, index+dataLimit);
			resumeDAO.generateTable(resumeList);
		}
	}
	
	public void generateAllInResume() throws SQLException{
		int maxID=resumeDAO.getMaxID();
		List<Resume> resumeList;
		for(int index=0;index<=maxID;index+=dataLimit){
			resumeList=resumeDAO.getAllIn(index, index+dataLimit);
			resumeDAO.generateTable(resumeList);
		}
	}
	
	public static void main(String[] args) throws SQLException {
		ValidResumeGeneration validResumeGeneration=new ValidResumeGeneration();
		validResumeGeneration.generateAllInResume();
	}
}
