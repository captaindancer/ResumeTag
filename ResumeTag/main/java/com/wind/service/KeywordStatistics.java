package com.wind.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.wind.dao.impl.ResumeDAO;
import com.wind.data.DatabaseOperation.TestClassloader;
import com.wind.data.utils.FileUtils;
import com.wind.information.model.ResumeKeyword;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 1, 2014  11:42:24 AM
 *@Description
 */
public class KeywordStatistics {
	
	private Map<String, Integer> keywordMap=new HashMap<String, Integer>(1000);
	private List<String> keywordList=new ArrayList<String>(5000);
	private static final String SQLSTATEMENT="select keyword from extraction where keyword is not null and self_assessment is not null";
	private static final int dataLimit=100;
	private Set<String> stopwordSet=new HashSet<String>(1000);
	
	public Set<String> getStopwordSet(){
		return this.stopwordSet;
	}
	
	public KeywordStatistics(){
		InputStream inputStream=TestClassloader.class.getClassLoader().getResourceAsStream("stopword.dic");
		BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
		String line;
		try {
			while((line=reader.readLine())!=null){
//				System.out.println(line);
				stopwordSet.add(line);
			}
		} catch (IOException e) {
			throw new ExceptionInInitializerError("can't load the stopword");
		}
	}
	
	public Map<String, Integer> getKeywordMap() throws SQLException {
		ResumeDAO resumeDAO=new ResumeDAO();
		for(String keywords:resumeDAO.getField(SQLSTATEMENT)){
			for(String keyword:keywords.split(" |、|,|，|。|!|;")){
				if(!keyword.matches("\\d|\\)|\\(|.|/|-|=\\w|\\\\|")){
					if(keywordMap.containsKey(keyword)){
						keywordMap.put(keyword, keywordMap.get(keyword)+1);
					}else{
						keywordMap.put(keyword, 1);
					}
				}
			}
		}
		return keywordMap;
	}
	
	public int getAmount() throws SQLException{
		ResumeDAO resumeDAO=new ResumeDAO();
		for(String keywords:resumeDAO.getField(SQLSTATEMENT)){
			for(String keyword:keywords.split(" |、|,|，|。|!|;")){
				if(!keyword.matches("\\d|\\)|\\(|.|/|-|=\\w|\\\\|")){
					keywordList.add(keyword);
					}
				}
		}
		return keywordList.size();
	}

	public void writeToFile(String filename) throws IOException{
		BufferedWriter bufferedWriter = FileUtils.getFileWriter(new File(filename));
		for(Entry<String, Integer> entry:keywordMap.entrySet()){
			bufferedWriter.write(entry.getKey()+"="+entry.getValue());
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}
	

	public Map<String, Double> getKeywordProbability() throws SQLException{
		Map<String, Double> keywordProbability=new HashMap<String, Double>(1000);
		ResumeDAO resumeDAO=new ResumeDAO();
		int maxID=resumeDAO.getMaxID();
		VectorSpaceModel vsm=new VectorSpaceModel();
		Map<ResumeKeyword, String> validMap;
		List<String> fullWords=new ArrayList<String>();
		Map<String, Integer> wordMap=new HashMap<String, Integer>(1000);
		for(int index=0;index<=maxID;index+=dataLimit){
			validMap=vsm.getValidMap(index, dataLimit);
			for(Entry<ResumeKeyword, String> entry:validMap.entrySet()){
				for(String keyword:entry.getKey().getKeyword().split(" |、|,|，|。|!|;")){
					if(!keyword.matches("\\d|\\)|\\(|.|/|-|=\\w|\\\\|")&&!stopwordSet.contains(keyword)){
						fullWords.add(keyword);
						if(wordMap.containsKey(keyword)){
							wordMap.put(keyword, wordMap.get(keyword)+1);
						}else{
							wordMap.put(keyword, 1);
						}
					}
				}
			}
		}
		int totalSize=fullWords.size();
		for(Entry<String, Integer> entry:wordMap.entrySet()){
			keywordProbability.put(entry.getKey(), 10000*1.0*entry.getValue()/totalSize);
		}
		return keywordProbability;
	}
	
	public Map<String, Integer> getKeywordFrequency() throws SQLException{
		Map<String, Integer> keywordFrequency=new HashMap<String, Integer>(1000);
		ResumeDAO resumeDAO=new ResumeDAO();
		int maxID=resumeDAO.getMaxID();
		VectorSpaceModel vsm=new VectorSpaceModel();
		Map<ResumeKeyword, String> validMap;
		for(int index=0;index<=maxID;index+=dataLimit){
			validMap=vsm.getValidMap(index, dataLimit);
			for(Entry<ResumeKeyword, String> entry:validMap.entrySet()){
				for(String keyword:entry.getKey().getKeyword().split(" |、|,|，|。|!|;")){
					if(!keyword.matches("\\d|\\)|\\(|.|/|-|=\\w|\\\\|")&&!stopwordSet.contains(keyword)){
						if(keywordFrequency.containsKey(keyword)){
							keywordFrequency.put(keyword, keywordFrequency.get(keyword)+1);
						}else{
							keywordFrequency.put(keyword, 1);
						}
					}
				}
			}
		}
		return keywordFrequency;
	}
	
	public static void main(String[] args) {
		
		Map<String, Integer> keywordMap=new HashMap<String, Integer>(1000);
		KeywordStatistics keywordStatistics=new KeywordStatistics();
		try {
			keywordMap=keywordStatistics.getKeywordMap();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(keywordMap.size());
		try {
			keywordStatistics.writeToFile("/home/liufeng/data/keyword.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			int amount=keywordStatistics.getAmount();
			System.out.println(amount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		for(Entry<String, Integer> entry:keywordMap.entrySet()){
//			System.out.println(entry.getKey()+":"+entry.getValue());
//		}
	}

}
