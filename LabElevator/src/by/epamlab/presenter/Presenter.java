package by.epamlab.presenter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import by.epamlab.Runner;
import by.epamlab.threads.ElevatorThread;
import by.epamlab.view.BuildingView;
import by.epamlab.beans.Elevator;
import by.epamlab.beans.TransportationActions;
import by.epamlab.events.AbstractEvent;
import by.epamlab.factories.BuildingFactory;

public class Presenter implements Runnable {

	private static BlockingQueue<AbstractEvent> events = new LinkedBlockingQueue<AbstractEvent>();
	private Thread elevatorThread = new ElevatorThread();
	private Thread thread;
	static {

	}

	public Presenter() {
		super();
		// TODO Auto-generated constructor stub
		if (Runner.ANIMATION_BOOST != 0) {
			new BuildingView();
		} else {
			thread = new Thread(this, "Presenter");
			thread.start();
		}
	}

	@Override
	public void run() {
		AbstractEvent event;
		elevatorThread.start();
		Elevator elevator = Runner.getBuilding().getElevator();
		boolean inProgress = true;
		try {
			while (inProgress) {
				if (events.isEmpty() && !Runner.isAllPassengersTransported()) {
					synchronized (elevator) {
						elevator.notify();
						elevator.wait();
					} 
				} else {
					if (events.isEmpty()){
							elevator.notify();
					}
				}
				event = events.take();
				if ((elevator.getAction() == TransportationActions.COMPLETION_TRANSPORTATION)
						&& (events.isEmpty())) {
					inProgress = false;
				}
				if (Runner.ANIMATION_BOOST > 0) {
					Thread.sleep(1000 / Runner.ANIMATION_BOOST);
				}
				event.show();
			}
		} catch (Exception e) {
			System.err.println(e + "Error with events queue");
			e.printStackTrace();
		}
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Thread getThread() {
		return thread;
	}

	public static BlockingQueue<AbstractEvent> getEvents() {
		return events;
	}

	public static void setEvents(BlockingQueue<AbstractEvent> events) {
		Presenter.events = events;
	}

	public static void abortTransportation() {
		Runner.log.info("ABORTING_TRANSPORTATION");
		BuildingFactory.getPassangersThreads().interrupt();
	}
}
