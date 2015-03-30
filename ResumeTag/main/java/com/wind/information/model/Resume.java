package com.wind.information.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liufeng E-mail:fliu.Dancer@wind.com.cn
 * @version Time:Jul 15, 2014 3:39:36 PM
 * @Description
 */
public class Resume {

	private String resumeId; // 简历id
	private String keyword; // 简历关键字
	private String sex; // 性别
	private String birthday; // 出生年月
	private String residence; // 居住地
	private String recentCompany; // 最近公司
	private String profession; // 行业
	private String position; // 职位
	private String degree; // 学历
	private String major; // 专业
	private String university; // 学校
	private String self_assessment; // 自我评价
	private String duty_time; // 到岗时间
	private String industry_expected; // 期望行业
	private String job_specification; // 工作性质
	private String location_target; // 目标地点
	private String expected_salary; // 期望月薪
	private String goal_function; // 目标职能
	private String job_status; // 求职状态

	private String work_experience; // 工作经验
	private String brief_work_experience;  //简明工作经验
	private List<String> worked_title=new ArrayList<String>();  //工作职位

	private String project; // 项目经验
	private String brief_project;  //简明项目经验
	private String edu_experience; // 教育经历
	private String brief_edu_experience; //简明教育经历

	private List<String> it_skill=new ArrayList<String>(); // it技能

	public String getBrief_edu_experience() {
		return brief_edu_experience;
	}
	
	public void setBrief_edu_experience(String brief_edu_experience) {
		this.brief_edu_experience = brief_edu_experience;
	}
	
	public List<String> getWorked_title() {
		return worked_title;
	}
	
	public void setWorked_title(List<String> worked_title) {
		this.worked_title = worked_title;
	}
	
	public String getResumeId() {
		return resumeId;
	}

	public void setResumeId(String resumeId) {
		this.resumeId = resumeId;
	}

	public List<String> getIt_skill() {
		return it_skill;
	}

