package fr.univ_valennciennes.poo.grid;

import fr.univ_valennciennes.poo.traffic.TrafficManager;

public class Arc {
	
	/**
	 * The start node
	 */
	private Node start;
	
	/**
	 * The end node
	 */
	private Node end;
	
	private Node[] subNodes;
	
	public Arc(Node start, Node end) {
		this.start = start;
		this.end = end;
		this.subNodes = new Node[] {};
	}
	
	public Arc(Node start, Node end, int nbSubnodes) {
		this.start = start;
		this.end = end;
		this.subNodes = new Node[nbSubnodes];
		
		double stepX = (end.getX() - start.getX()) / (nbSubnodes + 1);
		double stepY = (end.getY() - start.getY()) / (nbSubnodes + 1);
		
		Node previous = start;
		for(int i = 1; i <= nbSubnodes; i++) {
			double xx = start.getX() + (stepX * i);
			double yy = start.getY() + (stepY * i);
			
			Node node = new Node(xx, yy, true);
			this.subNodes[i - 1] = node;
			
			Grid.INSTANCE.addArc(previous, node);
			Grid.INSTANCE.addNode(node);
			
			previous = node;
		}
		
		Grid.INSTANCE.addArc(previous, end);
	}
	
	/**
	 * Return the start node
	 * @return <b>Node</b> the start node
	 */
	public Node getStart() { return start; }
	
	/**
	 * Return the end node
	 * @return <b>Node</b> the end node
	 */
	public Node getEnd() { return end; }
}
