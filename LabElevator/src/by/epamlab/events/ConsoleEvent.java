package by.epamlab.events;

import by.epamlab.Runner;
import by.epamlab.beans.Directions;
import by.epamlab.beans.Elevator;
import by.epamlab.beans.Floor;
import by.epamlab.beans.Passenger;
import by.epamlab.beans.TransportationActions;
import by.epamlab.beans.TransportationState;
import by.epamlab.events.AbstractEvent;

public class ConsoleEvent extends AbstractEvent {

	public ConsoleEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConsoleEvent(Passenger passenger, Elevator elevator) {
		super(passenger, elevator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		switch (state) {
		
		case ABORTING_TRANSPORTATION:
			System.out.println("operation do not supported");
			break;
			
		case BOARDING_OF_PASSENGER:
			Runner.log.info("BOARDING_OF_PASSENGER (" + passengerId
					+ " on floor-" + passengerStartFloor + ")");
			break;
			
		case COMPLETION_TRANSPORTATION:
			Runner.log.info("Validation start");
			boolean result = true;
			if (elevatorContainerSize == 0) {
				Runner.log.info("Elevator container is empty");
			} else {
				Runner.log.info("Elevator container is not empty");
				result = false;
			}
			if (state == TransportationActions.COMPLETION_TRANSPORTATION) {
				Runner.log.info("Elevator state is completed");
			} else {
				Runner.log.info("Elevator state is not completed");
				result = false;
			}
			for (Floor floor : Runner.getBuilding().getFloors()) {
				if (floor.getDispatchStoryContainer().size() == 0) {
					Runner.log.info("Dispatch container on floor#"
							+ floor.getFloor() + " is empty");
				} else {
					result = false;
					Runner.log.info("Dispatch container on floor#"
							+ floor.getFloor() + " conains "
							+ floor.getDispatchStoryContainer().size()
							+ " passengers");
				}
				Runner.log.info("Arrival container on the floor#"
						+ floor.getFloor() + " contains "
						+ floor.getArrivalStoryContainer().size()
						+ " passengers");
				for (Passenger passenger : floor.getArrivalStoryContainer()) {
					if ((passenger.getTransportationState() == TransportationState.COMPLETED)
							&& (passenger.getDestinationStory() == floor
									.getFloor())) {
						Runner.log.info("Passenger #" + passenger.getId()
								+ " on his target floor#" + floor.getFloor()
								+ " and his status is "
								+ passenger.getTransportationState());
					} else {
						result = false;
						Runner.log.info("Passenger #" + passenger.getId()
								+ " on the floor#" + floor.getFloor()
								+ " and his status is "
								+ passenger.getTransportationState());
					}
				}
			}
			if (Runner.getPassengersTransported() == Runner.PASSENGERS_NUMBER) {
				Runner.log.info("All transportation tasks are done");
			}
			if (result) {
				Runner.log.info("COMPLETION_TRANSPORTATION");
			} else {
				Runner.log.info("TRANSPORTATION_FAILED");
			}
			break;
			
		case DEBOARDING_OF_PASSENGER:
			Runner.log.info("DEBOARDING_OF_PASSENGER (" + passengerId
					+ " on floor-" + passengerStartFloor + ")");
			break;
		case MOVING_ELEVATOR: {
			if (elevatorDirection == Directions.UP) {
				Runner.log.info("MOVING_ELEVATOR (from floor-"
						+ elevatorCurrentFloor + " on floor-"
						+ (elevatorCurrentFloor + 1) + ")");
			} else {
				Runner.log.info("MOVING_ELEVATOR (from floor-"
						+ elevatorCurrentFloor + " on floor-"
						+ (elevatorCurrentFloor - 1) + ")");
			}
		}
			break;
			
		case STARTING_TRANSPORTATION:
			Runner.log.info("STARTING_TRANSPORTATION");
			break;
		}
	}

}
