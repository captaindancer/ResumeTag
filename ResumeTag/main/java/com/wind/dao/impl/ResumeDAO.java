package com.wind.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.wind.dao.GenericDAO;
import com.wind.data.utils.JDBCUtils;
import com.wind.information.model.Resume;
import com.wind.information.model.ResumeKeyword;

/**
 * @author liufeng E-mail:fliu.Dancer@wind.com.cn
 * @version Time:Jul 23, 2014 1:29:56 PM
 * @Description
 */

@Repository("MySQL")
public class ResumeDAO implements GenericDAO<Resume> {

	private static final Logger LOGGER=LogManager.getLogger(ResumeDAO.class);
	
	private static Connection connection;
	private PreparedStatement preparedStatement;
	private List<String> ormList;

	static {
		try {
			connection = JDBCUtils.getLocalConnection();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public void save(Resume resume) throws SQLException {
		String insertStatement = "insert into fullresume(resumeId,keyword,sex,birthday,residence,recentCompany,profession,position,degree,major,university,self_assessment,duty_time,industry_expected,job_specification,location_target,expected_salary,goal_function,job_status,work_experience,brief_work_experience,worked_title,project,brief_project,edu_experience,brief_edu_experience,it_skill) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		preparedStatement=connection.prepareStatement(insertStatement);
		if(resume!=null){
			preparedStatement.setString(1, resume.getResumeId());
			preparedStatement.setString(2, resume.getKeyword());
			preparedStatement.setString(3, resume.getSex());
			preparedStatement.setString(4, resume.getBirthday());
			preparedStatement.setString(5, resume.getResidence());
			preparedStatement.setString(6, resume.getRecentCompany());
			preparedStatement.setString(7, resume.getProfession());
			preparedStatement.setString(8, resume.getPosition());
			preparedStatement.setString(9, resume.getDegree());
			preparedStatement.setString(10, resume.getMajor());
			preparedStatement.setString(11, resume.getUniversity());
			preparedStatement.setString(12, resume.getSelf_assessment());
			preparedStatement.setString(13, resume.getDuty_time());
			preparedStatement.setString(14, resume.getIndustry_expected());
			preparedStatement.setString(15, resume.getJob_specification());
			preparedStatement.setString(16, resume.getLocation_target());
			preparedStatement.setString(17, resume.getExpected_salary());
			preparedStatement.setString(18, resume.getGoal_function());
			preparedStatement.setString(19, resume.getJob_status());
			preparedStatement.setString(20, resume.getWork_Experience());
			preparedStatement.setString(21, resume.getBrief_work_experience());
			preparedStatement.setString(22, resume.getWorked_title().toString());
			preparedStatement.setString(23, resume.getProject());
			preparedStatement.setString(24, resume.getBrief_project());
			preparedStatement.setString(25, resume.getEdu_experience());
			preparedStatement.setString(26, resume.getBrief_edu_experience());
			preparedStatement.setString(27, resume.getIt_skill().toString());
			preparedStatement.executeUpdate();
		}
		JDBCUtils.free(null, preparedStatement, null);
	}

	@Override
	public void add(Resume resume) {
		
	}

	@Override
	public List<Resume> query(String sqlStatement, Object... args) {
		return null;
	}

	@Override
	public void save(List<Resume> resumeList) {
		try {
			connection.setAutoCommit(false);
			String insertStatement = "insert into fullresume(resumeId,keyword,sex,birthday,residence,recentCompany,profession,position,degree,major,university,self_assessment,duty_time,industry_expected,job_specification,location_target,expected_salary,goal_function,job_status,work_experience,brief_work_experience,worked_title,project,brief_project,edu_experience,brief_edu_experience,it_skill) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//			LOGGER.info(insertStatement);
			preparedStatement = connection.prepareStatement(insertStatement);
//			int count=1;
			for (Resume resume : resumeList) {
				if(resume!=null){
					System.out.println(resume.getResumeId());
					preparedStatement.setString(1, resume.getResumeId());
					preparedStatement.setString(2, resume.getKeyword());
					preparedStatement.setString(3, resume.getSex());
					preparedStatement.setString(4, resume.getBirthday());
					preparedStatement.setString(5, resume.getResidence());
					preparedStatement.setString(6, resume.getRecentCompany());
					preparedStatement.setString(7, resume.getProfession());
					preparedStatement.setString(8, resume.getPosition());
					preparedStatement.setString(9, resume.getDegree());
					preparedStatement.setString(10, resume.getMajor());
					preparedStatement.setString(11, resume.getUniversity());
					preparedStatement.setString(12, resume.getSelf_assessment());
					preparedStatement.setString(13, resume.getDuty_time());
					preparedStatement.setString(14, resume.getIndustry_expected());
					preparedStatement.setString(15, resume.getJob_specification());
					preparedStatement.setString(16, resume.getLocation_target());
					preparedStatement.setString(17, resume.getExpected_salary());
					preparedStatement.setString(18, resume.getGoal_function());
					preparedStatement.setString(19, resume.getJob_status());
					preparedStatement.setString(20, resume.getWork_Experience());
					preparedStatement.setString(21, resume.getBrief_work_experience());
					preparedStatement.setString(22, resume.getWorked_title().toString());
					preparedStatement.setString(23, resume.getProject());
					preparedStatement.setString(24, resume.getBrief_project());
					preparedStatement.setString(25, resume.getEdu_experience());
					preparedStatement.setString(26, resume.getBrief_edu_experience());
					preparedStatement.setString(27, resume.getIt_skill().toString());
					preparedStatement.addBatch();
				}
//				LOGGER.info(count++);
			}
//			LOGGER.info("reach here");
			LOGGER.info("It processes:"+preparedStatement.executeBatch().length);;
			connection.commit();
//			LOGGER.info("*******************");
		} catch (SQLException e) {
			try {
				LOGGER.error("something error!rollback!");
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JDBCUtils.free(null, preparedStatement, null);
		}
	}

	@Override
	public List<String> getField(String sqlStatement) throws SQLException {
		preparedStatement=connection.prepareStatement(sqlStatement);
		ResultSet resultSet=preparedStatement.executeQuery();
		List<String> list=new ArrayList<String>();
		while(resultSet.next()){
			list.add(resultSet.getString(1));
		}
		JDBCUtils.free(resultSet, preparedStatement, null);
		return list;
	}
	
	/**
	 * 为了防止数据量过大而导致map容器撑爆,每次从数据库中取出一段区间的数据
	 * @param start_id  where条件中开始id
	 * @param end_id  where条件中结束id
	 * @return 返回关键字不为空和自我评价不为空的简历数据
	 * @throws SQLException
	 */
	public Map<ResumeKeyword, String> getFormatResult(int start_id,int end_id) throws SQLException{
		Map<ResumeKeyword, String> resultMap=new HashMap<ResumeKeyword, String>(5000);
		ResumeKeyword resumeKeyword;
		String assessment;
		String experience;
		String project;
		String education;
		String skill;
		String sqlStatement="select keyword,self_assessment,brief_work_experience,brief_project,brief_edu_experience,it_skill from fullresume where keyword is not null and self_assessment is not null and id >= ? and id < ?";
		preparedStatement=connection.prepareStatement(sqlStatement);
		preparedStatement.setInt(1, start_id);
		preparedStatement.setInt(2, end_id);
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()){
			resumeKeyword=new ResumeKeyword(resultSet.getString("keyword"));
			assessment=resultSet.getString("self_assessment");
			experience=resultSet.getString("brief_work_experience");
			project=resultSet.getString("brief_project");
			education=resultSet.getString("brief_edu_experience");
			skill=resultSet.getString("it_skill");
			String join="";
			if(assessment!=null){
				join=join+assessment;
			}
			if(experience!=null){
				join=join+" "+experience;
			}
			if(project!=null){
				join=join+" "+project;
			}
			if(education!=null){
				join=join+" "+education;
			}
			if(!skill.equals("[]")){
				skill=skill.replaceAll("\\[|\\]", "");
				join=join+" "+skill;
			}
			resultMap.put(resumeKeyword, join);
		}
		return resultMap;
	}

	public Map<ResumeKeyword, String> getTestResult(int start_id,int end_id) throws SQLException{
		Map<ResumeKeyword, String> resultMap=new LinkedHashMap<ResumeKeyword, String>(5000);
		ResumeKeyword resumeKeyword;
		String assessment;
		String experience;
		String project;
		String education;
		String skill;
		String sqlStatement="select resumeId,keyword,self_assessment,brief_work_experience,brief_project,brief_edu_experience,it_skill from fullresume where keyword is not null and self_assessment is not null and id >= ? and id < ?";
		preparedStatement=connection.prepareStatement(sqlStatement);
		preparedStatement.setInt(1, start_id);
		preparedStatement.setInt(2, end_id);
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()){
			resumeKeyword=new ResumeKeyword(resultSet.getString("resumeId"),resultSet.getString("keyword"));
			assessment=resultSet.getString("self_assessment");
			experience=resultSet.getString("brief_work_experience");
			project=resultSet.getString("brief_project");
			education=resultSet.getString("brief_edu_experience");
			skill=resultSet.getString("it_skill");
			String join="";
			if(assessment!=null){
				join=join+assessment;
			}
			if(experience!=null){
				join=join+" "+experience;
			}
			if(project!=null){
				join=join+" "+project;
			}
			if(education!=null){
				join=join+" "+education;
			}
			if(!skill.equals("[]")){
				skill=skill.replaceAll("\\[|\\]", "");
				join=join+" "+skill;
			}
			resultMap.put(resumeKeyword, join);
		}
		return resultMap;
	}
	
	public Map<ResumeKeyword, String> getNullTestResult(int start_id,int end_id) throws SQLException{
		Map<ResumeKeyword, String> resultMap=new LinkedHashMap<ResumeKeyword, String>(5000);
		ResumeKeyword resumeKeyword;
		String assessment;
		String experience;
		String project;
		String education;
		String skill;
		String sqlStatement="select resumeId,keyword,self_assessment,brief_work_experience,brief_project,brief_edu_experience,it_skill from fullresume where keyword is null and self_assessment is not null and id >= ? and id < ?";
		preparedStatement=connection.prepareStatement(sqlStatement);
		preparedStatement.setInt(1, start_id);
		preparedStatement.setInt(2, end_id);
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()){
			resumeKeyword=new ResumeKeyword(resultSet.getString("resumeId"),resultSet.getString("keyword"));
			assessment=resultSet.getString("self_assessment");
			experience=resultSet.getString("brief_work_experience");
			project=resultSet.getString("brief_project");
			education=resultSet.getString("brief_edu_experience");
			skill=resultSet.getString("it_skill");
			String join="";
			if(assessment!=null){
				join=join+assessment;
			}
			if(experience!=null){
				join=join+" "+experience;
			}
			if(project!=null){
				join=join+" "+project;
			}
			if(education!=null){
				join=join+" "+education;
			}
			if(!skill.equals("[]")){
				skill=skill.replaceAll("\\[|\\]", "");
				join=join+" "+skill;
			}
			resultMap.put(resumeKeyword, join);
		}
		return resultMap;
	}
	
