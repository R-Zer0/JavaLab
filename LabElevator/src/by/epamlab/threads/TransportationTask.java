package by.epamlab.threads;

import java.util.List;

import by.epamlab.Runner;
import by.epamlab.beans.Elevator;
import by.epamlab.beans.Floor;
import by.epamlab.beans.Passenger;
import by.epamlab.beans.TransportationActions;
import by.epamlab.beans.TransportationState;
import by.epamlab.factories.EventFactory;
import by.epamlab.presenter.Presenter;

public class TransportationTask extends Thread {

	private Passenger passenger;
	private boolean inElevator = false;

	public TransportationTask(ThreadGroup passangersThreads, Passenger passenger) {
		super(passangersThreads, "Passenger-" + passenger.getId());
		this.passenger = passenger;
		this.start();
		passenger.setTransportationState(TransportationState.IN_PROGRESS);
	}

	@Override
	public void run() {
		Elevator elevator = Runner.getBuilding().getElevator();
		Floor floor = Runner.getBuilding().getFloor(passenger.getStartingFloor());
		List<Passenger> container = floor.getDispatchStoryContainer();
		Runner.incrementGlobalStartCounter();
		if (Runner.getGlobalStartCounter() == Runner.PASSENGERS_NUMBER)
			synchronized (elevator.getElevatorContainer()) {
				elevator.getElevatorContainer().notify();
			}
		try {
			synchronized (container) {
				while (!inElevator) {
					container.wait();
					if ((elevator.getAction() == TransportationActions.BOARDING_OF_PASSENGER)
							&& elevator.getDirection() == passenger
									.getDirection() && !elevator.isFull()) {
						if (elevator.addPassenger(passenger)) {
							container.remove(passenger);
							inElevator = true;
							Presenter.getEvents().add(
									EventFactory.getEventFromFactory(elevator,
											passenger));
							if (Runner.ANIMATION_BOOST > 0) {
								Thread.sleep(700/Runner.ANIMATION_BOOST);
							}
						}
					}
					if (floor.getPassCounter() == 1) {
						synchronized (floor.getElevatorWaitingFlag()) {
							floor.getElevatorWaitingFlag().notifyAll();
						}
					} else {
						floor.changePassCounter();
					}
				}
			}
			synchronized (elevator.getElevatorContainer()) {
				while (inElevator) {
					elevator.getElevatorContainer().notifyAll();
					if ((elevator.getAction() == TransportationActions.DEBOARDING_OF_PASSENGER)
							&& (elevator.getCurrentFloor() == passenger
									.getDestinationStory())) {
						container = Runner.getBuilding()
								.getFloor(elevator.getCurrentFloor())
								.getArrivalStoryContainer();
						if (elevator.removePassenger(passenger)) {
							container.add(passenger);
							Presenter.getEvents().add(
									EventFactory.getEventFromFactory(elevator,
											passenger));
							inElevator = false;
							if (Runner.ANIMATION_BOOST > 0) {
								Thread.sleep(700/Runner.ANIMATION_BOOST);
							}
						}
						passenger
								.setTransportationState(TransportationState.COMPLETED);
						Runner.passengerTransported();
					} else {
						elevator.getElevatorContainer().wait();
					}
				}
			}

		} catch (InterruptedException e) {
			this.passenger.setTransportationState(TransportationState.ABORTED);
		}

	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setInElevator(boolean inElevator) {
		this.inElevator = inElevator;
	}

	public boolean isInElevator() {
		return inElevator;
	}

}
