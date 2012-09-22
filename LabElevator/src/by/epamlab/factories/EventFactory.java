package by.epamlab.factories;

import by.epamlab.Runner;
import by.epamlab.beans.Elevator;
import by.epamlab.beans.Passenger;
import by.epamlab.events.AbstractEvent;
import by.epamlab.events.ConsoleEvent;
import by.epamlab.events.FrameEvent;

public class EventFactory {
	private static int animationBoost = Runner.ANIMATION_BOOST;
	private static AbstractEvent event;

	public static AbstractEvent getEventFromFactory(Elevator elevator, Passenger passenger) {
		if (animationBoost == 0) {
			event = new ConsoleEvent(passenger, elevator);
		} else {
			event = new FrameEvent(passenger, elevator);
		}
		return event;
	}
}