	public void setIt_skill(List<String> it_skill) {
		this.it_skill = it_skill;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getIndustry_expected() {
		return industry_expected;
	}

	public void setIndustry_expected(String industry_expected) {
		this.industry_expected = industry_expected;
	}

	public String getLocation_target() {
		return location_target;
	}

	public void setLocation_target(String location_target) {
		this.location_target = location_target;
	}

	public String getGoal_function() {
		return goal_function;
	}

	public void setGoal_function(String goal_function) {
		this.goal_function = goal_function;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getRecentCompany() {
		return recentCompany;
	}

	public void setRecentCompany(String recentCompany) {
		this.recentCompany = recentCompany;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSelf_assessment() {
		return self_assessment;
	}

	public void setSelf_assessment(String self_assessment) {
		this.self_assessment = self_assessment;
	}

	public String getDuty_time() {
		return duty_time;
	}

	public void setDuty_time(String duty_time) {
		this.duty_time = duty_time;
	}

	public String getJob_specification() {
		return job_specification;
	}

	public void setJob_specification(String job_specification) {
		this.job_specification = job_specification;
	}

	public String getExpected_salary() {
		return expected_salary;
	}

	public void setExpected_salary(String expected_salary) {
		this.expected_salary = expected_salary;
	}

	public String getJob_status() {
		return job_status;
	}

	public void setJob_status(String job_status) {
		this.job_status = job_status;
	}

	public String getWork_Experience() {
		return work_experience;
	}

	public void setWork_Experience(String work_experience) {
		this.work_experience = work_experience;
	}

	public String getBrief_work_experience() {
		return brief_work_experience;
	}

	public void setBrief_work_experience(String brief_work_experience) {
		this.brief_work_experience = brief_work_experience;
	}

	public String getBrief_project() {
		return brief_project;
	}

	public void setBrief_project(String brief_project) {
		this.brief_project = brief_project;
	}

	public String getEdu_experience() {
		return edu_experience;
	}

	public void setEdu_experience(String edu_experience) {
		this.edu_experience = edu_experience;
	}

	/*@Override
	public String toString() {
		return "Resume [resumeId=" + this.resumeId + ", keyword=" + keyword + ", sex=" + sex + ", birthday=" + birthday + ", residence=" + residence
				+ ", recentCompany=" + recentCompany + ", profession=" + profession + ", position=" + position + ", degree=" + degree + ", major="
				+ major + ", university=" + university + ", self_assessment=" + self_assessment + ", duty_time=" + duty_time + ", industry_expected="
				+ industry_expected + ", job_specification=" + job_specification + ", location_target=" + location_target + ", expected_salary="
				+ expected_salary + ", goal_function=" + goal_function + ", job_status=" + job_status + ", work_experience=" + work_experience
				+ ", project=" + project + ", edu_experience=" + edu_experience + ", it_skill=" + it_skill.toString() + "]";
	}*/

	@Override
	public String toString() {
		return "The resume is:\nresumeId:" + this.resumeId + "\nkeyword:" + this.keyword + "\nsex:" + this.sex + "\nbirthday:" + this.birthday
				+ "\nresidence:" + this.residence + "\nrecentCompany:" + this.recentCompany + "\nprofession:" + this.profession + "\nposition:"
				+ this.position + "\ndegree:" + this.degree + "\nmajor:" + this.major + "\nuniversity:" + this.university + "\nself_assessment:"
				+ this.self_assessment + "\nduty_time:" + this.duty_time + "\nindustry_expected:" + this.industry_expected + "\njob_specification:"
				+ this.job_specification + "\nlocation_target:" + this.location_target + "\nexpected_salary:" + this.expected_salary
				+ "\ngoal_function:" + this.goal_function + "\njob_status:" + this.job_status + "\nwork_experience:" + this.work_experience + "\nbrief_work_experience:"+this.brief_work_experience+"\nworked_title:"+this.worked_title+"\nproject:"
				+ this.project +"\nbrief_project:"+this.brief_project+ "\nedu_experience:" + edu_experience + "\nit_skill:" + this.it_skill;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result + ((duty_time == null) ? 0 : duty_time.hashCode());
		result = prime * result + ((edu_experience == null) ? 0 : edu_experience.hashCode());
		result = prime * result + ((expected_salary == null) ? 0 : expected_salary.hashCode());
		result = prime * result + ((goal_function == null) ? 0 : goal_function.hashCode());
		result = prime * result + ((industry_expected == null) ? 0 : industry_expected.hashCode());
		result = prime * result + ((it_skill == null) ? 0 : it_skill.hashCode());
		result = prime * result + ((job_specification == null) ? 0 : job_specification.hashCode());
		result = prime * result + ((job_status == null) ? 0 : job_status.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((location_target == null) ? 0 : location_target.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((profession == null) ? 0 : profession.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((recentCompany == null) ? 0 : recentCompany.hashCode());
		result = prime * result + ((residence == null) ? 0 : residence.hashCode());
		result = prime * result + ((resumeId == null) ? 0 : resumeId.hashCode());
		result = prime * result + ((self_assessment == null) ? 0 : self_assessment.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((university == null) ? 0 : university.hashCode());
		result = prime * result + ((work_experience == null) ? 0 : work_experience.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resume other = (Resume) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (duty_time == null) {
			if (other.duty_time != null)
				return false;
		} else if (!duty_time.equals(other.duty_time))
			return false;
		if (edu_experience == null) {
			if (other.edu_experience != null)
				return false;
		} else if (!edu_experience.equals(other.edu_experience))
			return false;
		if (expected_salary == null) {
			if (other.expected_salary != null)
				return false;
		} else if (!expected_salary.equals(other.expected_salary))
			return false;
		if (goal_function == null) {
			if (other.goal_function != null)
				return false;
		} else if (!goal_function.equals(other.goal_function))
			return false;
		if (industry_expected == null) {
			if (other.industry_expected != null)
				return false;
		} else if (!industry_expected.equals(other.industry_expected))
			return false;
		if (it_skill == null) {
			if (other.it_skill != null)
				return false;
		} else if (!it_skill.equals(other.it_skill))
			return false;
		if (job_specification == null) {
			if (other.job_specification != null)
				return false;
		} else if (!job_specification.equals(other.job_specification))
			return false;
		if (job_status == null) {
			if (other.job_status != null)
				return false;
		} else if (!job_status.equals(other.job_status))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (location_target == null) {
			if (other.location_target != null)
				return false;
		} else if (!location_target.equals(other.location_target))
			return false;
		if (major == null) {
			if (other.major != null)
				return false;
		} else if (!major.equals(other.major))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (profession == null) {
			if (other.profession != null)
				return false;
		} else if (!profession.equals(other.profession))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (recentCompany == null) {
			if (other.recentCompany != null)
				return false;
		} else if (!recentCompany.equals(other.recentCompany))
			return false;
		if (residence == null) {
			if (other.residence != null)
				return false;
		} else if (!residence.equals(other.residence))
			return false;
		if (resumeId == null) {
			if (other.resumeId != null)
				return false;
		} else if (!resumeId.equals(other.resumeId))
			return false;
		if (self_assessment == null) {
			if (other.self_assessment != null)
				return false;
		} else if (!self_assessment.equals(other.self_assessment))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (university == null) {
			if (other.university != null)
				return false;
		} else if (!university.equals(other.university))
			return false;
		if (work_experience == null) {
			if (other.work_experience != null)
				return false;
		} else if (!work_experience.equals(other.work_experience))
			return false;
		return true;
	}

}
