package by.epamlab.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import by.epamlab.constants.Constants;

public class PropertiesReader {

	private Properties prop = new Properties();

	public PropertiesReader() {
		super();
		InputStream is = PropertiesReader.class.getClassLoader()
				.getResourceAsStream(Constants.CONFIG_FILE);
		try {
			this.prop.load(is);
		} catch (FileNotFoundException e) {
			System.err.println("File config.properties not found");
		} catch (Exception e) {
			e.printStackTrace();
			System.err
					.println("Error occured reading config.properties");
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getStoriesNumber() {
		return parsParameter(Constants.DEFAULT_STORIES_NUMBER,
				Constants.STORIES_NUMBER);
	}

	public int getElevatorCapacity() {
		return parsParameter(Constants.DEFAULT_ELEVATOR_CAPACITY,
				Constants.ELEVATOR_CAPACITY);
	}

	public int getPassengersNumber() {
		return parsParameter(Constants.DEFAULT_PASSENGERS_NUMBER,
				Constants.PASSENGERS_NUMBER);
	}

	public int getAnimationBoost() {
		return parsParameter(Constants.DEFAULT_ANIMATION_BOOST,
				Constants.ANIMATION_BOOST);
	}

	private int parsParameter(int defaultValue, String fieldName) {
		String tmpString = prop.getProperty(fieldName);
		int tmpInt;
		try {
			tmpInt = Integer.parseInt(tmpString);
		} catch (NumberFormatException e) {
			System.out.println("wrong " + fieldName
					+ " value setting default value = " + defaultValue);
			return defaultValue;
		}
		return tmpInt;
	}
}
