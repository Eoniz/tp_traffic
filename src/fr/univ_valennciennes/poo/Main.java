package fr.univ_valennciennes.poo;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.univ_valennciennes.poo.grid.Arc;
import fr.univ_valennciennes.poo.grid.Grid;
import fr.univ_valennciennes.poo.grid.Node;
import fr.univ_valennciennes.poo.traffic.Car;
import fr.univ_valennciennes.poo.traffic.TrafficManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application implements EventHandler<MouseEvent> {
	
	/**
	 * The offset
	 */
	private static final double offset = 100d;
	
	/**
	 * The window's width
	 */
	private static final double width = 500d;
	
	/**
	 * The window's height
	 */
	private static final double height = 400d;
	
	/**
	 * The delay (tempo)
	 */
	private static final int tempo = 500;
	
	/**
	 * The grid
	 */
	private Grid grid;
	/**
	 * The traffic manager
	 */
	private TrafficManager trafficManager;
	
	/**
	 * The cars renderer
	 */
	private ArrayList<CarRendering> carsRendering;
	
	@Override
	public void handle(MouseEvent arg0) {
		Object o = arg0.getSource();
		if(o instanceof CarRendering) {
			CarRendering cr = (CarRendering) o;
			Car car = trafficManager.findCarById(cr.getNo());
			if(car != null) {
				boolean b = car.isPaused();
				car.setPaused(!b);
				cr.switchSelected();
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		grid = new Grid();
		grid.createRoads();
		trafficManager = new TrafficManager(grid);
		carsRendering = new ArrayList<>();
		
		render(primaryStage);
	}
	
	/**
	 * Method for creating the stage, init the tick cycle & render the roads & the cars
	 * @param stage : the stage
	 */
	private void render(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 2*offset + width, 2*offset + height, Color.ANTIQUEWHITE);
		
		scene.setFill(Color.DARKGRAY);
		renderGame(root);
		
		stage.setTitle("Traffic");
		stage.setScene(scene);
		stage.show();
		
		int[] tick = {0};
		Timeline startCycle = new Timeline(new KeyFrame(Duration.millis(5000),
				e -> {
					Timeline cycle = new Timeline(new KeyFrame(Duration.millis(tempo),
							event -> {
								moveCars();
								if(tick[0]++ % 3 == 0) {
									for(int i = 0; i < 2; i++) {
										Car car = trafficManager.addCar();
										if(car != null)
											renderCar(root, car);
									}
								}
							}));
					cycle.setCycleCount(Timeline.INDEFINITE);
					cycle.play();
				}));
		startCycle.setCycleCount(1);
		startCycle.play();
	}
	
	/**
	 * Method for rendering the roads
	 * @param root : the root
	 */
	private void renderGame(Group root) {
		List<Node> nodes = grid.getNodes();
		
		double radius = 1 * (50d / 3);
		for(Node node : nodes) {
			double cx = offset + node.getX() * width / Grid.WIDTH;
			double cy = offset + node.getY() * height / Grid.HEIGHT;
			
			Circle c = new Circle(cx, cy, radius);
			c.setFill(Color.BLACK);
			c.setOpacity(0.2);
			
			root.getChildren().add(c);
			c.setSmooth(true);
		}
		
		for(Node node : nodes) {
			double ox = offset + node.getX() * width / Grid.WIDTH;
			double oy = offset + node.getY() * height / Grid.HEIGHT;
			
			for (Arc arc : node.getOutputs()) {
				Node dest = arc.getEnd();
				double dx = offset + dest.getX() * width / Grid.WIDTH;
				double dy = offset + dest.getY() * height / Grid.HEIGHT;
				Line l = new Line(ox, oy, dx, dy);
				l.setStrokeWidth(6);
				l.setStroke(Color.DARKGOLDENROD);
				root.getChildren().add(l);
			}
		}
		
		List<Car> cars = trafficManager.getCars();
		for(Car car : cars) {
			renderCar(root, car);
		}
	}
	
	/**
	 * Method for rendering a car
	 * @param root : the root
	 * @param car : the car to be render
	 */
	private void renderCar(Group root, Car car) {
		double cx = offset + car.getX() * width / Grid.WIDTH;
		double cy = offset + car.getY() * height / Grid.HEIGHT;
		
		CarRendering cr = new CarRendering(cx, cy, 0.1*width / grid.WIDTH, car.getId());
		root.getChildren().add(cr);
		cr.setSmooth(true);
		cr.setOnMouseClicked(this);
		
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		
		cr.setEffect(dropShadow);
		carsRendering.add(cr);
	}
	
	/**
	 * Method for moving cars
	 */
	private void moveCars() {
		List<Car> cars = trafficManager.getCars();
		List<Car> carsToDel = new ArrayList<>();
		
		for(int i = 0; i < cars.size(); i++) {
			Car car = cars.get(i);
			CarRendering cr = carsRendering.get(i);
			
			// Check if the car is stuck (accident in front)
			car.checkIfStucked(); 
			
			// If the car can move
			if(!car.isArrived() && !car.isPaused() && !car.isStuck() && !car.isCrashed()) {
				Node next = car.getNextNode();
				if(next != null) {
					if(!cr.selected) {
						Timeline timeline = new Timeline();
						
						double xdest = offset + next.getX() * width / Grid.WIDTH;
						double ydest = offset + next.getY() * height  / Grid.HEIGHT;
						
						KeyFrame moveCar = new KeyFrame(new Duration(tempo),
								new KeyValue(cr.centerXProperty(), xdest),
								new KeyValue(cr.centerYProperty(), ydest));
						timeline.getKeyFrames().add(moveCar);
						timeline.play();
						
						cr.setAnimation(timeline);
					}
				} else { // If no next node
					carsToDel.add(car);
				}
			} else { // If is arrived or crashed or stucked or paused
				if(car.isArrived()) // If arrived, remove it
					carsToDel.add(car);
				
				if(car.isCrashed()) { // If crashed, update the rendering & update the car's timer
					getCarRendering(car.getId()).crashed();
					if(car.updateTime()) { // If the car's timer is ok, remove the car
						carsToDel.add(car);
					}
				}
			}
		}
		
		// Remove each car to be deleted
		for(Car car : carsToDel) {
			CarRendering cr = getCarRendering(car.getId());
			cr.setVisible(false);
			
			carsRendering.remove(cr);
			trafficManager.removeCar(car);
		}
	}
	
	/**
	 * Method for getting the CarRendering via the car's id
	 * @param id : the car's id
	 * @return <b>CarRendering</b> : the car's CarRendering
	 */
	private CarRendering getCarRendering(int id) {
		CarRendering cr = null;
		Optional<CarRendering> opt = carsRendering.stream().filter(car -> car.getNo() == id).findFirst();
		if(opt.isPresent()) cr = opt.get();
		return cr;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
