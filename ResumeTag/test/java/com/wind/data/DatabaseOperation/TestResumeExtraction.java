package com.wind.data.DatabaseOperation;

import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.wind.dao.GenericDAO;
import com.wind.dao.impl.ResumeDAO;
import com.wind.data.utils.ResumeExtraction;
import com.wind.information.model.Resume;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 29, 2014  11:04:15 AM
 *@Description
 */
public class TestResumeExtraction {
	
private static final Logger LOGGER=LogManager.getLogger(TestResumeExtraction.class);

	@Test
	public void test() throws SQLException {
		long start_time=System.currentTimeMillis();
		ResumeExtraction resumeExtraction=new ResumeExtraction();
		Resume resume;
		int limit=100;
		Map<Integer,String> pageContent;
		GenericDAO<Resume> dao=new ResumeDAO();
		DataFetch dataFetch=new DataFetch();
		int maxID=dataFetch.getMaxID();
		/*for(int i=0;i<=maxID;i++){
			try {
				content=dataFetch.getPagecontent(i);
				resume=resumeExtraction.getExtraction(content,i);
//				System.out.println(resume);
				System.out.println("***************************");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
		for(int i=0;i<=500;i+=limit){
			pageContent=dataFetch.getPagecontentMap(i, i+limit);
			for(Entry<Integer, String> entry:pageContent.entrySet()){
				resume=resumeExtraction.getExtraction(entry.getValue(), entry.getKey());
//				System.out.println(resume);
				dao.save(resume);
//				LOGGER.info(resume);
			}
		}
		System.out.println(System.currentTimeMillis()-start_time);
	}
}
