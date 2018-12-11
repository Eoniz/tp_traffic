package fr.univ_valennciennes.poo.traffic;

import java.util.ArrayList;
import java.util.Random;

import fr.univ_valennciennes.poo.grid.Grid;

public class TrafficManager {
	private ArrayList<Car> cars;
	private Random random;
	private Grid grid;
	
	private static int CAR_ID = 0;
	
	
	public TrafficManager(Grid grid) {
		cars = new ArrayList<>();
		random = new Random();
		
		this.grid = grid;
		
		addCar();
		addCar();
	}
	
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
	
	public void removeCar(Car car) {
		if(car.getCurrentNode() != null)
			car.getCurrentNode().removeCar(car);
		cars.remove(car);
	}

	public Car findCarById(int id) {
		for(Car car : cars) {
			if(car.getId() == id)
				return car;
		}
		
		return null;
	}
	
	public ArrayList<Car> getCars() {
		return cars;
	}

	public void setCars(ArrayList<Car> cars) {
		this.cars = cars;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public static int getCAR_ID() {
		return CAR_ID;
	}

	public static void setCAR_ID(int cAR_ID) {
		CAR_ID = cAR_ID;
	}
	
	
}
