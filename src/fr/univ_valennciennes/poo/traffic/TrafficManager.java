package fr.univ_valennciennes.poo.traffic;

import java.util.ArrayList;
import java.util.Random;

import fr.univ_valennciennes.poo.grid.Grid;

public class TrafficManager {
	
	public static final TrafficManager INSTANCE = new TrafficManager(Grid.INSTANCE);
	
	/**
	 * The cars
	 */
	private ArrayList<Car> cars;
	
	/**
	 * Random number generator
	 */
	private Random random;
	
	/**
	 * The current grid of nodes
	 */
	private Grid grid;
	
	/**
	 * Allows us to give a unique id for each cars
	 */
	private static int CAR_ID = 0;
	
	
	public TrafficManager(Grid grid) {
		cars = new ArrayList<>();
		random = new Random();
		
		this.grid = grid;
	}
	
	/**
	 * Add a car in the traffic
	 * @return <b>Car</b> : the added car
	 */
	public Car addCar() {
		Car car = null;
		boolean isHorizontal = random.nextBoolean();
	
		if(isHorizontal) {
			int yy = 1 + random.nextInt(4);
			if(this.grid.getNode(0, yy).getCars().size() > 0)
				return null;
			
			car = new Car(CAR_ID++, this.grid.getNode(0, yy), this.grid.getNode(5, yy));
		} else {
			int xx = 1 + random.nextInt(4);
			if(this.grid.getNode(xx, 0).getCars().size() > 0)
				return null;
			
			car = new Car(CAR_ID++, this.grid.getNode(xx, 5), this.grid.getNode(xx, 0));
		}
		
		cars.add(car);
		
		return car;
	}
	
	/**
	 * Removes a car from the traffic
	 * @param car : the car to del
	 */
	public void removeCar(Car car) {
		if(car.getCurrentNode() != null)
			car.getCurrentNode().removeCar(car);
		cars.remove(car);
	}
	
	/**
	 * Finds a car by id
	 * @param id : the car's id
	 * @return <b>Car</b> : the car if found, <b>null</b> otherwise
	 */
	public Car findCarById(int id) {
		for(Car car : cars) {
			if(car.getId() == id)
				return car;
		}
		
		return null;
	}
	
	/** GETTERS */
	
	public ArrayList<Car> getCars() {
		return cars;
	}

	public Random getRandom() {
		return random;
	}

	public Grid getGrid() {
		return grid;
	}

	public static int getCAR_ID() {
		return CAR_ID;
	}	
	
}
