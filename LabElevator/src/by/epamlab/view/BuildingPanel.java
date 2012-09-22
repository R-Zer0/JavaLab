package by.epamlab.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;


public class BuildingPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel buildingPanel;
	private static JProgressBar transportationProgress;
	private static JPanel elevatorPanel;
	private static JPanel currentFloor;
	private static List<JTextField> storeContainers = new ArrayList<JTextField>();
	private static JTextField dispatchPassengerSumm;
	private static JTextField arrivalPassengerSumm;
	private static JTextField elevatorPassengerSumm;
	private static JTextField currenFloorNumber;
	

	public BuildingPanel(int floorsNumber) {

		// TODO Auto-generated constructor stub
		this.buildingPanel = new JPanel();
		initComponents(floorsNumber);
	}

	public static JTextField getElevatorPassengerSumm() {
		return elevatorPassengerSumm;
	}

	public static void setElevatorPassengerSumm(JTextField elevatorPassengerSumm) {
		BuildingPanel.elevatorPassengerSumm = elevatorPassengerSumm;
	}

	public static JPanel getElevatorPanel() {
		return elevatorPanel;
	}

	public static void setElevatorPanel(JPanel elevatorPanel) {
		BuildingPanel.elevatorPanel = elevatorPanel;
	}

	public static JTextField getDispatchPassengerSumm() {
		return dispatchPassengerSumm;
	}

	public static void setDispatchPassengerSumm(JTextField dispatchPassengerSumm) {
		BuildingPanel.dispatchPassengerSumm = dispatchPassengerSumm;
	}

	public static JTextField getArrivalPassengerSumm() {
		return arrivalPassengerSumm;
	}

	public static void setArrivalPassengerSumm(JTextField arrivalPassengerSumm) {
		BuildingPanel.arrivalPassengerSumm = arrivalPassengerSumm;
	}

	public JPanel getBuildingPanel() {
		return buildingPanel;
	}

	public void setBuildingPanel(JPanel buildingPanel) {
		this.buildingPanel = buildingPanel;
	}

	private void initComponents(int floorsNumber) {
		buildingPanel.setSize(600, 260);
		buildingPanel.setLocation(100, 5);
		buildingPanel.setOpaque(true);
		buildingPanel.setDoubleBuffered(true);
		buildingPanel.setLayout(new BorderLayout());
		buildingPanel.setBorder(BorderFactory.createTitledBorder("Building"));
		buildingPanel.setVisible(true);
		buildingPanel.add(new JLabel());
		
		JPanel panel;
		JTextField title;
		String name = null;
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 1; j++) {
					panel = new JPanel();
					panel.setVisible(true);
					panel.setSize(110, 40);
					panel.setLocation(new Point((15 + 260 * j), (30 + 60 * i)));
					panel.setOpaque(true);
					panel.setDoubleBuffered(true);
					panel.setLayout(new BorderLayout());
					switch (i) {
					case 0:
						if(j==0){
							name = "Above dispatcher";
						} else {
							name = "Above arrival";
						}
						break;
					case 1:
						if(j==0){
							name = "Current dispatcher";
						} else {
							name = "Current arrival";
						}
						break;
					case 2:
						if(j==0){
							name = "Below dispatcher";
						} else {
							name = "Below arrival";
						}
						break;
					default:
						break;
					}
					panel.setName(name);
					panel.setBorder(BorderFactory.createTitledBorder(name));
					title = createTitle(name);
					BuildingPanel.getStoreContainers().add(title);
					panel.add(title);
					panel.add(new JLabel());
					buildingPanel.add(panel);
			}
		}

		elevatorPanel = new JPanel();
		elevatorPanel.setVisible(true);
		elevatorPanel.setSize(70, 40);
		elevatorPanel.setLocation(new Point(165, 90));
		elevatorPanel.setOpaque(true);
		elevatorPanel.setDoubleBuffered(true);
		elevatorPanel.setLayout(new BorderLayout());
		elevatorPanel.setBorder(BorderFactory.createTitledBorder("elevator"));
		elevatorPassengerSumm = new JTextField();
		elevatorPassengerSumm.setSize(40, 20);
		elevatorPassengerSumm.setLocation(new Point(15, 15));
		elevatorPassengerSumm.setBackground(Color.white);
		elevatorPassengerSumm.setEditable(false);
		elevatorPanel.add(elevatorPassengerSumm);
		elevatorPanel.add(new JLabel());
		buildingPanel.add(elevatorPanel);
		
		currentFloor = new JPanel();
		currentFloor.setVisible(true);
		currentFloor.setSize(100, 100);
		currentFloor.setLocation(new Point(430, 20));
		currentFloor.setOpaque(true);
		currentFloor.setDoubleBuffered(true);
		currentFloor.setLayout(new BorderLayout());
		currentFloor.setBorder(BorderFactory.createTitledBorder("Current floor"));
		currenFloorNumber = new JTextField();
		currenFloorNumber.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		currenFloorNumber.setSize(70, 70);
		currenFloorNumber.setLocation(new Point(15, 15));
		currenFloorNumber.setBackground(Color.white);
		currenFloorNumber.setHorizontalAlignment(JTextField.CENTER);
		currenFloorNumber.setEditable(false);
		currentFloor.add(currenFloorNumber);
		currentFloor.add(new JLabel());
		buildingPanel.add(currentFloor);

		JPanel summPanel = new JPanel();
		summPanel.setSize(375, 50);
		summPanel.setLocation(10, 200);
		summPanel.setOpaque(true);
		summPanel.setDoubleBuffered(true);
		summPanel.setLayout(new BorderLayout());
		summPanel.setBorder(BorderFactory.createTitledBorder("Summary:"));
		summPanel.setVisible(true);
		dispatchPassengerSumm = new JTextField();
		dispatchPassengerSumm.setSize(60, 20);
		dispatchPassengerSumm.setLocation(new Point(30, 20));
		dispatchPassengerSumm.setEditable(false);
		dispatchPassengerSumm.setBackground(Color.white);
		transportationProgress = new JProgressBar();
		transportationProgress.setSize(180, 20);
		transportationProgress.setLocation(new Point(100, 20));
	
		arrivalPassengerSumm = new JTextField();
		arrivalPassengerSumm.setSize(60, 20);
		arrivalPassengerSumm.setLocation(new Point(290, 20));
		arrivalPassengerSumm.setBackground(Color.white);
		arrivalPassengerSumm.setEditable(false);
		summPanel.add(transportationProgress);
		summPanel.add(dispatchPassengerSumm);
		summPanel.add(arrivalPassengerSumm);
		summPanel.add(new JLabel());

		buildingPanel.add(elevatorPanel);
		buildingPanel.add(summPanel);
		buildingPanel.add(new JLabel());
	}

	private JTextField createTitle(String name) {
		JTextField title = new JTextField();
		title.setName(name);
		title.setSize(60, 15);
		title.setLocation(new Point(25, 16));
		title.setOpaque(true);
		title.setEditable(false);
		title.setBackground(Color.white);
		return title;
	}

	public static void setCurrentFloor(JPanel currentFloor) {
		BuildingPanel.currentFloor = currentFloor;
	}

	public static JPanel getCurrentFloor() {
		return currentFloor;
	}

	public static void setCurrenFloorNumber(JTextField currenFloorNumber) {
		BuildingPanel.currenFloorNumber = currenFloorNumber;
	}

	public static JTextField getCurrenFloorNumber() {
		return currenFloorNumber;
	}

	public static void setStoreContainers(List<JTextField> storeContainers) {
		BuildingPanel.storeContainers = storeContainers;
	}

	public static List<JTextField> getStoreContainers() {
		return storeContainers;
	}

	public static void setTransportationProgress(JProgressBar transportationProgress) {
		BuildingPanel.transportationProgress = transportationProgress;
	}

	public static JProgressBar getTransportationProgress() {
		return transportationProgress;
	}

}
