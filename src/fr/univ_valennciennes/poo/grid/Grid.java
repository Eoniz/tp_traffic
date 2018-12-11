package fr.univ_valennciennes.poo.grid;

import java.util.ArrayList;

public class Grid {
	
	/**
	 * The list of nodes
	 */
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	/**
	 * The grid width
	 */
	public static final int WIDTH = 6;
	
	/**
	 * The grid height
	 */
	public static final int HEIGHT = 6;
	
	public Grid() {	}
	
	/**
	 * Add an arc between the origin node and the destination node
	 * @param origin : the origin node
	 * @param destination : the destination node
	 * @return <b>Arc</b> the created arc
	 */
	private Arc addArc(Node origin, Node destination) {
		Arc a = new Arc(origin, destination);
		origin.addOutputArc(a);
		destination.addInputArc(a);
		
		return a;
	}
	
	/**
	 * Get the node in the position (x, y)
	 * @param x : x position
	 * @param y : y position
	 * @return <b>Node</b> : the node
	 */
	public Node getNode(int x, int y) {
		for(Node n : nodes) {
			if(n.getX() == x && n.getY() == y)
				return n;
		}
		
		return null;
	}
	
	/**
	 * Method for generating all the nodes and the arcs
	 */
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
	
	/**
	 * Add an arc to the grid
	 * @param node : the node to add
	 * @return <b>Node</b> the added node
	 */
	public Node addNode(Node node) {
		nodes.add(node);
		
		return node;
	}
	
	/**
	 * Returns the nodes array
	 * @return <b>List Node</b> : the nodes
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}
}
