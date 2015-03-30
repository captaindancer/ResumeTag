package com.wind.data.DatabaseOperation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.wind.dao.impl.ResumeDAO;
import com.wind.data.utils.FileUtils;
import com.wind.information.model.ResumeKeyword;
import com.wind.service.TagAnnotation;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 7, 2014  5:17:07 PM
 *@Description
 */
public class TestTagAnnotation {

	private static int startId=100;
	private static int endId=500;
	private static int number=50;
	private static int dataLimit=500;
	
	@Test
	public void test() throws SQLException, IOException {
		ResumeDAO resume=new ResumeDAO();
		TagAnnotation tag=new TagAnnotation();
		Map<ResumeKeyword, String> testMap;
//		System.out.println(testMap.size());
		BufferedWriter writer=FileUtils.getFileWriter(new File("/home/liufeng/data/resume/compare.dat"));
//		for(Entry<ResumeKeyword, String> entry:testMap.entrySet()){
////			System.out.println(entry.getKey()+":"+entry.getValue());
//			writer.write(entry.getKey()+":"+entry.getValue());
//			writer.newLine();
//		}
//		writer.close();
//		System.out.println("____________________________________________");
		int maxID=resume.getMaxID();
		for(int index=0;index<=maxID;index+=dataLimit){
			testMap=resume.getTestResult(index, index+dataLimit);
			for(Entry<ResumeKeyword, String> entry:testMap.entrySet()){
//				System.out.println(tag.taggingCosine(entry.getValue(), number));
				int count=0;
				String result="";
				for(String str:tag.taggingCosine(entry.getValue(), number)){
					if(entry.getValue().contains(str)){
						count++;
						result=result+" "+str;
					}
					if(count>5){
						break;
					}
				}
//				System.out.println(entry.getKey().getKeyword());
				System.out.println(entry.getKey().getResumeId());
				writer.write(entry.getKey().getResumeId());
				writer.newLine();
				writer.write(entry.getKey().getKeyword());
				writer.newLine();
				writer.write(result);
				writer.newLine();
				writer.write("_____________________________________");
				writer.newLine();
				writer.newLine();
		}
		/*for(Entry<ResumeKeyword, String> entry:testMap.entrySet()){
			System.out.println(tag.taggingCosine(entry.getValue(), number));
			int count=0;
			String result="";
			for(String str:tag.taggingCosine(entry.getValue(), number)){
				if(entry.getValue().contains(str)){
					count++;
					result=result+" "+str;
				}
				if(count>4){
					break;
				}
			}
			System.out.println(result);
			System.out.println(entry.getKey());
			System.out.println("**********************");
		}*/
	}
		writer.close(	);
		System.out.println("**********end**********");

	}
}
