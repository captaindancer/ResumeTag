package com.wind.data.DatabaseOperation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.wind.data.utils.FileUtils;
import com.wind.service.KeywordStatistics;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 5, 2014  7:37:54 PM
 *@Description
 */
public class TestKeywordStatistics {

	@Test
	public void testGetKeywordProbability() throws SQLException, IOException {
		KeywordStatistics keywordStatistics=new KeywordStatistics();
		Map<String, Double> map=keywordStatistics.getKeywordProbability();
		System.out.println(map.size());
//		for(Entry<String, Double> entry:map.entrySet()){
//			System.out.println(entry.getKey()+"="+entry.getValue());
//		}
		Map<String, Integer> freMap=keywordStatistics.getKeywordFrequency();
		System.out.println(freMap.size());
		BufferedWriter writer=FileUtils.getFileWriter(new File("/home/liufeng/data/newkeyword.dat"));
		for(Entry<String, Integer> entry:freMap.entrySet()){
//			System.out.println(entry.getKey()+"="+entry.getValue());
			writer.write(entry.getKey()+"="+entry.getValue());
			writer.newLine();
		}
		writer.close();
	}

}
