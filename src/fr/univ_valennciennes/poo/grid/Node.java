package fr.univ_valennciennes.poo.grid;

import java.util.ArrayList;

import fr.univ_valennciennes.poo.traffic.Car;

public class Node {
	
	/**
	 * the x position
	 */
	private double x;
	/**
	 * the y position
	 */
	private double y;
	
	/**
	 * The inputs arcs
	 */
	private ArrayList<Arc> inputs;
	/**
	 * The outputs arcs
	 */
	private ArrayList<Arc> outputs;
	
	/**
	 * The cars
	 */
	private ArrayList<Car> cars;
	
	public Node(int x, int y) {
		this.x = x;
		this. y = y;
		
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
		this.cars = new ArrayList<>();
	}
	
	/**
	 * Method for addind a car
	 * @param car : the car to add
	 * @return <b>Car</b> : the added car
	 */
	public Car addCar(Car car) {
		this.cars.add(car);
		
		if(this.cars.size() > 1) {
			for(Car c : cars) {
				for(Car cc : cars) {
					if(c.isHorizontal() != cc.isHorizontal()) {
						c.crashed();
						cc.crashed();
					}
				}
			}
		}
		
		return car;
	}
	
	/**
	 * Method for delete a car from the node
	 * @param car : the car to delete
	 * @return <b>Car</b> : the deleted car
	 */
	public Car removeCar(Car car) {
		this.cars.remove(car);
		
		return car;
	}
	
	@Override
	public String toString() {
		return "Node (" + x + ", " + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		
		Node node = (Node) obj;
		return (this.x == node.x && this.y == node.y);
	}
	
	/**
	 * Returns the x position
	 * @return <b>double</b> : the x position
	 */
	public double getX() { return (double) x; }
	/**
	 * Returns the y position
	 * @return <b>double</b> : the y position
	 */
	public double getY() { return (double) y; }
	
	/**
	 * Returns the inputs arcs
	 * @return <b>List Arc</b> : the inputs arcs
	 */
	public final ArrayList<Arc> getInputs() { return inputs; }
	/**
	 * Returns the outputs arcs
	 * @return <b>List Arc</b> : the outputs arcs
	 */
	public final ArrayList<Arc> getOutputs() { return outputs; }
	
	/**
	 * Method for adding an input arc to this node
	 */
	public void addInputArc(Arc arc) { this.inputs.add(arc); }
	/**
	 * Method for adding an output arc to this node
	 */
	public void addOutputArc(Arc arc) { this.outputs.add(arc); }
	
	/**
	 * Returns the cars in this node
	 * @return <b>List Car</b> : the cars in this node
	 */
	public final ArrayList<Car> getCars() { return cars; }
}
