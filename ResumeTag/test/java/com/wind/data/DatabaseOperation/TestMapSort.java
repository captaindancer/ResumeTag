package com.wind.data.DatabaseOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 7, 2014  5:06:07 PM
 *@Description
 */
public class TestMapSort {

	@Test
	public void test() {
		Map<String, Double> wordvaluemap=new HashMap<String, Double>();
		wordvaluemap.put("abc", 1.0);
		wordvaluemap.put("we", 2.0);
		wordvaluemap.put("royal", 1.8);
		wordvaluemap.put("edg", 3.4);
		wordvaluemap.put("ig", 5.0);
		wordvaluemap.put("omg", 3.0);
		System.out.println(wordvaluemap);
		List<Map.Entry<String, Double>> pairList = new ArrayList<Map.Entry<String, Double>>(
				wordvaluemap.entrySet());
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
		for(int index=0;index<pairList.size();index++)
			System.out.println(pairList.get(index));
	}

}
