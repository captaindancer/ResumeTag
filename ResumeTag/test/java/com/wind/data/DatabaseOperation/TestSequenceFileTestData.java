package com.wind.data.DatabaseOperation;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import com.wind.data.utils.SequenceFileCreation;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 26, 2014  11:21:59 AM
 *@Description
 */
public class TestSequenceFileTestData {

	@Test
	public void test() throws IOException, SQLException {
		String sqlStatement="select resumeId,keyword,self_assessment,brief_work_experience,brief_project,brief_edu_experience,it_skill from fullresume where keyword is not null and self_assessment is not null and id>= ? and id < ?";
		SequenceFileCreation sequenceFileCreation=new SequenceFileCreation();
		sequenceFileCreation.createSequenceFile("/home/liufeng/dataset/test.dat", sqlStatement,10000);
	}

}
