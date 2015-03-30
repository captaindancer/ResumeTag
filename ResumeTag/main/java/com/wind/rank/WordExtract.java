package com.wind.rank;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 基于TextRank的关键词抽取
 * 
 * @author liufeng
 * 
 */
class WDataSet {
	Graph graph = new Graph();
	List<Double> w = new ArrayList<Double>();// 当前每个顶点的权重
	List<Double> wBack = new ArrayList<Double>();//
	List<String> list = new ArrayList<String>();// 经过分词处理的所有词(包括重复词)
	List<String> subList = new ArrayList<String>();// 过滤重复词的分词结果
}

public class WordExtract extends AbstractExtractor {

	private static String className = null;

	static {
		try {
			Properties properties = new Properties();
			InputStream inputStream = WordExtract.class
					.getResourceAsStream("/segmentor.properties");
			properties.load(inputStream);
			className = properties.getProperty("segmentor");
		} catch (IOException e) {
			throw new ExceptionInInitializerError("can't load the file");
		}
	}
	public WordExtract(){
		precision = 0.001;
		dN = 0.85;
	}

	/**
	 * 适用于IKAnalyzer分词器,不需要加载自定义词典,已在IKAnalyzer配置文件中实现
	 * 
	 * @param stopPath          停用词目录
	 * @throws Exception
	 */
	public WordExtract(String stopPath) throws Exception {
		this(null,null,stopPath);
	}

	/**
	 * 通过反射的方法初始化分词器,无用户自定义词典
	 * 
	 * @param path       分词模型所在目录或者自定义词典所在目录
	 * @param stopPath   停用词所在目录
	 * @throws Exception
	 */
	public WordExtract(String path, String stopPath) throws Exception {
		this(path,null,stopPath);
	}

	/**
	 * 通过反射的方法初始化分词器,包含用户自定义词典
	 * 
	 * @param segPath
	 *            分词模型所在目录
	 * @param dicPath
	 *            用户自定义词典所在目录
	 * @param stopPath
	 *            停用词所在目录
	 * @throws Exception
	 */
	public WordExtract(String segPath, String dicPath, String stopPath)
			throws Exception {
		Constructor constructor;
		if (segPath == null && dicPath == null) {
			Class clazz = Class.forName(className);
			tag = (AbstractTagger) clazz.newInstance();
			stopwords = new StopWords(stopPath);
		}else if(segPath==null && dicPath!=null){
			Class clazz = Class.forName(className);
			constructor = clazz.getConstructor(String.class);
			tag = (AbstractTagger) constructor.newInstance(dicPath);
			stopwords = new StopWords(stopPath);
		}else if(segPath!=null && dicPath==null){
			Class clazz = Class.forName(className);
			constructor = clazz.getConstructor(String.class);
			tag = (AbstractTagger) constructor.newInstance(segPath);
			stopwords = new StopWords(stopPath);
		}else if(segPath!=null&&dicPath!=null){
			Class clazz = Class.forName(className);
			constructor = clazz.getConstructor(String.class,String.class);
			tag = (AbstractTagger) constructor.newInstance(segPath, dicPath);
			stopwords = new StopWords(stopPath);
		}
	}

	public WordExtract(AbstractTagger tag, String dicPath) {
		this.tag = tag;
		stopwords = new StopWords(dicPath);
	}

	public WordExtract(AbstractTagger tag, StopWords test) {
		this.tag = tag;
		this.stopwords = test;
	}

	private WDataSet getWord(String[] words) {
		//TODO
		Set<String> set = new TreeSet<String>();
		WDataSet wds = new WDataSet();

		if (stopwords != null) {
			wds.list = stopwords.phraseDel(words);
		} else {
			wds.list = new ArrayList<String>();
			for (int i = 0; i < words.length; i++) {
				if (words[i].length() > 0)
					wds.list.add(words[i]);
			}
		}

		for (int i = 0; i < wds.list.size(); i++) {
			String temp = wds.list.get(i);
			set.add(temp);
		}

		Iterator<String> ii = set.iterator();
		while (ii.hasNext()) {
			String str = ii.next();
			wds.subList.add(str);
		}
		return wds;
	}

