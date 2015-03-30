package com.wind.rank;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import love.cq.domain.Forest;

import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.ToAnalysis;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Feb 20, 2014  9:23:50 AM
 *@Description Ansj分词器的实现
 */
public class AnsjTagger extends AbstractTagger {

	public AnsjTagger(){
		
	}
	
	public AnsjTagger(String dicPath){
		Forest forest = new Forest();
        UserDefineLibrary.loadLibrary(forest, dicPath);
	}
	@Override
	public Object tag(String src) {
		return null;
	}

	@Override
	public String[] tag2Array(String src) {
		List<String> tagList=new ArrayList<String>();
		StringReader stringReader=new StringReader(src);
		Analysis analysis=new ToAnalysis(stringReader);
		Term term=null;
	    try {
			while(((term=analysis.next())!=null)&&!(term.toString()).contains("的")){
				tagList.add(term.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String[]) tagList.toArray(new String[tagList.size()]);
	}

}
