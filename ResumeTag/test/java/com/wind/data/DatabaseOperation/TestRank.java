package com.wind.data.DatabaseOperation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import com.wind.rank.AbstractExtractor;
import com.wind.rank.WordExtract;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 25, 2014  8:39:39 AM
 *@Description
 */
public class TestRank {

	@Test
	public void test() throws Exception {
		AbstractExtractor extractor=null;
		Properties properties = new Properties();
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/segmentor.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e1) {
			throw new ExceptionInInitializerError("cannot load the config file");
		}
		String dictionaryPath=null;
		String taggerPath=null;
		String ansjDic=null;
		if(1==Integer.parseInt(properties.getProperty("segPath"))&&1==Integer.parseInt(properties.getProperty("dicPath"))){
			taggerPath = this.getClass().getResource("/seg.m").toString();
			taggerPath=taggerPath.substring(taggerPath.indexOf("/"));
			dictionaryPath = this.getClass().getResource("/dictionary/dictionary.txt").toString();
			dictionaryPath = dictionaryPath.substring(dictionaryPath.indexOf("/"));
		}
		if(1==Integer.parseInt(properties.getProperty("segPath"))&&1!=Integer.parseInt(properties.getProperty("dicPath"))){
			taggerPath = this.getClass().getResource("/seg.m").toString();
			taggerPath=taggerPath.substring(taggerPath.indexOf("/"));
		}
		//TODO
		if(1==Integer.parseInt(properties.getProperty("ansjDic"))){
			dictionaryPath = this.getClass().getResource("/ansj.dic").toString();
			dictionaryPath = dictionaryPath.substring(dictionaryPath.indexOf("/"));
		}
		String stopwordsPath = this.getClass().getResource("/stopwords").toString();
		stopwordsPath=stopwordsPath.substring(stopwordsPath.indexOf("/"));
		extractor=new WordExtract(taggerPath, dictionaryPath, stopwordsPath);
		String textContent="阿里巴巴集团校园招聘:今天给同学们介绍的技术大牛，有人说他是淘宝的“扫地僧”~他就是蔡景现，花名多隆，集团核心系统研发部技术大牛";
		System.out.println(extractor.extract(textContent, 10));
	}

}
