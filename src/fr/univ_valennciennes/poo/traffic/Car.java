package fr.univ_valennciennes.poo.traffic;

import java.util.ArrayList;

import fr.univ_valennciennes.poo.grid.Arc;
import fr.univ_valennciennes.poo.grid.Node;

public class Car {
	
	/**
	 * The car's id
	 */
	private int id;
	/**
	 * The car's x position
	 */
	private double x;
	/**
	 * The car's y position
	 */
	private double y;
	
	/**
	 * The path
	 */
	private ArrayList<Node> path;
	/**
	 * The remaining path
	 */
	private ArrayList<Node> remainingPath;
	
	/**
	 * The car's origin
	 */
	private Node origin;
	/**
	 * The car's destination
	 */
	private Node destination;
	/**
	 * The car's current node
	 */
	private Node currentNode;
	
	private boolean 
		paused, // Is it paused ?
		arrived, // Is it arrived ?
		stuck, // Is it stucked ?
		isHorizontal, // Is it going horizontally ?
		crashed, // Is it crashed ?
		dirty; // Needs to be del
	
	/**
	 * The delay before removing the car of the node
	 */
	private static final int DELAY = 10;
	
	/**
	 * The remaining time before removing the car of the node
	 */
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
		
		this.paused = this.arrived = this.stuck = this.crashed = this.dirty = false;
		
		this.pathFinder();
	}
	
	/**
	 * Method for generating the path
	 */
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
	
	/**
	 * Returns the next node from the path
	 * @return <b>Node</b> : the next node
	 */
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
	
	/**
	 * Returns true if the car's stucked
	 * @return <b>boolean</b> : is the car stucked ?
	 */
	public boolean checkIfStucked() {
		if(remainingPath.isEmpty())
			return false;
		
		Node next = remainingPath.get(0);
		boolean stucked = false;

		for(Car car : next.getCars()) {
			if(car.isHorizontal == this.isHorizontal || car.isCrashed()) {
				stucked = true;
				break;
			}
		}
		
		this.stuck = stucked;
		
		return stucked;
	}
	
	/**
	 * Method for updating the remaining time if the car's crashed
	 * @return <b>boolean</b> : true when the remaining time is >= DELAY
	 */
	public boolean updateTime() {
		if(!crashed) return false;
		
		return (remainingTime++) >= DELAY;
	}
	
	
	/** GETTERS & SETTERS */
	public void crashed() {
		this.crashed = true;
	}

	public void remove() {
		this.dirty = true;
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
	
	public double getY() {
		return y;
	}


	public ArrayList<Node> getPath() {
		return path;
	}


	public ArrayList<Node> getRemainingPath() {
		return remainingPath;
	}

	public Node getOrigin() {
		return origin;
	}

	public Node getDestination() {
		return destination;
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
	
	public boolean isDirty() {
		return dirty;
	}

	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
	}
	
	
	
	
}
