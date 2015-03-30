package com.wind.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.wind.rank.AbstractExtractor;
import com.wind.rank.WordExtract;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 7, 2014  10:24:11 AM
 *@Description
 */

public class TagAnnotation {

	private Map<String, Map<String, Double>> modelMap;
	private AbstractExtractor extractor;
	private static Properties properties;
	private static String dictionaryPath;
	private static String taggerPath;
	private static String stopwordsPath;
	
	private static final int extractNumber=30;
	
	static {
		properties = new Properties();
		InputStream inputStream = VectorSpaceModel.class.getResourceAsStream("/segmentor.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new ExceptionInInitializerError("can't load the property file!");
		}
		if (1 == Integer.parseInt(properties.getProperty("segPath")) && 1 == Integer.parseInt(properties.getProperty("dicPath"))) {
			taggerPath = VectorSpaceModel.class.getResource("/seg.m").toString();
			taggerPath = taggerPath.substring(taggerPath.indexOf("/"));
			dictionaryPath = VectorSpaceModel.class.getResource("/dictionary/dictionary.txt").toString();
			dictionaryPath = dictionaryPath.substring(dictionaryPath.indexOf("/"));
		}
		if (1 == Integer.parseInt(properties.getProperty("segPath")) && 1 != Integer.parseInt(properties.getProperty("dicPath"))) {
			taggerPath = VectorSpaceModel.class.getResource("/seg.m").toString();
			taggerPath = taggerPath.substring(taggerPath.indexOf("/"));
		}
		// TODO
		if (1 == Integer.parseInt(properties.getProperty("ansjDic"))) {
			dictionaryPath = VectorSpaceModel.class.getResource("/ansj.dic").toString();
			dictionaryPath = dictionaryPath.substring(dictionaryPath.indexOf("/"));
		}
		stopwordsPath = VectorSpaceModel.class.getResource("/stopwords").toString();
		stopwordsPath = stopwordsPath.substring(stopwordsPath.indexOf("/"));
	}
	
	public Map<String, Map<String, Double>> getModelMap(){
		return this.modelMap;
	}
	
	public TagAnnotation(){
		try {
			modelMap=BayesianModel.getPredictModel();
			extractor = new WordExtract(taggerPath, dictionaryPath, stopwordsPath);
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("the model's generation fails");
		} catch (Exception e) {
			throw new ExceptionInInitializerError("the model's generation fails");
		}
	}
	
	public List<String> taggingCosine(String content,int number){
		List<String> tagList=new ArrayList<String>();
		Map<String, Double> tagvalue=new HashMap<String, Double>(1000);
		Map<String, Double> contentMap=extractor.extract(content, extractNumber);
		double norms=0;
		for(Entry<String, Double> entry:contentMap.entrySet()){
			norms+=entry.getValue()*entry.getValue();
		}
		norms=Math.sqrt(norms);
		contentMap.put("norms", norms);
		for(Entry<String, Map<String, Double>> entry:modelMap.entrySet()){
			double innerProduct=0;
			for(Entry<String, Double> valueEntry:entry.getValue().entrySet()){
				String valueKey=valueEntry.getKey();
				if(!valueKey.equals("norms")){
					if(contentMap.containsKey(valueKey)){
						innerProduct+=contentMap.get(valueKey)*valueEntry.getValue();
					}
				}
			}
			double model_norms=entry.getValue().get("norms");
			double content_norms=contentMap.get("norms");
			double result=innerProduct/(model_norms*content_norms);
			tagvalue.put(entry.getKey(), result);
		}
		List<Map.Entry<String, Double>> pairList = new ArrayList<Map.Entry<String, Double>>(
				tagvalue.entrySet());
		Collections.sort(pairList,
				new Comparator<Map.Entry<String, Double>>() {
					public int compare(Map.Entry<String, Double> entry1,
							Map.Entry<String, Double> entry2) {
						if (entry1.getValue() < entry2.getValue()) {
							return 1;
						} else if (entry1.getValue() == entry2.getValue()) {
							return 0;
						} else {
							return -1;
						}
					}
				});
		for(int index=0;index<number;index++){
			if(index<pairList.size()){
				tagList.add(pairList.get(index).getKey());
//				System.out.println(pairList.get(index).getKey()+":"+pairList.get(index).getValue());
			}
		}
		return tagList;
	}
}
