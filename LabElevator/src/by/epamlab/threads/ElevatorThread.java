package by.epamlab.threads;

import java.util.List;

import by.epamlab.Runner;
import by.epamlab.beans.Directions;
import by.epamlab.beans.Elevator;
import by.epamlab.beans.Floor;
import by.epamlab.beans.Passenger;
import by.epamlab.beans.TransportationActions;
import by.epamlab.factories.EventFactory;
import by.epamlab.presenter.Presenter;

public class ElevatorThread extends Thread {

	private static Elevator elevator = Runner.getBuilding().getElevator();

	public ElevatorThread() {
		super("Elevator");
	}

	@Override
	public void run() {
		Presenter.getEvents().add(
				EventFactory.getEventFromFactory(elevator, null));
		Floor floor;
		try {
			while (!(Runner.getGlobalStartCounter() == Runner.PASSENGERS_NUMBER)) {
				synchronized (elevator.getElevatorContainer()) {
					elevator.getElevatorContainer().wait();
				}
			}
			int currentFloor;
			synchronized (elevator.getElevatorContainer()) {
				while (!Runner.isAllPassengersTransported()) {
					synchronized (elevator) {
						elevator.notify();
						elevator.wait();
					}
					if (Runner.ANIMATION_BOOST > 0) {
						Thread.sleep(700 / Runner.ANIMATION_BOOST);
					}
					currentFloor = elevator.getCurrentFloor();
					floor = Runner.getBuilding().getFloor(currentFloor);
					while (elevator.isNeedDeboarding(currentFloor)) {
						deboarding();
					}
					if ((!elevator.isFull())
							&& (floor.getDispatchStoryContainer().size() != 0)) {
						boarding();
					}
					moveElevator();
				}
				elevator.setAction(TransportationActions.COMPLETION_TRANSPORTATION);
				Presenter.getEvents().add(
						EventFactory.getEventFromFactory(elevator, null));
			}
			synchronized (elevator) {
				elevator.notify();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void boarding() throws InterruptedException {
		elevator.setAction(TransportationActions.BOARDING_OF_PASSENGER);
		Floor floor = Runner.getBuilding().getFloor(elevator.getCurrentFloor());
		List<Passenger> container = floor.getDispatchStoryContainer();
		floor.setPassCounter();
		synchronized (floor.getElevatorWaitingFlag()) {
			synchronized (container) {
				container.notifyAll();
			}
			floor.getElevatorWaitingFlag().wait();
		}
	}

	private void deboarding() throws InterruptedException {
		elevator.setAction(TransportationActions.DEBOARDING_OF_PASSENGER);
		elevator.getElevatorContainer().notifyAll();
		elevator.getElevatorContainer().wait();
	}

	private void moveElevator() {
		int currentFloor = elevator.getCurrentFloor();
		elevator.setAction(TransportationActions.MOVING_ELEVATOR);
		Presenter.getEvents().add(
				EventFactory.getEventFromFactory(elevator, null));
		if (elevator.getDirection() == Directions.UP) {
			elevator.setCurrentFloor(++currentFloor);
			if (Runner.STORIES_NUMBER == currentFloor) {
				elevator.changeDirection();
			}
		} else {
			elevator.setCurrentFloor(--currentFloor);
			if (currentFloor == 1) {
				elevator.changeDirection();
			}
		}
	}
}
