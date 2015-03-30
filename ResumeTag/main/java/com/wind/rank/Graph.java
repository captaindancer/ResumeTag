package com.wind.rank;

import java.util.ArrayList;

/**
 * 图模型
 * 
 * @author liufeng fileds:当前图中的顶点集合,图中的顶点个数
 */
public class Graph {
	private ArrayList<Vertex> vertexList = new ArrayList<Vertex>(); // 图中的顶点集合
	private int nVerts = 0;// 图中的顶点个数

	public ArrayList<Vertex> getVertexList() {
		return vertexList;
	}

	public int getNVerts() {
		return nVerts;
	}

	public Vertex getVertex(int index) {
		return vertexList.get(index);
	}

	public int getIndex(String id) {
		int index;
		for (index = 0; index < nVerts; index++)
			if (vertexList.get(index).getId() == id)
				break;
		if (index == nVerts)
			index = -1;
		return index;
	}

	public void addVertex(Vertex vertex) {
		vertex.setIndex(nVerts);
		vertexList.add(vertex);
		nVerts++;
	}

	// 为index等于start和end的两个顶点之间设立一条边
	public void addEdge(int start, int end) {
		Vertex vertexStart = vertexList.get(start);
		Vertex vertexEnd = vertexList.get(end);
		if (vertexStart.getNext() != null) {
			int index = vertexStart.getNext().indexOf(vertexEnd);
			// 如果vertex1中包含vertex2
			if (index != -1) {
				vertexStart.setWNext(index, 1);
			} else
				vertexStart.addVer(vertexEnd);
		} else {
			vertexStart.addVer(vertexEnd);
		}
		vertexList.get(end).addForwardCount(1);
	}
}
