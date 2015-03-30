package com.wind.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wind.dao.GenericDAO;
import com.wind.dao.impl.ResumeDAO;
import com.wind.data.utils.JDBCUtils;
import com.wind.information.model.Resume;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 15, 2014  10:14:39 AM
 *@Description
 */
public class DataSync {
    private static Connection srcConnection;
    private static PreparedStatement srcPreparedStatement;
    private static ResultSet srcResultSet;
    
    private static final int dataLimit=500;
    
    private ResumeDAO resumeDAO;
    
    public DataSync(){
    	resumeDAO=new ResumeDAO();
    }
    
    static{
    	try {
			srcConnection=JDBCUtils.getConnection();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("can't get the connection");
		}
    }
	
    public List<Resume> readSrc(int startId,int endId) throws SQLException{
    	List<Resume> resumeList=new ArrayList<Resume>(dataLimit);
    	String sqlStatement="select resumeId,keyword,sex,birthday,residence,recentCompany,profession,position,degree,major,university,self_assessment,duty_time,industry_expected,job_specification,location_target,expected_salary,goal_function,job_status,work_experience,brief_work_experience,worked_title,project,brief_project,edu_experience,brief_edu_experience,it_skill from fullresume where id>=? and id<?";
    	srcPreparedStatement=srcConnection.prepareStatement(sqlStatement);
    	srcPreparedStatement.setInt(1, startId);
    	srcPreparedStatement.setInt(2, endId);
    	srcResultSet=srcPreparedStatement.executeQuery();
    	while(srcResultSet.next()){
    		resumeList.add(getORM(srcResultSet));
    	}
    	JDBCUtils.free(srcResultSet, srcPreparedStatement, null);
    	return resumeList;
    }
    
    public int getSrcMaxID() throws SQLException{
    	int maxID=0;
    	String sqlStatement="select max(id) from fullresume";
    	srcPreparedStatement=srcConnection.prepareStatement(sqlStatement);
    	srcResultSet=srcPreparedStatement.executeQuery();
    	while(srcResultSet.next()){
    		maxID=srcResultSet.getInt(1);
    	}
    	return maxID;
    }
    
    public void syncData(List<Resume> resumeList){
    	resumeDAO.save(resumeList);
    }
    
    public Resume getORM(Object obj) throws SQLException {
		Resume resume=new Resume();
		List<String> ormList;
		if(obj!=null){
			ResultSet resultSet=(ResultSet) obj;
			resume.setResumeId(resultSet.getString("resumeID"));
			resume.setKeyword(resultSet.getString("keyword"));
			resume.setSex(resultSet.getString("sex"));
			resume.setBirthday(resultSet.getString("birthday"));
			resume.setResidence(resultSet.getString("residence"));
			resume.setRecentCompany(resultSet.getString("recentCompany"));
			resume.setProfession(resultSet.getString("profession"));
			resume.setPosition(resultSet.getString("position"));
			resume.setDegree(resultSet.getString("degree"));
			resume.setMajor(resultSet.getString("major"));
			resume.setUniversity(resultSet.getString("university"));
			resume.setSelf_assessment(resultSet.getString("self_assessment"));
			resume.setDuty_time(resultSet.getString("duty_time"));
			resume.setIndustry_expected(resultSet.getString("industry_expected"));
			resume.setJob_specification(resultSet.getString("job_specification"));
			resume.setLocation_target(resultSet.getString("location_target"));
			resume.setExpected_salary(resultSet.getString("expected_salary"));
			resume.setGoal_function(resultSet.getString("goal_function"));
			resume.setJob_status(resultSet.getString("job_status"));
			resume.setWork_Experience(resultSet.getString("work_experience"));
			resume.setBrief_work_experience(resultSet.getString("brief_work_experience"));
			String workTitle=resultSet.getString("worked_title");
			if(workTitle!="[]"){
				workTitle=workTitle.replaceAll("\\[", "");
				workTitle=workTitle.replaceAll("\\]", "");
			}else{
				workTitle=null;
			}
			ormList=new ArrayList<String>();
			ormList.add(workTitle);
			resume.setWorked_title(ormList);
			resume.setProject(resultSet.getString("project"));
			resume.setBrief_project(resultSet.getString("brief_project"));
			resume.setEdu_experience(resultSet.getString("edu_experience"));
			resume.setBrief_edu_experience(resultSet.getString("brief_edu_experience"));
			String itSkill=resultSet.getString("it_skill");
			if(itSkill!="[]"){
				itSkill=itSkill.replaceAll("\\[", "");
				itSkill=itSkill.replaceAll("\\]", "");
			}else{
				itSkill=null;
			}
			ormList=new ArrayList<String>();
			ormList.add(itSkill);
			resume.setIt_skill(ormList);
		}
		return resume;
	}
    
	public static void main(String[] args) throws SQLException {
		DataSync dataSync=new DataSync();
		int maxID=dataSync.getSrcMaxID();
		for(int index=0;index<=maxID;index+=dataLimit){
			dataSync.syncData(dataSync.readSrc(index, index+dataLimit));
		}
	}

}
