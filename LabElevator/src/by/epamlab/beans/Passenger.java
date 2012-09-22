package by.epamlab.beans;

public class Passenger {
	private final int destinationStory;
	private final int passengerID;
	private final int startingFloor;
	private TransportationState transportationState;
	private final Directions direction;

	public Passenger() {
		this(0, 0, 0);
	}

	public Passenger(int destinationStory, int passengerID, int startingFloor) {
		super();
		this.destinationStory = destinationStory;
		this.passengerID = passengerID;
		this.startingFloor = startingFloor;
		this.transportationState = TransportationState.NOT_STARTED;
		if (destinationStory > startingFloor) {
			this.direction = Directions.UP;
		} else {
			this.direction = Directions.DOWN;
		}
	}

	public int getDestinationStory() {
		return destinationStory;
	}

	public int getId() {
		return passengerID;
	}

	public TransportationState getTransportationState() {
		return transportationState;
	}

	public void setTransportationState(TransportationState transportationState) {
		this.transportationState = transportationState;
	}

	public Directions getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return destinationStory + ";" + passengerID + ";"
				+ transportationState + ";" + direction;
	}

	public int getStartingFloor() {
		return startingFloor;
	}

}
