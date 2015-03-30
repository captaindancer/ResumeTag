package com.wind.loadmodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.wind.data.utils.FileUtils;
import com.wind.rank.AbstractExtractor;
import com.wind.rank.WordExtract;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 28, 2014  1:36:58 PM
 *@Description
 */
public class ModelLoader {
	
	private static Map<String, Map<String, Double>> modelMap=new HashMap<String, Map<String,Double>>(1000);
	private static AbstractExtractor extractor;
	private static Properties properties;
	private static String dictionaryPath;
	private static String taggerPath;
	private static String stopwordsPath;
	private static final int extractNumber=50;
	
	static{
		properties = new Properties();
		InputStream inputStream = ModelLoader.class.getResourceAsStream("/segmentor.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new ExceptionInInitializerError("can't load the property file!");
		}
		if (1 == Integer.parseInt(properties.getProperty("segPath")) && 1 == Integer.parseInt(properties.getProperty("dicPath"))) {
			taggerPath = ModelLoader.class.getResource("/seg.m").toString();
			taggerPath = taggerPath.substring(taggerPath.indexOf("/"));
			dictionaryPath = ModelLoader.class.getResource("/dictionary/dictionary.txt").toString();
			dictionaryPath = dictionaryPath.substring(dictionaryPath.indexOf("/"));
		}
		if (1 == Integer.parseInt(properties.getProperty("segPath")) && 1 != Integer.parseInt(properties.getProperty("dicPath"))) {
			taggerPath = ModelLoader.class.getResource("/seg.m").toString();
			taggerPath = taggerPath.substring(taggerPath.indexOf("/"));
		}
		// TODO
		if (1 == Integer.parseInt(properties.getProperty("ansjDic"))) {
			dictionaryPath = ModelLoader.class.getResource("/ansj.dic").toString();
			dictionaryPath = dictionaryPath.substring(dictionaryPath.indexOf("/"));
		}
		stopwordsPath = ModelLoader.class.getResource("/stopwords").toString();
		stopwordsPath = stopwordsPath.substring(stopwordsPath.indexOf("/"));
		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void load() throws IOException{
		BufferedReader reader=FileUtils.getFileReader(new File("/home/liufeng/dataset/model.dat"));
		String str;
		Map<String, Double> valueMap;
		while ((str = reader.readLine()) != null){
			String values[]=str.split("\\s+");
			if(values.length==2){
				String key=values[0];
				valueMap=new LinkedHashMap<String, Double>();
				for(String maps:values[1].split("@")){
					String[] pair=maps.split(",");
					valueMap.put(pair[0].replaceAll("\\(", ""), Double.valueOf(pair[1].replaceAll("\\)", "")));
				}
				modelMap.put(key, valueMap);
			}
		}
		reader.close();
		System.out.println(modelMap.size());
	}
	
	public static List<String> doTag(String content,int number) throws Exception{
		extractor = new WordExtract(taggerPath, dictionaryPath, stopwordsPath);
		Map<String, Double> contentMap=extractor.extract(content, extractNumber);
		Map<String, Double> tagvalue=new HashMap<String, Double>(1000);
		List<String> tagList=new ArrayList<String>();
//		load();
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
		List<Map.Entry<String, Double>> pairList = new ArrayList<Map.Entry<String, Double>>(tagvalue.entrySet());
		Collections.sort(pairList,new Comparator<Map.Entry<String, Double>>() {
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
			}
		}
		return tagList;
	}
	
	public static void main(String[] args) throws Exception {
		long start_time=System.currentTimeMillis();
//		load();
		List<String> list=doTag("为Microsoft的MSDN论坛,ASP.NET论坛和Silverlight论坛做技术支持为Nokia的Windows Phone做Email的技术支持,同时撰写和翻译一些Windows Phone方面的技术文章.该项目开发钢材领域中所有有关的步骤,包括钢材市场,钢材运输,钢材加工等等我是负责钢材加工模块的开发，公共信息模块的开发和一些自定义的控件的开发和维护", 20);
		System.out.println(list);
		System.out.println(System.currentTimeMillis()-start_time);
	}
}
