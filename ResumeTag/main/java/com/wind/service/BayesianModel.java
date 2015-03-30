package com.wind.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wind.dao.impl.ResumeDAO;
import com.wind.data.DatabaseOperation.TestClassloader;
import com.wind.information.model.ResumeKeyword;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 4, 2014  11:25:42 AM
 *@Description
 */

public class BayesianModel {
	
	private static final int dataLimit=100;
	private static final int number=30;
	private static final Logger LOGGER=LogManager.getLogger(BayesianModel.class);
	private static Set<String> stopwordSet=new HashSet<String>(1000);
	
	static{
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
	
	public static Map<String, Map<String, Double>> getPredictModel() throws SQLException{
		Map<String, Map<String, Double>> predictModel=new HashMap<String, Map<String,Double>>();
		ResumeDAO resumeDAO=new ResumeDAO();
		int maxID=resumeDAO.getMaxID();
		VectorSpaceModel vsm=new VectorSpaceModel();
		Map<ResumeKeyword, Map<String, Double>> validMap;
		KeywordStatistics keywordStatistics=new KeywordStatistics();
		Map<String, Integer> keywordFrequency=keywordStatistics.getKeywordFrequency();
		for(int index=0;index<=maxID;index+=dataLimit){
			validMap=vsm.getValidVSMMap(index, dataLimit, number);
			for(Entry<ResumeKeyword, Map<String, Double>> entry:validMap.entrySet()){
				for(String keyword:entry.getKey().getKeyword().split(" |、|,|，|。|!|;")){
					if(!keyword.matches("\\d|\\)|\\(|.|/|-|=\\w|\\\\|")&&!stopwordSet.contains(keyword)){
						if(predictModel.containsKey(keyword)){
							for(Entry<String, Double> keywordEntry:entry.getValue().entrySet()){
								if(predictModel.get(keyword).containsKey(keywordEntry.getKey())){
									predictModel.get(keyword).put(keywordEntry.getKey(), predictModel.get(keyword).get(keywordEntry.getKey())+keywordEntry.getValue());
								}else{
									predictModel.get(keyword).put(keywordEntry.getKey(), keywordEntry.getValue());
								}
							}
						}else{
							predictModel.put(keyword, entry.getValue());
						}
					}
				}
			}
		}
		
		for(Entry<String, Integer> entry:keywordFrequency.entrySet()){
			String entryKey=entry.getKey();
			int entryValue=entry.getValue();
			if(predictModel.containsKey(entryKey)){
				Map<String, Double> newvalueMap=new LinkedHashMap<String, Double>();
				for(Entry<String, Double> valueEntry:predictModel.get(entryKey).entrySet()){
//					LOGGER.info(entry.getKey()+":"+valueEntry.getKey()+"="+valueEntry.getValue()+":"+entry.getValue());
//					predictModel.get(entryKey).put(valueEntry.getKey(), valueEntry.getValue()/entryValue);
					newvalueMap.put(valueEntry.getKey(), valueEntry.getValue()/entryValue);
//					System.out.println(entry.getKey()+":"+valueEntry.getValue()+":"+entry.getValue());
				}
				predictModel.put(entryKey, newvalueMap);
			}
		}
		
		for(Entry<String, Map<String, Double>> entry:predictModel.entrySet()){
			String key=entry.getKey();
			double norms=0;
			for(Entry<String, Double> valueEntry:entry.getValue().entrySet()){
				norms+=valueEntry.getValue()*valueEntry.getValue();
			}
			norms=Math.sqrt(norms);
			predictModel.get(key).put("norms", norms);
		}
		/*System.out.println(predictModel.get("绩效管理"));
		System.out.println(keywordFrequency.get("绩效管理"));*/
//		int count=0;
		/*for(Entry<String, Map<String, Double>> entry:predictModel.entrySet()){
			if(entry.getKey().equals("maven")){
				count++;
			}	
		}*/
		/*for(Entry<String, Integer> entry:keywordFrequency.entrySet()){
			if(entry.getKey().equals("maven")){
				count++;
			}
		}
		System.out.println(count);*/
		
		return predictModel;
	}
	
	public static void main(String[] args) throws SQLException {
		getPredictModel();
	}
}
