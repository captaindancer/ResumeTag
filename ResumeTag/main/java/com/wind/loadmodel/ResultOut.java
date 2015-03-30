package com.wind.loadmodel;

import java.io.BufferedWriter;
import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import com.wind.dao.impl.ResumeDAO;
import com.wind.data.utils.FileUtils;
import com.wind.information.model.ResumeKeyword;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Sep 1, 2014  8:47:34 PM
 *@Description
 */
public class ResultOut {
	private static int dataLimit=500;

	public static void main(String[] args) throws Exception {
		ResumeDAO resume=new ResumeDAO();
		Map<ResumeKeyword, String> testMap;
		BufferedWriter writer=FileUtils.getFileWriter(new File("/home/liufeng/dataset/100_20.dat"));
//		int maxID=resume.getMaxID();
		for(int index=0;index<=1500;index+=dataLimit){
			testMap=resume.getTestResult(index, index+dataLimit);
			for(Entry<ResumeKeyword, String> entry:testMap.entrySet()){
//				int count=0;
				String result="";
				for(String str:ModelLoader.doTag(entry.getValue(), 20)){
					/*if(entry.getValue().contains(str)){
						count++;
						result=result+" "+str;
					}
					if(count>5){
						break;
					}*/
					result=result+str+" ";
				}
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
	}
		writer.close(	);
		System.out.println("**********end**********");
	}

}
