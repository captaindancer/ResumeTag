package com.wind.rank;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Feb 18, 2014  5:42:52 PM
 *@Description IKanalyzer分词器的实现
 */
public class IKanalyzerTagger extends AbstractTagger {

	private boolean useSmart=true;
	
	public boolean isUseSmart() {
		return useSmart;
	}

	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;
	}

	public IKanalyzerTagger(){
		
	}
	
	@Override
	public Object tag(String src) {
		return null;
	}

	@Override
	public String[] tag2Array(String src) {
		List<String> tagList=new ArrayList<String>();
		StringReader stringReader=new StringReader(src);
		IKSegmenter ikSegmenter = new IKSegmenter(stringReader, useSmart);
		Lexeme lexeme = null;
		try {
			while((lexeme = ikSegmenter.next()) != null&&!(lexeme.getLexemeText()).contains("的")){
				tagList.add(lexeme.getLexemeText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		stringReader.close();
		return (String[]) tagList.toArray(new String[tagList.size()]);
	}

}