	private WDataSet mapInit(int window, WDataSet wds) {
		TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
		Iterator<String> ii = wds.subList.iterator();
		int nnn = 0;
		while (ii.hasNext()) {
			String s = ii.next();
			Vertex vertex = new Vertex(s);
			wds.graph.addVertex(vertex);
			wds.w.add(1.0);
			wds.wBack.add(1.0);
			treeMap.put(s, nnn);
			nnn++;
		}

		String id1, id2;
		int index1, index2;

		int length = wds.list.size();
		while (true) {
			if (window > length)
				window /= 2;
			else if (window <= length || window <= 3)
				break;
		}

		for (int i = 0; i < wds.list.size() - window; i++) {
			id1 = wds.list.get(i);
			index1 = treeMap.get(id1);
			// TODO 找出相关联的边 等号注意删除
			for (int j = i + 1; j <= i + window; j++) {
				id2 = wds.list.get(j);
				index2 = treeMap.get(id2);
				wds.graph.addEdge(index2, index1);
				// TODO 查看效果,待删除
				wds.graph.addEdge(index1, index2);
			}
		}
		// TODO 查看效果,待删除
		for (int i = wds.list.size() - window; i < wds.list.size(); i++) {
			id1 = wds.list.get(i);
			index1 = treeMap.get(id1);
			// 找出相关联的边
			for (int j = i + 1; j < wds.list.size(); j++) {
				id2 = wds.list.get(j);
				index2 = treeMap.get(id2);
				wds.graph.addEdge(index2, index1);
				// TODO 查看效果,待删除
				wds.graph.addEdge(index1, index2);
			}
		}
		return wds;
	}

	boolean isCover(WDataSet wds) {
		int i;
		double result = 0.0;

		for (i = 0; i < wds.graph.getNVerts(); i++) {
			result += Math.abs(wds.w.get(i) - wds.wBack.get(i));
		}

		if (result < precision)
			return true;
		else
			return false;
	}

	public void toBackW(WDataSet wds) {
		int i;

		for (i = 0; i < wds.graph.getNVerts(); i++) {
			wds.wBack.set(i, wds.w.get(i));
		}
	}

	public WDataSet cal(WDataSet wds) {
		int i, j, forwardCount;
		double sumWBackLink, newW;
		ArrayList<Vertex> nextList;
		ArrayList<Integer> nextWList;
		Vertex vertex;

		while (true) {
			for (i = 0; i < wds.graph.getNVerts(); i++) {
				vertex = wds.graph.getVertex(i);
				nextList = vertex.getNext();
				nextWList = vertex.getWNext();
				if (nextList != null) {
					sumWBackLink = 0;
					for (j = 0; j < nextWList.size(); j++) {
						vertex = nextList.get(j);
						int ww = nextWList.get(j);
						int temp = vertex.index;
						forwardCount = vertex.getForwardCount();
						if (forwardCount != 0)
							sumWBackLink += wds.wBack.get(temp) * ww
									/ forwardCount;
					}
					newW = (1 - dN) + dN * sumWBackLink;
					wds.w.set(i, newW);
				}
			}
			if (isCover(wds) == true) {
				break;
			}
			toBackW(wds);
		}
		return wds;
	}

	/**
	 * 对权重值进行标准化处理
	 * @param wds 数据集
	 * @return
	 */
	public ArrayList<Integer> normalized(WDataSet wds) {
		ArrayList<Integer> wNormalized = new ArrayList<Integer>();
		double max, min, wNDouble;
		int i, wNormalInt;
		double wNormal;
		max = Collections.max(wds.w);
		min = Collections.min(wds.w);

		if (max != min)
			for (i = 0; i < wds.graph.getNVerts(); i++) {
				wNDouble = wds.w.get(i);
				wNormal = (wNDouble - min) / (max - min);
				wNormalInt = (int) (100 * wNormal);
				wds.w.set(i, wNormal);
				wNormalized.add(wNormalInt);
			}
		else
			for (i = 0; i < wds.graph.getNVerts(); i++)
				wNormalized.add(100);
		return wNormalized;
	}
	
