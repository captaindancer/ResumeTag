package com.wind.trietree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 9, 2014  7:56:59 PM
 *@Description
 */
public class TrieTree {

	private final TrieNode trieNode=new TrieNode();
	private static final String MAIN_DICT = "/test/dict.txt";
	
	public TrieTree(){
		loadDict();
	}
	
	public TrieNode getTrieNode(){
		return this.trieNode;
	}
	
	public void loadDict() {
        InputStream is = this.getClass().getResourceAsStream(MAIN_DICT);
        System.out.println(MAIN_DICT);
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            long s = System.currentTimeMillis();
            while (br.ready()) {
                String line = br.readLine();
                String[] tokens = line.split("[\t ]+");
                if (tokens.length < 2) continue;

                String word = tokens[0];
                word = addWord(word);
            }
            System.out.println(String.format("main dict load finished, time elapsed %d ms",
                    System.currentTimeMillis() - s));
        } catch (IOException e) {
            System.err.println("%s load failure!");
        } finally {
            try {
                if (null != is) is.close();
            } catch (IOException e) {
                System.err.println("%s close failure!");
            }
        }
    }
	
	private String addWord(String word) {
	        TrieNode p = this.trieNode;
	        StringBuilder r = new StringBuilder();
	        for (char ch : word.toCharArray()) {
	            ch = CharacterUtil.regularize(ch);
	            r.append(ch);
	            if (ch == ' ') continue;
	            TrieNode pChild = p.childs.get(ch);
	            System.out.println(pChild);
	            if (pChild == null) {
	                pChild = new TrieNode();
	                p.childs.put(ch, pChild);
	            }
	            System.out.println(p);
	            p = pChild;
	            System.out.println("..."+p);
	        }
	        p.childs.put(' ', null);
	        return r.toString();
	 }
	
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
