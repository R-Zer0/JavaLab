package by.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

public class Floor {
	private final int floor;
	private List<Passenger> dispatchStoryContainer = new ArrayList<Passenger>();
	private List<Passenger> arrivalStoryContainer= new ArrayList<Passenger>();
	private Object elevatorWaitingFlag = new Object();
	private int passCounter;
	
	public Floor() {
		this(0);
	}

	public Floor(int floor) {
		super();
		this.floor = floor;
	}

	public List<Passenger> getDispatchStoryContainer() {
		return dispatchStoryContainer;
	}

	public void setDispatchStoryContainer(List<Passenger> dispatchStoryContainer) {
		this.dispatchStoryContainer = dispatchStoryContainer;
	}

	public List<Passenger> getArrivalStoryContainer() {
		return arrivalStoryContainer;
	}

	public void setArrivalStoryContainer(List<Passenger> arrivalStoryContainer) {
		this.arrivalStoryContainer = arrivalStoryContainer;
	}

	public int getFloor() {
		return floor;
	}

	public void setElevatorWaitingFlag(Object elevatorWaitingFlag) {
		this.elevatorWaitingFlag = elevatorWaitingFlag;
	}

	public Object getElevatorWaitingFlag() {
		return elevatorWaitingFlag;
	}

	public void setPassCounter() {
		this.passCounter = dispatchStoryContainer.size();
	}

	public synchronized int getPassCounter() {
		return passCounter;
	}
	
	public synchronized void changePassCounter() {
		this.passCounter--;
	}
}
