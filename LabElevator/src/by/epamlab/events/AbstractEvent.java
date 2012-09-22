package by.epamlab.events;

import by.epamlab.Runner;
import by.epamlab.beans.Building;
import by.epamlab.beans.Directions;
import by.epamlab.beans.Elevator;
import by.epamlab.beans.Passenger;
import by.epamlab.beans.TransportationActions;

public abstract class AbstractEvent {

	protected TransportationActions state;
	protected int elevatorCurrentFloor;
	protected Directions elevatorDirection;
	protected int elevatorContainerSize;
	protected static int passengersTransported = 0;
	protected int passengerStartFloor;
	protected int passengerDestinationFloor;
	protected int passengerId;
	protected static Building building = Runner.getBuilding();

	public AbstractEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AbstractEvent(Passenger passenger, Elevator elevator) {
		super();
		if (passenger != null) {
			this.passengerStartFloor = passenger.getStartingFloor();
			this.passengerId = passenger.getId();
			this.passengerDestinationFloor = passenger.getDestinationStory();
		}
		this.state = elevator.getAction();
		this.elevatorContainerSize = elevator.getElevatorContainer().size();
		this.elevatorCurrentFloor = elevator.getCurrentFloor();
		this.elevatorDirection = elevator.getDirection();
	}

	public abstract void show();

	public void setState(TransportationActions state) {
		this.state = state;
	}

	public TransportationActions getState() {
		return state;
	}

	public int getElevatorCurrentFloor() {
		return elevatorCurrentFloor;
	}
}
