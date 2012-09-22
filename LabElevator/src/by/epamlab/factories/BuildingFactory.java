package by.epamlab.factories;

import java.util.Random;

import by.epamlab.beans.Building;
import by.epamlab.beans.Passenger;
import by.epamlab.constants.Constants;
import by.epamlab.threads.TransportationTask;

public class BuildingFactory {
	private static Random rnd = new Random();
	private static ThreadGroup passangersThreads = new ThreadGroup(
			Constants.PASSENGER_GROUP);

	public static Building createBuilding(int storiesNumber,
			int passengersNumber, int elevatorCapacity) {
		Building building = new Building(storiesNumber, passengersNumber,
				elevatorCapacity);
		int floorNumber;
		Passenger passenger;
		for (int i = 1; i <= passengersNumber; i++) {
			floorNumber = rnd.nextInt(storiesNumber) + 1;
			passenger = createPassenger(storiesNumber, i, floorNumber, building);
			building.getFloor(floorNumber).getDispatchStoryContainer()
					.add(passenger);
		}
		return building;
	}

	private static Passenger createPassenger(int storiesNumber, int id,
			int startingFloor, Building building) {
		int destinationStory = rnd.nextInt(storiesNumber) + 1;
		while (startingFloor == destinationStory) {
			destinationStory = rnd.nextInt(storiesNumber) + 1;
		}
		Passenger passenger = new Passenger(destinationStory, id, startingFloor);
		new TransportationTask(passangersThreads, passenger);
		return passenger;
	}

	public static ThreadGroup getPassangersThreads() {
		return passangersThreads;
	}

	public static void setPassangersThreads(ThreadGroup passangersThreads) {
		BuildingFactory.passangersThreads = passangersThreads;
	}

}
