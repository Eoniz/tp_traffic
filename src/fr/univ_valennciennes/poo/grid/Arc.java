package fr.univ_valennciennes.poo.grid;

public class Arc {
	
	/**
	 * The start node
	 */
	private Node start;
	
	/**
	 * The end node
	 */
	private Node end;
	
	public Arc(Node start, Node end) {
		this.start = start;
		this.end = end;
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
