package by.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

public class Building {
	private final int storiesNumber;
	private final int passengersNumber;
	private List<Floor> floors;
	private Elevator elevator;
	private ThreadGroup passengersThreads;

	public Building() {
		this(0, 0, 0);
	}

	public Building(int storiesNumber, int passengersNumber,
			int elevatorCapacity) {
		super();
		this.storiesNumber = storiesNumber;
		this.passengersNumber = passengersNumber;
		this.floors = new ArrayList<Floor>(storiesNumber);
		for (int i = 0; i < storiesNumber; i++) {
			floors.add(i, new Floor(i + 1));
		}
		this.elevator = new Elevator(elevatorCapacity);
		passengersThreads = new ThreadGroup("Passengers");
	}

	public int getStoriesNumber() {
		return storiesNumber;
	}

	public int getPassengersNumber() {
		return passengersNumber;
	}

	public List<Floor> getFloors() {
		return floors;
	}

	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}

	public Elevator getElevator() {
		return elevator;
	}
	
	public boolean isDispatchContainerEmpty(int floor){
		return floors.get(floor-1).getDispatchStoryContainer().isEmpty();
		
	}
	
	public Floor getFloor(int floor){
		return floors.get(floor-1);
	}

	public void setPassengersThreads(ThreadGroup passengersThreads) {
		this.passengersThreads = passengersThreads;
	}

	public ThreadGroup getPassengersThreads() {
		return passengersThreads;
	}
}
