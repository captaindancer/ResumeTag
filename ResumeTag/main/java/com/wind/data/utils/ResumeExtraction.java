package com.wind.data.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.wind.data.DatabaseOperation.DataFetch;
import com.wind.information.model.Resume;

/**
 * @author liufeng E-mail:fliu.Dancer@wind.com.cn
 * @version Time:Jul 29, 2014 9:43:25 AM
 * @Description
 */

@Repository("Resume")
public class ResumeExtraction implements GenericExtraction<Resume> {

	private static final Logger LOGGER=LogManager.getLogger(ResumeExtraction.class);
	
	private RegExp regExp = new RegExp();
	private static DataFetch dataFetch = new DataFetch();
	private String start_str = "<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">";
	private Pattern corpPattern=Pattern.compile(".+/\\d+--[\\s\\S]+");
	private Matcher corpMatcher;
	private List<String> corpList;
	private List<String> devTools;
	private List<String> responDescs;

	@Override
	public Resume getExtraction(String content, int primaryKey) {
		Resume resume = null;
		int begin_index = -1;
		if (content != null) {
			begin_index = content.indexOf(start_str);
		}
		if (begin_index != -1) {
			resume=new Resume();
			String resumeID = null; // 简历ID
			String basic = null;
			String keyword = null; // 简历关键字
			String sex = null; // 性别
			String birthday = null; // 出身年月
			String residence = null; // 居住地
			String recentCompany = null; // 最近公司
			String profession = null; // 行业
			String position = null; // 职业
			String degree = null; // 学历
			String major = null; // 专业
			String university = null; // 学校
			String full_infor = null;
			String self_assessment = null; // 自我评价
			String duty_time = null; // 到岗时间
			String job_specification = null; // 工作性质
			String industry_expected = null; // 期望行业
			String location_target = null; // 目标地点
			String expected_salary = null; // 期望月薪
			String goal_function = null; // 目标职能
			String job_status = null; // 求职状态
			String work_experience = null; // 工作经验
			String brief_work_experience=null;  //简明工作经验
			List<String> worked_title=new ArrayList<String>();  //工作职位
			String project = null; // 项目经验
			String brief_project=null;  //简明项目经验
			String edu_experience = null; // 教育经历
			String brief_education=null; //简明教育经历
			List<String> it_skill = new ArrayList<String>(); // it技能

			/*
			 * String resumeID_RegExp=regExp.getAssertion(
			 * "<span style=\"font-size:25px;height:30px;line-height:30px;\"><b>"
			 * , "</b></span>"); resumeID=regExp.getGroup(resumeID_RegExp,
			 * content).trim(); if(resumeID!=null){
			 * resumeID=resumeID.substring(resumeID.indexOf(":")+1); }
			 */
			try {
				resumeID = dataFetch.getResumeId(primaryKey);
			} catch (SQLException e) {
				System.out.println("resumeID error!");
				throw new ExceptionInInitializerError(e);
			}
			System.out.println(resumeID);
			resume.setResumeId(resumeID); // 设置resumeID
			String keyword_RegExp = regExp.getAssertion("<span id=\"spanTitled\">", "</span>");
			keyword = regExp.getGroup(keyword_RegExp, content);
			resume.setKeyword(keyword); // 设置关键词
			String basic_RegExp = regExp.getAssertion("<span class=\"blue\"><b>", "</b></span>");
			basic = regExp.getGroup(basic_RegExp, content);
			if (basic != null) {
				if (basic.contains("男")) {
					sex = "男";
				} else {
					sex = "女";
				}
				int start_index = basic.indexOf("（");
				int end_index = basic.indexOf("）");
				if (start_index != -1 && end_index != -1) {
					birthday = basic.substring(start_index + 1, end_index);
				}
			}
			resume.setSex(sex);
			resume.setBirthday(birthday);
			String residence_RegExp = regExp.getAssertion("居住地：</td><td width=\"42%\" height=\"20\">", "</td>");
			residence = regExp.getGroup(residence_RegExp, content);
			resume.setResidence(residence);
			String job_RegExp = regExp
					.getAssertion(
							"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:8px auto;line-height:22px;padding:0 0 0 8px;\">",
							"</table>");
			String job_detail = null;
			job_detail = regExp.getGroup(job_RegExp, content);
			if (job_detail != null && job_detail.contains("公　司：")) {
				String company_RegExp = regExp.getAssertion("<td width=\"230\">", "</td>");
				recentCompany = regExp.getGroup(company_RegExp, job_detail);
				String profession_RegExp = regExp.getAssertion("</td><td>", "</td></tr><tr><td>");
				profession = regExp.getGroup(profession_RegExp, job_detail);
				String positionStr = job_detail.substring(job_detail.indexOf("职　位："));
				String position_RegExp = regExp.getAssertion("</td><td>", "</td></tr>");
				position = regExp.getGroup(position_RegExp, positionStr);
			}
			resume.setRecentCompany(recentCompany);
			resume.setProfession(profession);
			resume.setPosition(position);
			String education_RegExp = regExp
					.getAssertion(
							"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:8px auto;line-height:22px;padding:0 0 0 10px;\">",
							"</table>");
			String education_detail = null;
			education_detail = regExp.getGroup(education_RegExp, content);
			if (education_detail != null) {
				String degree_RegExp = regExp.getAssertion("<td width=\"230\">", "</td>");
				degree = regExp.getGroup(degree_RegExp, education_detail);
				resume.setDegree(degree);
				String major_RegExp = regExp.getAssertion("</td><td>", "</td></tr><tr>");
				major = regExp.getGroup(major_RegExp, education_detail);
				resume.setMajor(major);
				if (major == null && degree == null) {

				} else {
					if (education_detail.indexOf("学　校：") != -1) {
						String universityStr = education_detail.substring(education_detail.indexOf("学　校："));
						String university_RegExp = regExp.getAssertion("</td><td>", "</td></tr>");
						university = regExp.getGroup(university_RegExp, universityStr);
					}
					resume.setUniversity(university);
				}
			}
			String fullInfor_RegExp = regExp.getGreedyAssertion(
					"<table width=\"97%\" border=\"0\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\">", "</table></td></tr>");
			full_infor = regExp.getGroup(fullInfor_RegExp, content);
			if (full_infor != null) {
				if (full_infor.contains("自我评价")) {
					String assessment_RegExp = regExp.getAssertion("<td align=\"left\" valign=\"middle\" class=\"text_left\"><span class=\"text\">","</span>");
					self_assessment = regExp.getGroup(assessment_RegExp, full_infor);
					if (self_assessment != null) {
						self_assessment = self_assessment.replaceAll("<\\S+?>", "");
					}
					resume.setSelf_assessment(self_assessment);
				}
				if (full_infor.contains("到岗时间：")) {
					String dutytime_RegExp = regExp.getAssertion("                          到岗时间：                          <span class=\"text\" style=\"padding-left:10px;\">", "</span>");
					duty_time = regExp.getGroup(dutytime_RegExp, full_infor);
					resume.setDuty_time(duty_time);
				}
				if (full_infor.contains("工作性质：")) {
					String jobspecification_RegExp = regExp.getAssertion("                          工作性质：                          <span class=\"text\" style=\"padding-left:10px;\">", "</span>");
					job_specification = regExp.getGroup(jobspecification_RegExp, full_infor);
					resume.setJob_specification(job_specification);
				}
				if (full_infor.contains("希望行业：")) {
					String industryexpected_RegExp = regExp.getAssertion("                          希望行业：                          <span class=\"text\" style=\"padding-left:10px;\">", "</span>");
					industry_expected = regExp.getGroup(industryexpected_RegExp, full_infor);
					resume.setIndustry_expected(industry_expected);
				}
				if (full_infor.contains("目标地点：")) {
					String locationtarget_RegExp = regExp.getAssertion("                          目标地点：                          <span class=\"text\" style=\"padding-left:10px;\">", "</span>");
					location_target = regExp.getGroup(locationtarget_RegExp, full_infor);
					resume.setLocation_target(location_target);
				}
				if (full_infor.contains("期望月薪：")) {
					String expectedsalary_RegExp = regExp.getAssertion("						  期望月薪：							<span class=\"text\" style=\"padding-left:10px;\">","</span>");
					expected_salary = regExp.getGroup(expectedsalary_RegExp, full_infor);
					resume.setExpected_salary(expected_salary);
				}
				if (full_infor.contains("目标职能：")) {
					String goalfunction_RegExp = regExp.getAssertion("                          目标职能：                          <span class=\"text\" style=\"padding-left:10px;\">", "</span>");
					goal_function = regExp.getGroup(goalfunction_RegExp, full_infor);
					resume.setGoal_function(goal_function);
				}
				if (full_infor.contains("求职状态：")) {
					String jobstatus_RegExp = regExp.getAssertion("                          求职状态：                          <span class=\"text\" style=\"padding-left:10px;\">", "</span>");
					job_status = regExp.getGroup(jobstatus_RegExp, full_infor);
					resume.setJob_status(job_status);
				}
				if (full_infor.contains("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">工作经验</td>")) {
					String experience_RegExp = regExp.getAssertion("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">工作经验", "</table>");
					work_experience = regExp.getGroup(experience_RegExp, full_infor);
					if (work_experience != null) {
						work_experience=  work_experience.replaceAll("<.+?>", " ");
						work_experience = work_experience.replaceAll("</.+?>", " ");
					}
					resume.setWork_Experience(work_experience);
					String worked_title_RegExp=regExp.getAssertion("</td><td class=\"text\"><b>", "</b>");
					worked_title=regExp.getGroupList(worked_title_RegExp, full_infor);
					LOGGER.info("in:"+regExp.getGroupList(worked_title_RegExp, full_infor));
					resume.setWorked_title(worked_title);
					String brief_work_experience_RegExp=regExp.getAssertion("</tr><tr><td colspan=\"2\" class=\"text_left\">", "</td>");
					corpList=regExp.getGroupList(brief_work_experience_RegExp, full_infor);
					if(corpList.size()!=0){
						brief_work_experience="";
					}
					for(String corpStr:corpList){
						corpMatcher=corpPattern.matcher(corpStr);
						if(!corpMatcher.matches()){
							brief_work_experience=brief_work_experience+corpStr;
						}
					}
//					System.out.println(brief_work_experience);
					if(brief_work_experience!=null){
						if(!brief_work_experience.equals("[]")){
							brief_work_experience=brief_work_experience.replaceAll("\\[", "");
							brief_work_experience=brief_work_experience.replaceAll("\\]", "");
							brief_work_experience=brief_work_experience.replaceAll("<.+?>", "");
							brief_work_experience=brief_work_experience.replaceAll("</.+?>", "");
						}
					}
					resume.setBrief_work_experience(brief_work_experience);
				}
				if (full_infor.contains("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">项目经验")) {
					String project_RegExp = regExp.getAssertion("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">项目经验", "</table>");
					project = regExp.getGroup(project_RegExp, full_infor);
					if (project != null) {
						project = project.replaceAll("<.+?>", " ");
						project = project.replaceAll("</.+?>", "");
					}
					resume.setProject(project);
					String brief_project_RegExp=regExp.getAssertion("</td><td width=\"84%\" class=\"text\">", "</td>");
					brief_project=regExp.getGroupList(brief_project_RegExp, full_infor).toString();
					String devTool_RegExp=regExp.getAssertion("开发工具：</td><td class=\"text\">", "</td>");
					devTools=regExp.getGroupList(devTool_RegExp, full_infor);
					String responDesc_RegExp=regExp.getAssertion("责任描述：</td><td class=\"text\">", "</td>");
					responDescs=regExp.getGroupList(responDesc_RegExp, full_infor);
					if(!brief_project.equals("[]")){
						brief_project=brief_project.replaceAll("\\[", "");
						brief_project=brief_project.replaceAll("\\]", "");
						brief_project=brief_project.replaceAll("<.+?>", "");
						brief_project=brief_project.replaceAll("</.+?>", "");
						for(String devTool:devTools){
							brief_project=brief_project+" "+devTool;
						}
						for(String responDesc:responDescs){
							brief_project=brief_project+" "+responDesc;
						}
						resume.setBrief_project(brief_project);
					}
				}
				if (full_infor.contains("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">教育经历")) {
					String eduexp_detail = full_infor.substring(full_infor.indexOf("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">教育经历"));
					String eduexp_RegExp = regExp.getAssertion("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"table_set\">", "</table>");
					edu_experience = regExp.getGroup(eduexp_RegExp, eduexp_detail);
					if(edu_experience!=null){
						String brief_education_RegExp=regExp.getAssertion("<td colspan=\"4\" class=\"text_left\">", "</td>");
						brief_education=regExp.getGroup(brief_education_RegExp, edu_experience);
						/*if(brief_education.contains("专业课程：")){
							String course_RegExp=regExp.getAssertion("专业课程：", "<br>");
							String course_Str=regExp.getGroup(course_RegExp, edu_experience);
							brief_education=brief_education+course_Str;
						}
						if(edu_experience.contains("毕业设计：")){
							String graduatation_RegExp=regExp.getAssertion("毕业设计：", "<br>");
							String graduatation_Str=regExp.getGroup(graduatation_RegExp, edu_experience);
							brief_education=brief_education+" "+graduatation_Str;
						}*/
						if(brief_education!=null){
							brief_education=brief_education.replaceAll("<.+?>", "");
						}
						resume.setBrief_edu_experience(brief_education);
					}
					if (edu_experience != null) {
						edu_experience=  edu_experience.replaceAll("<.+?>", " ");
						edu_experience = edu_experience.replaceAll("</.+?>", " ");
					}
					resume.setEdu_experience(edu_experience);
				}
				if (full_infor.contains("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">IT    技能")) {
					String itskill_detail = full_infor.substring(full_infor.indexOf("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">IT    技能"));
					String itskill_RegExp = regExp.getAssertion("<tr><td class=\"text_left\">", "</td><td class=\"text\">");
					it_skill = regExp.getGroupList(itskill_RegExp, itskill_detail);
					resume.setIt_skill(it_skill);
				}
			}
		}
//		System.out.println("primary key id is : "+primaryKey);
		LOGGER.info("primary key id is : "+primaryKey);
//		LOGGER.info(resume);
		return resume;
	}

}
