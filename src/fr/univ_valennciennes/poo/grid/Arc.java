package fr.univ_valennciennes.poo.grid;

public class Arc {
	
	private Node start, end;
	
	public Arc(Node start, Node end) {
		this.start = start;
		this.end = end;
	}
	
	public Node getStart() { return start; }
	public Node getEnd() { return end; }
}
