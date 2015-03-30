package com.wind.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.wind.dao.GenericDAO;
import com.wind.data.DatabaseOperation.DataFetch;
import com.wind.data.utils.GenericExtraction;
import com.wind.information.model.Resume;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 23, 2014  8:26:16 PM
 *@Description
 */

@ComponentScan("com.wind")
@Service("resumeService")
public class ResumeService {
	
	private static final Logger LOGGER=LogManager.getLogger(ResumeService.class);
	private static final int limit=100; 
	private static DataFetch dataFetch=new DataFetch();
	
	private GenericDAO<Resume> resumeDAO;
	private GenericExtraction<Resume> resumeExtraction;
	private int maxID;
	
	public ResumeService(){
		
	}
	
	
	
	@Resource(name="MySQL")
	public void setResumeDAO(GenericDAO<Resume> resumeDAO){
		this.resumeDAO=resumeDAO;
	}
	
	@Resource(name="Resume")
	public void setResumeExtraction(GenericExtraction<Resume> resumeExtraction){
		this.resumeExtraction=resumeExtraction;
	}
	
	public void extract() throws SQLException{
		maxID=dataFetch.getMaxID();
		Map<Integer,String> pageContent;
		for(int index=0;index<maxID;index+=limit){
			pageContent=dataFetch.getPagecontentMap(index, index+limit);
			List<Resume> resumeList=new ArrayList<Resume>();
			for(Entry<Integer, String> entry:pageContent.entrySet()){
				resumeList.add(resumeExtraction.getExtraction(entry.getValue(), entry.getKey()));
			}
			resumeDAO.save(resumeList);
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext context=new AnnotationConfigApplicationContext(ResumeService.class);
		ResumeService service=context.getBean(ResumeService.class);
//		service.extract();
		try {
			service.extract();
		} catch (SQLException e) {
			LOGGER.error("something error in extract");
			e.printStackTrace();
		}
	}
}
