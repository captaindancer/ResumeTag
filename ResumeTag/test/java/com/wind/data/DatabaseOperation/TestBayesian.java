package com.wind.data.DatabaseOperation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.wind.data.utils.FileUtils;
import com.wind.service.BayesianModel;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 5, 2014  8:38:48 PM
 *@Description
 */
public class TestBayesian {

	@Test
	public void testGetPredictModel() throws SQLException, IOException {
		Map<String, Map<String, Double>> map=BayesianModel.getPredictModel();
		System.out.println(map.size());
		BufferedWriter bufferedWriter = FileUtils.getFileWriter(new File("/home/liufeng/data/weightedpredictnorms.dat"));
		for(Entry<String, Map<String, Double>> entry:map.entrySet()){
//			System.out.println(entry.getKey()+" = "+entry.getValue());
			bufferedWriter.write("["+entry.getKey()+"] = "+entry.getValue());
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}

}
