package com.wind.data.DatabaseOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.wind.data.utils.FileUtils;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 7, 2014  11:33:00 AM
 *@Description
 */
public class TestMapEntryset {

	@Test
	public void test() throws IOException {
		Map<String, Map<String, Double>> baseMap=new HashMap<String, Map<String,Double>>();
		Map<String, Double> valueMap=new LinkedHashMap<String, Double>();
//		valueMap.put("数据库", 1.0);
//		valueMap.put("动态", 0.6310451416936935);
//		valueMap.put("apache", 0.6115987265163276);
		BufferedReader reader=FileUtils.getFileReader(new File("/home/liufeng/data/testdataset"));
		String line=reader.readLine();
		for(String keyvalue:line.split(",")){
			String[] strArray=keyvalue.split("=");
			valueMap.put(strArray[0].trim(),Double.valueOf(strArray[1].trim()));
		}
		baseMap.put("maven", valueMap);
		System.out.println(valueMap.size());
		Set<Entry<String, Double>> test=null;
		if(baseMap.containsKey("maven")){
			for(Entry<String, Double> valueEntry:test=baseMap.get("maven").entrySet()){
				System.out.println(test);
				baseMap.get("maven").put(valueEntry.getKey(), valueEntry.getValue()/2);
			}
		}
	}

}