	public Map<ResumeKeyword, String> getTestResult(int index) throws SQLException{
		Map<ResumeKeyword, String> resultMap=new LinkedHashMap<ResumeKeyword, String>(5000);
		ResumeKeyword resumeKeyword;
		String assessment;
		String experience;
		String project;
		String education;
		String skill;
		String sqlStatement="select resumeId,keyword,self_assessment,brief_work_experience,brief_project,brief_edu_experience,it_skill from fullresume where id= ?";
		preparedStatement=connection.prepareStatement(sqlStatement);
		preparedStatement.setInt(1, index);
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()){
			resumeKeyword=new ResumeKeyword(resultSet.getString("keyword"));
			assessment=resultSet.getString("self_assessment");
			experience=resultSet.getString("brief_work_experience");
			project=resultSet.getString("brief_project");
			education=resultSet.getString("brief_edu_experience");
			skill=resultSet.getString("it_skill");
			String join="";
			if(assessment!=null){
				join=join+assessment;
			}
			if(experience!=null){
				join=join+" "+experience;
			}
			if(project!=null){
				join=join+" "+project;
			}
			if(education!=null){
				join=join+" "+education;
			}
			if(!skill.equals("[]")){
				skill=skill.replaceAll("\\[|\\]", "");
				join=join+" "+skill;
			}
			resultMap.put(resumeKeyword, join);
		}
		return resultMap;
	}
	
	@Override
	public int getMaxID() {
		int maxID=0;
		String sqlStatement="select max(id) from fullresume";
		try {
			preparedStatement=connection.prepareStatement(sqlStatement);
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("can't create the statement ");
		}
		ResultSet resultSet;
		try {
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("can't execute the query");
		}
		try {
			if(resultSet.next()){
				maxID=resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("can't fetch the resultset");
		}
		return maxID;
	}

	public int getNewMaxID(){
		int maxID=0;
		String sqlStatement="select max(id) from validresume";
		try {
			preparedStatement=connection.prepareStatement(sqlStatement);
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("can't create the statement ");
		}
		ResultSet resultSet;
		try {
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("can't execute the query");
		}
		try {
			if(resultSet.next()){
				maxID=resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("can't fetch the resultset");
		}
		return maxID;
	}
	
	public void generateTable(List<Resume> resumeList){
		try {
			connection.setAutoCommit(false);
			String insertStatement = "insert into allinresume(resumeId,keyword,sex,birthday,residence,recentCompany,profession,position,degree,major,university,self_assessment,duty_time,industry_expected,job_specification,location_target,expected_salary,goal_function,job_status,work_experience,brief_work_experience,worked_title,project,brief_project,edu_experience,brief_edu_experience,it_skill) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			LOGGER.info(insertStatement);
			preparedStatement = connection.prepareStatement(insertStatement);
//			int count=1;
			for (Resume resume : resumeList) {
				if(resume!=null){
					System.out.println(resume.getResumeId());
					preparedStatement.setString(1, resume.getResumeId());
					preparedStatement.setString(2, resume.getKeyword());
					preparedStatement.setString(3, resume.getSex());
					preparedStatement.setString(4, resume.getBirthday());
					preparedStatement.setString(5, resume.getResidence());
					preparedStatement.setString(6, resume.getRecentCompany());
					preparedStatement.setString(7, resume.getProfession());
					preparedStatement.setString(8, resume.getPosition());
					preparedStatement.setString(9, resume.getDegree());
					preparedStatement.setString(10, resume.getMajor());
					preparedStatement.setString(11, resume.getUniversity());
					preparedStatement.setString(12, resume.getSelf_assessment());
					preparedStatement.setString(13, resume.getDuty_time());
					preparedStatement.setString(14, resume.getIndustry_expected());
					preparedStatement.setString(15, resume.getJob_specification());
					preparedStatement.setString(16, resume.getLocation_target());
					preparedStatement.setString(17, resume.getExpected_salary());
					preparedStatement.setString(18, resume.getGoal_function());
					preparedStatement.setString(19, resume.getJob_status());
					preparedStatement.setString(20, resume.getWork_Experience());
					preparedStatement.setString(21, resume.getBrief_work_experience());
					preparedStatement.setString(22, resume.getWorked_title().toString());
					preparedStatement.setString(23, resume.getProject());
					preparedStatement.setString(24, resume.getBrief_project());
					preparedStatement.setString(25, resume.getEdu_experience());
					preparedStatement.setString(26, resume.getBrief_edu_experience());
					preparedStatement.setString(27, resume.getIt_skill().toString());
					preparedStatement.addBatch();
				}
//				LOGGER.info(count++);
			}
//			LOGGER.info("reach here");
			LOGGER.info("It processes:"+preparedStatement.executeBatch().length);;
			connection.commit();
//			LOGGER.info("*******************");
		} catch (SQLException e) {
			try {
				LOGGER.error("something error!rollback!");
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JDBCUtils.free(null, preparedStatement, null);
		}
	}

	@Override
	public List<Resume> getDomain(int startId, int endId) throws SQLException {
		List<Resume> resumeList=new ArrayList<Resume>();
		String sqlStatement="select resumeId,keyword,sex,birthday,residence,recentCompany,profession,position,degree,major,university,self_assessment,duty_time,industry_expected,job_specification,location_target,expected_salary,goal_function,job_status,work_experience,brief_work_experience,worked_title,project,brief_project,edu_experience,brief_edu_experience,it_skill from fullresume where id>=? and id<?";
		preparedStatement=connection.prepareStatement(sqlStatement);
		preparedStatement.setInt(1, startId);
		preparedStatement.setInt(2, endId);
		ResultSet resultSet=preparedStatement.executeQuery();
		String keyword;
		String assessment;
		String experience;
		String project;
		String education;
		String skill;
		while(resultSet.next()){
			keyword=resultSet.getString("keyword");
			assessment=resultSet.getString("self_assessment");
			experience=resultSet.getString("brief_work_experience");
			project=resultSet.getString("brief_project");
			education=resultSet.getString("brief_edu_experience");
			skill=resultSet.getString("it_skill");
			String join="";
			if(assessment!=null){
				join=join+assessment;
			}
			if(experience!=null){
				join=join+" "+experience;
			}
			if(project!=null){
				join=join+" "+project;
			}
			if(education!=null){
				join=join+" "+education;
			}
			if(!skill.equals("[]")){
				skill=skill.replaceAll("\\[|\\]", "");
				join=join+" "+skill;
			}
			if(keyword!=null){
				for(String str:keyword.split(" ")){
					if(join.contains(str)){
						Resume resume=getORM(resultSet);
						resumeList.add(resume);
						break;
					}
				}
			}
		}
		return resumeList;
	}

	public List<Resume> getAllIn(int startId, int endId) throws SQLException {
		List<Resume> resumeList=new ArrayList<Resume>();
		String sqlStatement="select resumeId,keyword,sex,birthday,residence,recentCompany,profession,position,degree,major,university,self_assessment,duty_time,industry_expected,job_specification,location_target,expected_salary,goal_function,job_status,work_experience,brief_work_experience,worked_title,project,brief_project,edu_experience,brief_edu_experience,it_skill from fullresume where id>=? and id<?";
		preparedStatement=connection.prepareStatement(sqlStatement);
		preparedStatement.setInt(1, startId);
		preparedStatement.setInt(2, endId);
		ResultSet resultSet=preparedStatement.executeQuery();
		String keyword;
		String assessment;
		String experience;
		String project;
		String education;
		String skill;
		while(resultSet.next()){
			keyword=resultSet.getString("keyword");
			assessment=resultSet.getString("self_assessment");
			experience=resultSet.getString("brief_work_experience");
			project=resultSet.getString("brief_project");
			education=resultSet.getString("brief_edu_experience");
			skill=resultSet.getString("it_skill");
			String join="";
			if(assessment!=null){
				join=join+assessment;
			}
			if(experience!=null){
				join=join+" "+experience;
			}
			if(project!=null){
				join=join+" "+project;
			}
			if(education!=null){
				join=join+" "+education;
			}
			if(!skill.equals("[]")){
				skill=skill.replaceAll("\\[|\\]", "");
				join=join+" "+skill;
			}
			if(keyword!=null){
				int count=0;
				for(String str:keyword.split(" ")){
					if(join.contains(str)){
						count++;
						break;
					}
				}
				if(count==keyword.split(" ").length){
					Resume resume=getORM(resultSet);
					resumeList.add(resume);
				}
			}
		}
		return resumeList;
	}
	
	@Override
	public Resume getORM(Object obj) throws SQLException {
		Resume resume=new Resume();
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
	
}
