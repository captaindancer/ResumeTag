package com.wind.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.wind.dao.impl.ResumeDAO;
import com.wind.data.utils.FileUtils;
import com.wind.information.model.ResumeKeyword;
import com.wind.rank.AbstractExtractor;
import com.wind.rank.WordExtract;

/**
 * @author liufeng E-mail:fliu.Dancer@wind.com.cn
 * @version Time:Aug 1, 2014 3:48:38 PM
 * @Description
 */
public class VectorSpaceModel {

	private AbstractExtractor extractor;
	private static final int databaseLimit = 100;

	private static Properties properties;
	private static String dictionaryPath;
	private static String taggerPath;
	private static String stopwordsPath;

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

	public VectorSpaceModel() {
		try {
			extractor = new WordExtract(taggerPath, dictionaryPath, stopwordsPath);
		} catch (Exception e) {
			throw new ExceptionInInitializerError("cannot initialize the extractor");
		}
	}

	public Map<String, Double> getExtractedResult(String content, int limit) {
		return extractor.extract(content, limit);
	}

	public void writeToFile(String filename, Map<ResumeKeyword, String> map) throws IOException {
		BufferedWriter bufferedWriter = FileUtils.getFileWriter(new File(filename), true);
		for (Entry<ResumeKeyword, String> entry : map.entrySet()) {
			bufferedWriter.write("[" + entry.getKey() + "] = {" + entry.getValue() + "}");
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}

	/**
	 * 将数据表中的数据写入指定文件,每次从数据表中读取limit条数据,格式为[关键词组]={词1=权重,词2=权重......}
	 * 
	 * @param filename
	 *            指定的文件
	 * @param maxID
	 *            数据表中ID的最大值
	 * @param limit
	 *            每次从数据表中查询数据的数量
	 * @throws IOException
	 * @throws SQLException
	 */
	public void writeToFile(String filename, int maxID, int limit) throws IOException, SQLException {
		BufferedWriter bufferedWriter = FileUtils.getFileWriter(new File(filename), true);
		Map<ResumeKeyword, String> recieveMap;
		ResumeDAO resumeDAO = new ResumeDAO();
		for (int index = 0; index <= maxID; index += limit) {
			recieveMap = resumeDAO.getFormatResult(index, index + limit);
			for (Entry<ResumeKeyword, String> entry : recieveMap.entrySet()) {
				bufferedWriter.write("[" + entry.getKey() + "] = " + this.getExtractedResult(entry.getValue(), 30).toString());
				bufferedWriter.newLine();
			}
		}
		bufferedWriter.close();
	}

	public void writeToFile(String filename, int limit) throws IOException, SQLException {
		ResumeDAO resumeDAO = new ResumeDAO();
		int maxID = resumeDAO.getMaxID();
		writeToFile(filename, maxID, limit);
	}

	public Map<ResumeKeyword, Map<String, Double>> getValidVSMMap(int limit, int number) throws SQLException {
		Map<ResumeKeyword, Map<String, Double>> vsmMap = new HashMap<ResumeKeyword, Map<String, Double>>();
		Map<ResumeKeyword, String> validMap=getValidMap(limit);
		for (Entry<ResumeKeyword, String> entry : validMap.entrySet()) {
			vsmMap.put(entry.getKey(), this.getExtractedResult(entry.getValue(), number));
		}
		return vsmMap;
	}

	public Map<ResumeKeyword, Map<String, Double>> getValidVSMMap(int stardId,int limit,int number) throws SQLException{
		Map<ResumeKeyword, Map<String, Double>> vsmMap = new HashMap<ResumeKeyword, Map<String, Double>>();
		Map<ResumeKeyword, String> validMap=getValidMap(stardId, limit);
		for(Entry<ResumeKeyword, String> entry:validMap.entrySet()){
			vsmMap.put(entry.getKey(), this.getExtractedResult(entry.getValue(), number));
		}
		return vsmMap;
	}
	
	public Map<ResumeKeyword, Map<String, Double>> getAllExistedVSMMap(int limit,int number) throws SQLException{
		Map<ResumeKeyword, Map<String, Double>> vsmMap = new HashMap<ResumeKeyword, Map<String, Double>>();
		Map<ResumeKeyword, String> allExistedMap=getAllExistedMap(limit);
		for (Entry<ResumeKeyword, String> entry : allExistedMap.entrySet()) {
			vsmMap.put(entry.getKey(), this.getExtractedResult(entry.getValue(), number));
		}
		return vsmMap;
	}
	
	public Map<ResumeKeyword, Map<String, Double>> getAllExistedVSMMap(int startId,int limit,int number) throws SQLException{
		Map<ResumeKeyword, Map<String, Double>> vsmMap = new HashMap<ResumeKeyword, Map<String, Double>>();
		Map<ResumeKeyword, String> allExistedMap=getAllExistedMap(startId,limit);
		for (Entry<ResumeKeyword, String> entry : allExistedMap.entrySet()) {
			vsmMap.put(entry.getKey(), this.getExtractedResult(entry.getValue(), number));
		}
		return vsmMap;
	}
	
	/**
	 * 返回简历中的关键词Keyword至少在简历中出现一次的从stardId开始到startId+limit的简历数据
	 * 
	 * @param limit   查询中的增量大小
	 * @param startId   查询中开始的Id
	 * @return
	 * @throws SQLException
	 */
	public Map<ResumeKeyword, String> getValidMap(int startId, int limit) throws SQLException {
		Map<ResumeKeyword, String> validMap = new HashMap<ResumeKeyword, String>();
		ResumeDAO resumeDAO = new ResumeDAO();
//		int maxID = resumeDAO.getMaxID();
		// for (int index = 0; index <= maxID; index += limit) {
		Map<ResumeKeyword, String> recieveMap = resumeDAO.getFormatResult(startId, startId + limit);
		for (Entry<ResumeKeyword, String> entry : recieveMap.entrySet()) {
			String value = entry.getValue();
			String[] arrayStr = entry.getKey().getKeyword().split(" ");
			for (String str : arrayStr) {
				if (value.contains(str)) {
					validMap.put(entry.getKey(), value);
					break;
				}
			}
		}
		// }
		return validMap;
	}

	/**
	 * 返回简历中的关键词Keyword至少在简历中出现一次的全部简历数据
	 * @param limit  查询中每次的增量大小
	 * @return
	 * @throws SQLException
	 */
	public Map<ResumeKeyword, String> getValidMap(int limit) throws SQLException{
		Map<ResumeKeyword, String> validMap = new HashMap<ResumeKeyword, String>();
		ResumeDAO resumeDAO = new ResumeDAO();
		int maxID = resumeDAO.getMaxID();
		Map<ResumeKeyword, String> recieveMap;
		 for (int index = 0; index <= maxID; index += limit) {
			 recieveMap = resumeDAO.getFormatResult(index, index + limit);
			 for (Entry<ResumeKeyword, String> entry : recieveMap.entrySet()) {
				 String value = entry.getValue();
				 String[] arrayStr = entry.getKey().getKeyword().split(" ");
				 for (String str : arrayStr) {
					 if (value.contains(str)) {
						 validMap.put(entry.getKey(), value);
						 break;
					 }
				 }
			 }
		 }
		return validMap;
	}
	
	/**
	 * 返回简历中的关键词Keyword全部在简历中出现的从stardId开始到startId+limit的简历数据
	 * 
	 * @param limit      查询中的增量大小
	 * @param startId    查询中开始的id
	 * @return
	 * @throws SQLException
	 */
	public Map<ResumeKeyword, String> getAllExistedMap(int startId, int limit) throws SQLException {
		Map<ResumeKeyword, String> allExistedMap = new HashMap<ResumeKeyword, String>();
		ResumeDAO resumeDAO = new ResumeDAO();
		// int maxID = resumeDAO.getMaxID();
		// for(int index=0;index<=maxID;index+=limit){
		Map<ResumeKeyword, String> recieveMap = resumeDAO.getFormatResult(startId, startId + limit);
		for (Entry<ResumeKeyword, String> entry : recieveMap.entrySet()) {
			String value = entry.getValue();
			String[] arrayStr = entry.getKey().getKeyword().split(" ");
			int len = 0;
			for (String str : arrayStr) {
				if (value.contains(str)) {
					len++;
				} else {
					break;
				}
			}
			if (len == arrayStr.length) {
				allExistedMap.put(entry.getKey(), value);
			}
		}
		// }
		return allExistedMap;
	}

	/**
	 * 返回简历中的关键词Keyword全部在简历中出现的全部简历数据
	 * @param limit 查询中的每次增量大小
	 * @return
	 * @throws SQLException
	 */
	public Map<ResumeKeyword, String> getAllExistedMap(int limit) throws SQLException{
		Map<ResumeKeyword, String> allExistedMap = new HashMap<ResumeKeyword, String>();
		ResumeDAO resumeDAO = new ResumeDAO();
		int maxID = resumeDAO.getMaxID();
		Map<ResumeKeyword, String> recieveMap;
		for(int index=0;index<=maxID;index+=limit){
			recieveMap = resumeDAO.getFormatResult(index, index + limit);
			for (Entry<ResumeKeyword, String> entry : recieveMap.entrySet()) {
				String value = entry.getValue();
				String[] arrayStr = entry.getKey().getKeyword().split(" ");
				int len = 0;
				for (String str : arrayStr) {
					if (value.contains(str)) {
						len++;
					} else {
						break;
					}
				}
				if (len == arrayStr.length) {
					allExistedMap.put(entry.getKey(), value);
				}
			}
		 }
		return allExistedMap;
	}
	
	public static void main(String[] args) throws IOException {
		VectorSpaceModel vsm = new VectorSpaceModel();
		String content = "主要负责文字排版，打字速度80分钟/字。由于工作时使用的是专业软件，所以办公软件不是很精通，如果有熟手指点我，相信可以很快上手。";
		System.out.println(vsm.getExtractedResult(content, 10));
		ResumeDAO resumeDAO = new ResumeDAO();
		int maxID = resumeDAO.getMaxID();
		int count=0;
		Map<ResumeKeyword, String> dataMap;
		BufferedWriter bufferedWriter = FileUtils.getFileWriter(new File("/home/liufeng/data/validvsm.dat"));
		for (int index = 0; index < maxID; index += databaseLimit) {
			try {
				// dataMap=resumeDAO.getFormatResult(index,index+databaseLimit);
				dataMap = vsm.getValidMap(index,databaseLimit);
				// System.out.println(recieveMap);
				System.out.println(dataMap.size());
				for (Entry<ResumeKeyword, String> entry : dataMap.entrySet()) {
					// bufferedWriter.write(entry.getValue());
					bufferedWriter.write("[" + entry.getKey() + "] = " + vsm.getExtractedResult(entry.getValue(), 30).toString());
					// System.out.println(vsm.getExtractedResult(entry.getValue(),20));
					bufferedWriter.newLine();
					count++;
				}
				// vsm.writeToFile("/home/liufeng/data/vsm.dat", recieveMap);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(count);
		bufferedWriter.close();
	}
}
