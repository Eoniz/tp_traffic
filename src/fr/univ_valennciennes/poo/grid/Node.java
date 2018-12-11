package fr.univ_valennciennes.poo.grid;

import java.util.ArrayList;

import fr.univ_valennciennes.poo.traffic.Car;

public class Node {
	
	private double x;
	private double y;
	
	private ArrayList<Arc> inputs, outputs;
	private ArrayList<Car> cars;
	
	public Node(int x, int y) {
		this.x = x;
		this. y = y;
		
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
		this.cars = new ArrayList<>();
	}
	
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
	
	public double getX() { return (double) x; }
	public double getY() { return (double) y; }
	
	public final ArrayList<Arc> getInputs() { return inputs; }
	public final ArrayList<Arc> getOutputs() { return outputs; }
	
	public void addInputArc(Arc arc) { this.inputs.add(arc); }
	public void addOutputArc(Arc arc) { this.outputs.add(arc); }
	
	public final ArrayList<Car> getCars() { return cars; }
}
