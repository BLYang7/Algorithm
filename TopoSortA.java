package com.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 拓扑排序，使用Kahn算法来实现
 * 当前实现方案是在节点中加入一个子节点集，用来确定边
 */
public class TopoSortA {
	
	/**
	 * 拓扑排序节点类
	 */
	private static class Node {
		public Object val;
		public Set<Node> child = new HashSet<Node>();
		public int pathIn = 0;
		public Node(Object val){
			this.val = val;
		}
	}
	
	/**
	 * 拓扑图类
	 */
	private static class Graph {
		// 图中节点的集合
		public Set<Node> vertexSet = new HashSet<Node>();
		
		//将节点加入图中
		public boolean addNode(Node start, Node end){
			if(!vertexSet.contains(start)){
				vertexSet.add(start);
			}
			if(!vertexSet.contains(end)){
				vertexSet.add(end);
			}
			start.child.add(end);
			end.pathIn++;
			return true;
		}
	}
	
	
	//Kahn算法做拓扑排序
	private static class KahnTopo {
		private List<Node> result; // 用来存储结果集
		private Queue<Node> setOfZeroIndegree; // 用来存储入度为0的顶点
		private Graph graph;

		//构造函数，初始化
		public KahnTopo(Graph di) {
			this.graph = di;

			this.result = new ArrayList<Node>();
			this.setOfZeroIndegree = new LinkedList<Node>();

			// 对入度为0的集合进行初始化
			for(Node iterator : this.graph.vertexSet){
				if(iterator.pathIn == 0){
					this.setOfZeroIndegree.add(iterator);
				}
			}
		}

		//排序的处理
		private void process() {
			while (!setOfZeroIndegree.isEmpty()) {
				Node v = setOfZeroIndegree.poll();
				
				// 将当前顶点添加到结果集中
				result.add(v);
				
				// 遍历由v引出的所有边
				for (Node w : v.child ) {
					// 将该边从图中移除，通过减少边的数量来表示
					w.pathIn--;
					if (0 == w.pathIn) // 如果入度为0，那么加入入度为0的集合
					{
						setOfZeroIndegree.add(w);
					}
				}
				this.graph.vertexSet.remove(v);
			}
			
			// 如果此时图中还存在边，那么说明图中含有环路
			if (!this.graph.vertexSet.isEmpty()) {
				throw new IllegalArgumentException("Has Cycle !");
			}
		}

		//结果集
		public Iterable<Node> getResult() {
			return result;
		}
	}
	
	//测试
	public static void main(String[] args) {
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		Node F = new Node("F");
		
		Graph graph = new Graph();
		graph.addNode(A, B);
		graph.addNode(B, C);
		graph.addNode(B, D);
		graph.addNode(D, C);
		graph.addNode(E, C);
		graph.addNode(C, F);
		
		KahnTopo topo = new KahnTopo(graph);
		topo.process();
		for(Node temp : topo.getResult()){
			System.out.print(temp.val.toString() + "-->");
		}
	}

	

}
