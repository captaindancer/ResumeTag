package com.wind.trietree;

import java.util.HashMap;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 9, 2014  7:55:59 PM
 *@Description
 */
public class TrieNode {
    public char key = (char)0;

    public HashMap<Character, TrieNode> childs = new HashMap<Character, TrieNode>();
    
    public TrieNode() {this.key='#';}
    
    public TrieNode(char key) {
        this.key = key;
    }
}
