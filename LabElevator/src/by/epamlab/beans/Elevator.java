package by.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

import by.epamlab.constants.Constants;


public class Elevator {
	private final int capacity;
	private int currentFloor;
	private List<Passenger> elevatorContainer;
	private Directions direction;
	private TransportationActions action;

	public Elevator() {
		this(0);
		// TODO Auto-generated constructor stub
	}

	public Elevator(int capacity) {
		super();
		this.capacity = capacity;
		this.currentFloor = Constants.STARTING_FLOOR;
		this.elevatorContainer = new ArrayList<Passenger>();
		this.setDirection(Directions.UP);
		this.setAction(TransportationActions.STARTING_TRANSPORTATION);
	}

	public int getCapacity() {
		return capacity;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public List<Passenger> getElevatorContainer() {
		return elevatorContainer;
	}

	public boolean addPassenger(Passenger passenger){
		if (isFull()){
			return false;
		} else {
			elevatorContainer.add(passenger);
			return true;
		}
	}
	
	public boolean removePassenger(Passenger passenger){		
			elevatorContainer.remove(passenger);
			return true;
	}

	public void setDirection(Directions direction) {
		this.direction = direction;
	}

	public Directions getDirection() {
		return direction;
	}

	public boolean isFull() {
		if (capacity == elevatorContainer.size()) {
			return true;
		} else {
			return false;
		}
	}

	public void setAction(TransportationActions action) {
		this.action = action;
	}

	public TransportationActions getAction() {
		return action;
	}

	public void changeDirection(){
		if (direction == Directions.UP){
			this.direction = Directions.DOWN;
		} else {
			this.direction = Directions.UP;
		}
	}
	public boolean isNeedDeboarding(int currentFloor){
		for (Passenger passenger : elevatorContainer) {
			if (passenger.getDestinationStory() == currentFloor) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return capacity + ";" + currentFloor + ";" + elevatorContainer + ";"
				+ direction + ";" + action;
	}
	
	
}
