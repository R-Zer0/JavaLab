package by.epamlab.events;

import by.epamlab.Runner;
import by.epamlab.beans.Directions;
import by.epamlab.beans.Elevator;
import by.epamlab.beans.Floor;
import by.epamlab.beans.Passenger;
import by.epamlab.beans.TransportationActions;
import by.epamlab.beans.TransportationState;
import by.epamlab.constants.Constants;
import by.epamlab.view.BuildingView;
import by.epamlab.view.BuildingPanel;

public class FrameEvent extends AbstractEvent {

	public FrameEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FrameEvent(Passenger passenger, Elevator elevator) {
		super(passenger, elevator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		switch (state) {
		case ABORTING_TRANSPORTATION:

			break;
		case BOARDING_OF_PASSENGER:
			Runner.log.info("BOARDING_OF_PASSENGER (" + passengerId
					+ " on floor-" + passengerStartFloor + ")");
			BuildingView.getjTextLog().append(
					"BOARDING_OF_PASSENGER (" + passengerId
							+ " on floor-" + passengerStartFloor
							+ ")\n");
			repaintFloors();
			break;
		case COMPLETION_TRANSPORTATION:
			Runner.log.info("Validation start");
			BuildingView.getJButtonStart().setText("VIEW LOG FILE");
			BuildingView.getjTextLog().append("Validation start\n");
			boolean result = true;
			Elevator elevator = Runner.getBuilding().getElevator();
			if (elevator.getElevatorContainer().size() == 0) {
				BuildingView.getjTextLog().append(
						"Elevator container is empty\n");
				Runner.log.info("Elevator container is empty");
			} else {
				Runner.log.info("Elevator container is not empty");
				BuildingView.getjTextLog().append(
						"Elevator container is not empty");
				result = false;
			}
			if (elevator.getAction() == TransportationActions.COMPLETION_TRANSPORTATION) {
				Runner.log.info("Elevator state is completed");
				BuildingView.getjTextLog().append(
						"Elevator state is completed\n");
			} else {
				Runner.log.info("Elevator state is not completed");
				BuildingView.getjTextLog().append(
						"Elevator state is not completed\n");
				result = false;
			}
			for (Floor floor : Runner.getBuilding().getFloors()) {
				if (floor.getDispatchStoryContainer().size() == 0) {
					Runner.log.info("Dispatch container on floor#"
							+ floor.getFloor() + " is empty");
					BuildingView.getjTextLog().append(
							"Dispatch container on floor#" + floor.getFloor()
									+ " is empty\n");
				} else {
					result = false;
					Runner.log.info("Dispatch container on floor#"
							+ floor.getFloor() + " conains "
							+ floor.getDispatchStoryContainer().size()
							+ " passengers");
					BuildingView.getjTextLog().append(
							"Dispatch container on floor#" + floor.getFloor()
									+ " conains "
									+ floor.getDispatchStoryContainer().size()
									+ " passengers\n");
				}
				Runner.log.info("Arrival container on the floor#"
						+ floor.getFloor() + " contains "
						+ floor.getArrivalStoryContainer().size()
						+ " passengers");
				BuildingView.getjTextLog().append(
						"Arrival container on the floor#" + floor.getFloor()
								+ " contains "
								+ floor.getArrivalStoryContainer().size()
								+ " passengers\n");
				for (Passenger passenger : floor.getArrivalStoryContainer()) {
					if ((passenger.getTransportationState() == TransportationState.COMPLETED)
							&& (passenger.getDestinationStory() == floor
									.getFloor())) {
						Runner.log.info("Passenger #" + passenger.getId()
								+ " on his target floor#"
								+ floor.getFloor() + " and his status is "
								+ passenger.getTransportationState());
						BuildingView.getjTextLog().append(
								"Passenger #" + passenger.getId()
										+ " on his target floor#"
										+ floor.getFloor()
										+ " and his status is "
										+ passenger.getTransportationState()
										+ "\n");
					} else {
						result = false;
						Runner.log.info("Passenger #" + passenger.getId()
								+ " on the floor#" + floor.getFloor()
								+ " and his status is "
								+ passenger.getTransportationState());
						BuildingView.getjTextLog().append(
								"Passenger #" + passenger.getId()
										+ " on the floor#"
										+ floor.getFloor()
										+ " and his status is "
										+ passenger.getTransportationState()
										+ "\n");
					}
				}
			}
			if (Runner.getPassengersTransported() == Runner.PASSENGERS_NUMBER) {
				Runner.log.info("All transportation tasks are done");
				BuildingView.getjTextLog().append(
						"All transportation tasks are done\n");
			}
			if (result) {
				Runner.log.info("COMPLETION_TRANSPORTATION");
				BuildingView.getjTextLog()
						.append("COMPLETION_TRANSPORTATION\n");
			} else {
				Runner.log.info("TRANSPORTATION_FAILED");
				BuildingView.getjTextLog().append("TRANSPORTATION_FAILED\n");
			}

			break;
		case DEBOARDING_OF_PASSENGER:
			Runner.log.info("DEBOARDING_OF_PASSENGER (" + passengerId
					+ " on floor-" + passengerStartFloor + ")");
			BuildingView.getjTextLog().append(
					"DEBOARDING_OF_PASSENGER (" + passengerId
							+ " on floor-" + passengerStartFloor
							+ ")\n");
			passengersTransported++;
			repaintFloors();
			break;
		case MOVING_ELEVATOR: {
			if (elevatorDirection == Directions.UP) {
				Runner.log.info("MOVING_ELEVATOR (from floor-"
						+ elevatorCurrentFloor + " on floor-"
						+ (elevatorCurrentFloor + 1) + ")");
				BuildingView.getjTextLog().append(
						"MOVING_ELEVATOR (from floor-" + elevatorCurrentFloor
								+ " on floor-" + (elevatorCurrentFloor + 1)
								+ ")\n");
			} else {
				Runner.log.info("MOVING_ELEVATOR (from floor-"
						+ elevatorCurrentFloor + " on floor-"
						+ (elevatorCurrentFloor - 1) + ")");
				BuildingView.getjTextLog().append(
						"MOVING_ELEVATOR (from floor-" + elevatorCurrentFloor
								+ " on floor-" + (elevatorCurrentFloor - 1)
								+ ")\n");
			}
			repaintFloors();

		}
			break;
		case STARTING_TRANSPORTATION:
			BuildingPanel.getCurrenFloorNumber().setText(
					Integer.toString(Constants.STARTING_FLOOR));
			BuildingPanel.getDispatchPassengerSumm().setText(
					Integer.toString(Runner.PASSENGERS_NUMBER
							- passengersTransported));
			BuildingPanel.getTransportationProgress().setMaximum(
					Runner.PASSENGERS_NUMBER);
			BuildingPanel.getArrivalPassengerSumm().setText(
					Integer.toString(passengersTransported));

			BuildingPanel.getElevatorPassengerSumm().setText(
					Integer.toString(0));

			BuildingPanel
					.getStoreContainers()
					.get(Constants.ABOVE_DISPATCHER)
					.setText(
							Integer.toString(building.getFloor(2)
									.getDispatchStoryContainer().size()));
			BuildingPanel
					.getStoreContainers()
					.get(Constants.ABOVE_ARRIVAL)
					.setText(
							Integer.toString(building.getFloor(2)
									.getArrivalStoryContainer().size()));
			BuildingPanel
					.getStoreContainers()
					.get(Constants.CURRENT_DISPATCHER)
					.setText(
							Integer.toString(building.getFloor(1)
									.getDispatchStoryContainer().size()));
			BuildingPanel
					.getStoreContainers()
					.get(Constants.CURRENT_ARRIVAL)
					.setText(
							Integer.toString(building.getFloor(1)
									.getArrivalStoryContainer().size()));
			BuildingPanel.getStoreContainers().get(Constants.BELOW_DISPATCHER)
					.setText(Constants.NULL_FIELD);
			BuildingPanel.getStoreContainers().get(Constants.BELOW_ARRIVAL)
					.setText(Constants.NULL_FIELD);

			BuildingView.getjTextLog().append("STARTING_TRANSPORTATION\n");
			Runner.log.info("STARTING_TRANSPORTATION");
			break;
		}
	}

	private void repaintFloors() {
		BuildingPanel.getTransportationProgress().setValue(
				passengersTransported);
		BuildingPanel.getDispatchPassengerSumm().setText(
				Integer.toString(Runner.PASSENGERS_NUMBER
						- passengersTransported));
		BuildingPanel.getArrivalPassengerSumm().setText(
				Integer.toString(passengersTransported));

		BuildingPanel.getElevatorPassengerSumm().setText(
				Integer.toString(elevatorContainerSize));
		
		if ((elevatorCurrentFloor == Runner.STORIES_NUMBER)
				|| (elevatorCurrentFloor == 1)) {
			if (elevatorCurrentFloor == 1) {
				BuildingPanel.getStoreContainers()
						.get(Constants.BELOW_DISPATCHER)
						.setText(Constants.NULL_FIELD);
				BuildingPanel.getStoreContainers().get(Constants.BELOW_ARRIVAL)
						.setText(Constants.NULL_FIELD);
				BuildingPanel
						.getStoreContainers()
						.get(Constants.ABOVE_DISPATCHER)
						.setText(
								Integer.toString(building
										.getFloor(elevatorCurrentFloor + 1)
										.getDispatchStoryContainer().size()));
				BuildingPanel
						.getStoreContainers()
						.get(Constants.ABOVE_ARRIVAL)
						.setText(
								Integer.toString(building
										.getFloor(elevatorCurrentFloor + 1)
										.getArrivalStoryContainer().size()));
			} else {
				BuildingPanel.getStoreContainers()
						.get(Constants.ABOVE_DISPATCHER)
						.setText(Constants.NULL_FIELD);
				BuildingPanel.getStoreContainers().get(Constants.ABOVE_ARRIVAL)
						.setText(Constants.NULL_FIELD);
				BuildingPanel
						.getStoreContainers()
						.get(Constants.BELOW_DISPATCHER)
						.setText(
								Integer.toString(building
										.getFloor(elevatorCurrentFloor - 1)
										.getDispatchStoryContainer().size()));
				BuildingPanel
						.getStoreContainers()
						.get(Constants.BELOW_ARRIVAL)
						.setText(
								Integer.toString(building
										.getFloor(elevatorCurrentFloor - 1)
										.getArrivalStoryContainer().size()));
			}

		} else {
			BuildingPanel
					.getStoreContainers()
					.get(Constants.ABOVE_DISPATCHER)
					.setText(
							Integer.toString(building
									.getFloor(elevatorCurrentFloor + 1)
									.getDispatchStoryContainer().size()));
			BuildingPanel
					.getStoreContainers()
					.get(Constants.ABOVE_ARRIVAL)
					.setText(
							Integer.toString(building
									.getFloor(elevatorCurrentFloor + 1)
									.getArrivalStoryContainer().size()));
			BuildingPanel
					.getStoreContainers()
					.get(Constants.BELOW_DISPATCHER)
					.setText(
							Integer.toString(building
									.getFloor(elevatorCurrentFloor - 1)
									.getDispatchStoryContainer().size()));
			BuildingPanel
					.getStoreContainers()
					.get(Constants.BELOW_ARRIVAL)
					.setText(
							Integer.toString(building
									.getFloor(elevatorCurrentFloor)
									.getDispatchStoryContainer().size()));
		}

		BuildingPanel
				.getStoreContainers()
				.get(Constants.CURRENT_DISPATCHER)
				.setText(
						Integer.toString(building
								.getFloor(elevatorCurrentFloor)
								.getDispatchStoryContainer().size()));
		BuildingPanel
				.getStoreContainers()
				.get(Constants.CURRENT_ARRIVAL)
				.setText(
						Integer.toString(building
								.getFloor(elevatorCurrentFloor)
								.getArrivalStoryContainer().size()));
		BuildingPanel.getCurrenFloorNumber().setText(
				Integer.toString(elevatorCurrentFloor));

	}
}
