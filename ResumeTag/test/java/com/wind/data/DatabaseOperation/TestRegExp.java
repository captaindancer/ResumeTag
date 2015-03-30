package com.wind.data.DatabaseOperation;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.wind.data.utils.RegExp;

/**
 * @author liufeng E-mail:fliu.Dancer@wind.com.cn
 * @version Time:Jul 15, 2014 6:10:43 PM
 * @Description
 */
public class TestRegExp {

	@Test
	public void test() throws SQLException {
		DataFetch dataFetch = new DataFetch();
		String pageContent = dataFetch.getPagecontent(9);
		RegExp regExp = new RegExp();
		String resumeId=null;
		// String
		// start_str="<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">";
		// int begin_index=webpage.indexOf(start_str);
		// System.out.println(webpage.substring(begin_index));
		// Pattern pattern=Pattern.compile("(?<=<b>).+(?=</b>)");
		// Pattern pattern=Pattern.compile("(?<=<td>).+(?=</td>)");
		// Matcher matcher=pattern.matcher(webpage);
		// while(matcher.find()){
		// System.out.println(matcher.group());
		// }
		for (int index = 1; index <= 1000; index++) {
			resumeId=dataFetch.getResumeId(index);
			System.out.println("简历编号:"+resumeId);
			pageContent = dataFetch.getPagecontent(index);
			String start_str = "<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">";
			int begin_index=-1;
			if(pageContent!=null){
				begin_index = pageContent.indexOf(start_str);
			}
			if (begin_index != -1) {
				String resumeID=null;
				String basic = null;
				String keyword = null;
				String sex = null;
				String birthday = null;
				String residence = null;
				String company = null;
				String profession = null;
				String position = null;
				String degree = null;
				String major = null;
				String university = null;
				String full_infor = null;
				String self_assessment = null;
				String duty_time = null;
				String job_specification = null;
				String industry_expected = null;
				String location_target = null;
				String expected_salary = null;
				String goal_function = null;
				String job_status = null;
				String experience = null;
				String project = null;
				String edu_experience=null;
				List<String> it_skill=null;
				String resumeID_RegExp=regExp.getAssertion("<span style=\"font-size:25px;height:30px;line-height:30px;\"><b>", "</b></span>");
				resumeID=regExp.getGroup(resumeID_RegExp, pageContent).trim();
				if(resumeID!=null){
					resumeID=resumeID.substring(resumeID.indexOf(":")+1);
				}
				Assert.assertEquals(resumeId, resumeID);
				System.out.println("简历编号:"+resumeID);
				String keyword_RegExp = regExp.getAssertion(
						"<span id=\"spanTitled\">", "</span>");
				// System.out.println(regExp);
				keyword = regExp.getGroup(keyword_RegExp, pageContent);
				System.out.println("关键词:" + keyword);

				if (keyword != null) {
					String basic_RegExp = regExp.getAssertion("<span class=\"blue\"><b>", "</b></span>");
					basic = regExp.getGroup(basic_RegExp, pageContent);
					System.out.println(basic);
					if (basic.contains("男")) {
						sex = "男";
						System.out.println("性别:" + sex);
					} else {
						sex = "女";
						System.out.println("性别:" + sex);
					}
					int start_index = basic.indexOf("（");
					int end_index = basic.indexOf("）");
					birthday = basic.substring(start_index + 1, end_index);
					System.out.println("生日:" + birthday);
					String residence_RegExp = regExp.getAssertion(
							"居住地：</td><td width=\"42%\" height=\"20\">",
							"</td>");
					residence = regExp.getGroup(residence_RegExp, pageContent);
					System.out.println("居住地:" + residence);
					String job_RegExp = regExp
							.getAssertion(
									"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:8px auto;line-height:22px;padding:0 0 0 8px;\">",
									"</table>");
					String job_detail = null;
					job_detail = regExp.getGroup(job_RegExp, pageContent);
					if (job_detail!=null && job_detail.contains("公　司：")) {
						String company_RegExp = regExp.getAssertion(
								"<td width=\"230\">", "</td>");
						company = regExp.getGroup(company_RegExp, job_detail);
						System.out.println("公司:" + company);
						String profession_RegExp = regExp.getAssertion(
								"</td><td>", "</td></tr><tr><td>");
						profession = regExp.getGroup(profession_RegExp,
								job_detail);
						System.out.println("行业:" + profession);
						String positionStr = job_detail.substring(job_detail
								.indexOf("职　位："));
						String position_RegExp = regExp.getAssertion(
								"</td><td>", "</td></tr>");
						position = regExp
								.getGroup(position_RegExp, positionStr);
						System.out.println("职位:" + position);
					}
					String education_RegExp = regExp.getAssertion(
									"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:8px auto;line-height:22px;padding:0 0 0 10px;\">",
									"</table>");
					String education_detail = null;
					education_detail = regExp.getGroup(education_RegExp,pageContent);
					if(education_detail!=null){
					String degree_RegExp = regExp.getAssertion("<td width=\"230\">", "</td>");
					degree = regExp.getGroup(degree_RegExp, education_detail);
					System.out.println("学历:" + degree);
					String major_RegExp = regExp.getAssertion("</td><td>",
							"</td></tr><tr>");
					major = regExp.getGroup(major_RegExp, education_detail);
					System.out.println("专业:" + major);
					if (major == null && degree == null) {
						System.out.println("学校:" + university);
					} else {
						String universityStr = education_detail
								.substring(education_detail.indexOf("学　校："));
						String university_RegExp = regExp.getAssertion(
								"</td><td>", "</td></tr>");
						university = regExp.getGroup(university_RegExp,
								universityStr);
						System.out.println("学校:" + university);
					}
					}
					String fullInfor_RegExp = regExp
							.getGreedyAssertion(
									"<table width=\"97%\" border=\"0\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\">",
									"</table></td></tr>");
					full_infor = regExp.getGroup(fullInfor_RegExp, pageContent);
					System.out.println("全部信息:" + full_infor);
					if (full_infor.contains("自我评价")) {
						String assessment_RegExp = regExp
								.getAssertion(
										"<td align=\"left\" valign=\"middle\" class=\"text_left\"><span class=\"text\">",
										"</span>");
						self_assessment = regExp.getGroup(assessment_RegExp,full_infor);
						if(self_assessment!=null){
							self_assessment = self_assessment.replaceAll("<\\S+?>","");
						}
						System.out.println("自我评价:" + self_assessment);
					}
					if (full_infor.contains("到岗时间：")) {
						String dutytime_RegExp = regExp
								.getAssertion(
										"                          到岗时间：                          <span class=\"text\" style=\"padding-left:10px;\">",
										"</span>");
						duty_time = regExp
								.getGroup(dutytime_RegExp, full_infor);
						System.out.println("到岗时间:" + duty_time);
					}
					if (full_infor.contains("工作性质：")) {
						String jobspecification_RegExp = regExp
								.getAssertion(
										"                          工作性质：                          <span class=\"text\" style=\"padding-left:10px;\">",
										"</span>");
						job_specification = regExp.getGroup(
								jobspecification_RegExp, full_infor);
						System.out.println("工作性质:" + job_specification);
					}
					if (full_infor.contains("希望行业：")) {
						String industryexpected_RegExp = regExp
								.getAssertion(
										"                          希望行业：                          <span class=\"text\" style=\"padding-left:10px;\">",
										"</span>");
						industry_expected = regExp.getGroup(
								industryexpected_RegExp, full_infor);
						System.out.println("希望行业:" + industry_expected);
					}
					if (full_infor.contains("目标地点：")) {
						String locationtarget_RegExp = regExp
								.getAssertion(
										"                          目标地点：                          <span class=\"text\" style=\"padding-left:10px;\">",
										"</span>");
						location_target = regExp.getGroup(
								locationtarget_RegExp, full_infor);
						System.out.println("目标地点:" + location_target);
					}
					if (full_infor.contains("期望月薪：")) {
						String expectedsalary_RegExp = regExp
								.getAssertion(
										"						  期望月薪：							<span class=\"text\" style=\"padding-left:10px;\">",
										"</span>");
						expected_salary = regExp.getGroup(
								expectedsalary_RegExp, full_infor);
						System.out.println("期望月薪:" + expected_salary);
					}
					if (full_infor.contains("目标职能：")) {
						String goalfunction_RegExp = regExp
								.getAssertion(
										"                          目标职能：                          <span class=\"text\" style=\"padding-left:10px;\">",
										"</span>");
						goal_function = regExp.getGroup(goalfunction_RegExp,
								full_infor);
						System.out.println("目标职能:" + goal_function);
					}
					if (full_infor.contains("求职状态：")) {
						String jobstatus_RegExp = regExp
								.getAssertion(
										"                          求职状态：                          <span class=\"text\" style=\"padding-left:10px;\">",
										"</span>");
						job_status = regExp.getGroup(jobstatus_RegExp,
								full_infor);
						System.out.println("求职状态:" + job_status);
					}
					if (full_infor
							.contains("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">工作经验</td>")) {
						String experience_RegExp = regExp
								.getAssertion(
										"<td align=\"left\" valign=\"middle\" class=\"cvtitle\">工作经验",
										"</table>");
						experience = regExp.getGroup(experience_RegExp,full_infor).replaceAll("<.+?>", " ");
						experience = experience.replaceAll("</.+?>", " ");
						System.out.println("工作经验:" + experience);
					}
					if (full_infor
							.contains("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">项目经验")) {
						String project_RegExp = regExp
								.getAssertion("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">","</table>");
						project = regExp.getGroup(project_RegExp, full_infor).replaceAll("<.+?>", " ");
						project = project.replaceAll("</.+?>", "");
						System.out.println("项目经验:" + project);
					}
					if(full_infor.contains("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">教育经历")){
						String eduexp_detail=full_infor.substring(full_infor.indexOf("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">教育经历"));
						String eduexp_RegExp=regExp.getAssertion("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"table_set\">", "</table>");
						edu_experience=regExp.getGroup(eduexp_RegExp, eduexp_detail).replaceAll("<.+?>", " ");
						edu_experience=edu_experience.replaceAll("</.+?>", " ");
						System.out.println("教育经历:"+edu_experience);
					}
					if(full_infor.contains("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">IT    技能")){
						String itskill_detail=full_infor.substring(full_infor.indexOf("<td align=\"left\" valign=\"middle\" class=\"cvtitle\">IT    技能"));
						String itskill_RegExp=regExp.getAssertion("<tr><td class=\"text_left\">", "</td><td class=\"text\">");
						it_skill=regExp.getGroupList(itskill_RegExp, itskill_detail);
						for(String str:it_skill){
							System.out.println("it 技能:"+str);
						}
					}
					System.out.println("It has processed " + index);
				}
			}
		}
	}
}
