package fr.univ_valennciennes.poo.traffic;

import java.util.ArrayList;

import fr.univ_valennciennes.poo.grid.Arc;
import fr.univ_valennciennes.poo.grid.Node;

public class Car {
	
	private int id;
	private double x, y;
	
	private ArrayList<Node> path;
	private ArrayList<Node> remainingPath;
	
	private Node origin, destination, currentNode;
	
	private boolean 
		paused,
		arrived,
		stuck,
		isHorizontal,
		crashed;
	
	private static final int DELAY = 10;
	private int remainingTime = 0;
	
	public Car(int id, Node origin, Node destination) {
		this.id = id;
		this.origin = origin;
		this.destination = destination;
		this.currentNode = origin;
		
		this.currentNode.addCar(this);
		
		this.x = origin.getX();
		this.y = origin.getY();
		
		this.path = new ArrayList<>();
		this.remainingPath = new ArrayList<>();
		
		this.paused = this.arrived = this.stuck = this.crashed = false;
		
		this.pathFinder();
	}
	
	public void pathFinder() {
		this.isHorizontal = (this.origin.getY() == this.destination.getY());
		Node next = origin;
		
		while(!next.equals(destination)) {
			ArrayList<Arc> outputs = next.getOutputs();
			if(!outputs.isEmpty()) {
				for(Arc arc : outputs) {
					next = arc.getEnd();
					
					if(next == null) System.err.println("Error for the next node for the arc : " + arc);
					if(isHorizontal && next.getY() == destination.getY()) break;
					if(!isHorizontal && next.getX() == destination.getX()) break;
				}
				
				path.add(next);
			} else {
				break;
			}
		}
		
		for(Node node : path) {
			remainingPath.add(node);
		}
	}
	
	public Node getNextNode() {
		if(currentNode != null)
			currentNode.removeCar(this);
		
		if(!remainingPath.isEmpty()) {
			Node next = remainingPath.remove(0);
			currentNode = next;
			next.addCar(this);
			
			return next;
		}
			
		this.arrived = true;
		return null;
	}
	
	public boolean checkIfStucked() {
		if(remainingPath.isEmpty())
			return false;
		
		Node next = remainingPath.get(0);
		boolean stucked = false;

		for(Car car : next.getCars()) {
			if(car.isHorizontal == this.isHorizontal) {
				stucked = true;
				break;
			}
		}
		
		this.stuck = stucked;
		
		return stucked;
	}
	
	public boolean updateTime() {
		return (remainingTime++) >= DELAY;
	}
	
	public void crashed() {
		this.crashed = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public ArrayList<Node> getPath() {
		return path;
	}

	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}

	public ArrayList<Node> getRemainingPath() {
		return remainingPath;
	}

	public void setRemainingPath(ArrayList<Node> remainingPath) {
		this.remainingPath = remainingPath;
	}

	public Node getOrigin() {
		return origin;
	}

	public void setOrigin(Node origin) {
		this.origin = origin;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean pause) {
		this.paused = pause;
	}

	public boolean isArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public boolean isStuck() {
		return stuck;
	}

	public void setStuck(boolean stuck) {
		this.stuck = stuck;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public static int getDelay() {
		return DELAY;
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public void setHorizontal(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}

	public boolean isCrashed() {
		return crashed;
	}

	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
	}
	
	
	
	
}
