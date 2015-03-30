package com.wind.information.model;
/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 1, 2014  4:11:21 PM
 *@Description
 */

public class ResumeKeyword {
	private String keyword;
	private String resumeId;

	public String getResumeId() {
		return resumeId;
	}

	public void setResumeId(String resumeId) {
		this.resumeId = resumeId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public ResumeKeyword(){
		
	}
	/**
	 * 用传进来的content构造类
	 * @param content 关键词内容
	 */
	public ResumeKeyword(String content){
		this.keyword=content;
	}
	
	public ResumeKeyword(String resumeId,String content){
		this.resumeId=resumeId;
		this.keyword=content;
	}
	
	@Override
	public String toString(){
		return this.resumeId+":"+this.keyword;
	}
}
