package by.epamlab;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import by.epamlab.beans.Building;
import by.epamlab.factories.BuildingFactory;
import by.epamlab.presenter.Presenter;
import by.epamlab.service.PropertiesReader;

public class Runner {

	public static Logger log = Logger.getRootLogger();
	private static final String logFile = "src/by/epamlab/resourses/log4j.properties";
	public final static int STORIES_NUMBER;
	public final static int ELEVATOR_CAPACITY;
	public final static int PASSENGERS_NUMBER;
	public final static int ANIMATION_BOOST;
	
	static {
		PropertyConfigurator.configure(logFile);
		PropertiesReader property = new PropertiesReader();	
		STORIES_NUMBER = property.getStoriesNumber();
		ELEVATOR_CAPACITY = property.getElevatorCapacity();
		PASSENGERS_NUMBER = property.getPassengersNumber();
		ANIMATION_BOOST = property.getAnimationBoost();	
	}

	private static int passengersTransported = 0;
	private static int globalStartCounter = 0;
	private static Building building = BuildingFactory.createBuilding(
			STORIES_NUMBER, PASSENGERS_NUMBER, ELEVATOR_CAPACITY);
	private static Thread presenter;
	
	public static void main(String[] args) {
		presenter = new Thread(new Presenter());
	}

	public static void setBuilding(Building building) {
		Runner.building = building;
	}

	public static Building getBuilding() {
		return building;
	}

	public static void setPassengersTransported(int passengersTransported) {
		Runner.passengersTransported = passengersTransported;
	}

	public static int getPassengersTransported() {
		return passengersTransported;
	}

	public static boolean isAllPassengersTransported() {
		return passengersTransported == PASSENGERS_NUMBER;
	}

	public static synchronized void passengerTransported(){
		passengersTransported++;
	}

	public synchronized static void incrementGlobalStartCounter() {
		Runner.globalStartCounter++;
	}

	public synchronized static int getGlobalStartCounter() {
		return globalStartCounter;
	}

	public static void startPresenter() {
		presenter.start();
	}
}
