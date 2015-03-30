package com.wind.trietree;

import java.util.HashMap;
import java.util.Map;


/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Sep 4, 2014  9:57:55 AM
 *@Description
 */
public class KeywordDetection {
	public static void main(String[] args) {
		TrieTree trieTree=new TrieTree();
		TrieNode trieNode=trieTree.getTrieNode();
		System.out.println(trieNode.childs.size());
		System.out.println("..."+trieNode.key);
		Map<String, String> testMap=new HashMap<String, String>();
		System.out.println(testMap.isEmpty());
		testMap.put("a", "1");
		System.out.println(testMap.isEmpty());
	}
}
