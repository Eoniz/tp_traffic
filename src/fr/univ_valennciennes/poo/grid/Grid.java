package fr.univ_valennciennes.poo.grid;

import java.util.ArrayList;

public class Grid {
		
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	public static final int WIDTH = 6;
	public static final int HEIGHT = 6;
	
	public Grid() {	
	}
	
	private Arc addArc(Node origin, Node destination) {
		Arc a = new Arc(origin, destination);
		origin.addOutputArc(a);
		destination.addInputArc(a);
		
		return a;
	}
	
	public Node getNode(int x, int y) {
		for(Node n : nodes) {
			if(n.getX() == x && n.getY() == y)
				return n;
		}
		
		return null;
	}
	
	public void createRoads() {
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				if(i == 0 && (j == 0 || j == HEIGHT - 1)) continue;
				if(i == WIDTH - 1 && (j == 0 || j == HEIGHT - 1)) continue;
				
				nodes.add(new Node(i, j));
			}
		}
		
		for(int i = 0; i < WIDTH - 1; i++) {
			for(int j = HEIGHT - 1; j > 0; j--) {
				Node origin = getNode(i, j);
				if(origin == null) continue;
				
				if(j != 0 && j != 5) {
					Node destination = getNode(i + 1, j);
					if(destination == null) continue;
					
					addArc(origin, destination);
				}
				if(i != 0 && i != 5) {
					Node destination = getNode(i, j - 1);
					if(destination == null) continue;
					
					addArc(origin, destination);
				}
			}
		}
	}
	
	public Node addNode(Node node) {
		nodes.add(node);
		
		return node;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
}
