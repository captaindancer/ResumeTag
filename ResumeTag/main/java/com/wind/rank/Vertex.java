package com.wind.rank;

import java.util.ArrayList;

/**
 * 图的顶点类
 * @author liufeng
 * fileds:当前顶点的词,当前顶点在图中的index号,当前顶点的权值
 */
public class Vertex {
	public String id; //当前顶点的词
	public int index;//当前顶点在图中的index号
	private int forwardCount = 0;  //进入当前顶点的顶点个数,由于是无向图,因此是与当前顶点相连接的顶点个数
	private ArrayList<Vertex> next = null;
	private ArrayList<Integer> wNext = null;
	
	public Vertex(String id){
		this.id = id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void addVer(Vertex ver){
		if(next == null){
			next = new ArrayList<Vertex>();
			wNext = new ArrayList<Integer>();
		}
		next.add(ver);
		wNext.add(1);
	}
	
	public ArrayList<Vertex> getNext(){
		return next;
	}
	
	public ArrayList<Integer> getWNext(){
		return wNext;
	}
	
	public void setWNext(int index, int wAdd){
		int w = wNext.get(index);
		wNext.set(index, w + wAdd);
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public void addForwardCount(int wAdd){
		forwardCount += wAdd;
	}
	
	public int getForwardCount(){
		return forwardCount;
	}
	
	public String vertexToString(){
		String s = id+ " " + String.valueOf(index)+ " " + String.valueOf(forwardCount);//+ " " + next.toString();
		return s;
	}
	
	@Override
	public String toString(){
		return id+ " " + String.valueOf(index)+ " " + String.valueOf(forwardCount);
	}
}