	public ArrayList<Double> doubleNormalized(WDataSet wds){
		ArrayList<Double> wDoubleNormalized=new ArrayList<Double>();
		double max, min, wNDouble;
		double wNormal;
		max = Collections.max(wds.w);
		min = Collections.min(wds.w);
		if (max != min)
			for (int index = 0; index < wds.graph.getNVerts(); index++) {
				wNDouble = wds.w.get(index);
				wNormal = (wNDouble - min) / (max - min);
				wds.w.set(index, wNormal);
				wDoubleNormalized.add(wNormal);
			}
		else
			for (int index = 0; index < wds.graph.getNVerts(); index++)
				wDoubleNormalized.add(1.0);
		return wDoubleNormalized;
	}

	public LinkedHashMap<String, Double> selectTop(int selectCount,WDataSet wds) {
		int i, j, index;
		double max;
		LinkedHashMap<String, Double> mapList = new LinkedHashMap<String, Double>();

		if (wds.graph.getNVerts() == 0)
			return mapList;

		ArrayList<Double> wNormalized = doubleNormalized(wds);
		toBackW(wds);

		int temp = wds.subList.size();
		if (selectCount > temp)
			selectCount = temp;

		for (j = 0; j < selectCount; j++) {
			max = -1.0;
			index = -1;
			for (i = 0; i < wds.graph.getNVerts(); i++) {
				if (wds.wBack.get(i) > max) {
					max = wds.wBack.get(i);
					index = i;
				}
			}
			if (index != -1) {
				if(wNormalized.get(index)!=0){
					mapList.put(wds.graph.getVertex(index).getId(),wNormalized.get(index));
				}
				wds.wBack.set(index, -2.0);
			}
		}
		return mapList;
	}

	public LinkedHashMap<String, Integer> selectIntegerTop(int selectCount,WDataSet wds){
		int i, j, index;
		double max;
		LinkedHashMap<String, Integer> mapList = new LinkedHashMap<String, Integer>();

		if (wds.graph.getNVerts() == 0)
			return mapList;

		ArrayList<Integer> wNormalized = normalized(wds);
		toBackW(wds);

		int temp = wds.subList.size();
		if (selectCount > temp)
			selectCount = temp;

		for (j = 0; j < selectCount; j++) {
			max = -1.0;
			index = -1;
			for (i = 0; i < wds.graph.getNVerts(); i++) {
				if (wds.wBack.get(i) > max) {
					max = wds.wBack.get(i);
					index = i;
				}
			}
			if (index != -1) {
				mapList.put(wds.graph.getVertex(index).getId(),wNormalized.get(index));
				wds.wBack.set(index, -2.0);
			}
		}
		return mapList;
	}
	
	public WDataSet proceed(String[] words) {
		WDataSet wds1, wds2;
		wds1 = getWord(words);
		wds2 = mapInit(windowN, wds1);
		wds1 = cal(wds2);
		return wds1;
	}

	/**
	 * 返回权重值在0~1.0之间的double类型的关键词及其对应的权重
	 */
	public Map<String, Double> extract(String str, int num) {
		String[] words;
		if (tag != null)
			words = tag.tag2Array(str);// 这个语句可以换成其他的分词方法
		else
			words = str.split("\\s+");
		WDataSet wds = proceed(words);
		LinkedHashMap<String, Double> mapList = selectTop(num, wds);
		return mapList;
	}

	/**
	 * 返回权重值是0~100之间的int类型的关键词及其对应的权重
	 * @param content
	 * @param num
	 * @return
	 */
	public Map<String, Integer> extractIntNormal(String content,int num){
		String[] words;
		if(tag!=null){
			words=tag.tag2Array(content);
		}else{
			words=content.split("\\s+");
		}
		WDataSet wds=proceed(words);
		LinkedHashMap<String, Integer> map=selectIntegerTop(num, wds);
		return map;
	}
	
	@Override
	public String extract(String str, int num, boolean isWeighted) {
		return extract(str, num).toString();
	}
}
