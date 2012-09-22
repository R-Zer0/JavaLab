package by.epamlab.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import by.epamlab.Runner;
import by.epamlab.constants.Constants;
import by.epamlab.presenter.Presenter;

public class BuildingView implements ActionListener {

	private JFrame buildingView;
	private static JButton jButtonStart;
	private JPanel startPanel;
	private JPanel logPanel;
	private static JPanel elevatorPanel;
	private static BuildingPanel buildingPanel;
	private static JTextArea jTextLog;
	private JScrollPane jLogScroll;

	public BuildingView() {
		initComponents();
	}

	private void initComponents() {
		buildingView = new JFrame("Elevator processing");
		buildingView.setSize(850, 800);
		buildingView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildingView.setResizable(true);
		buildingView.setVisible(true);

		startPanel = new JPanel();
		startPanel.setSize(80, 60);
		startPanel.setLocation(20, 12);
		startPanel.setOpaque(true);
		startPanel.setDoubleBuffered(true);
		startPanel.setLayout(new BorderLayout());
		startPanel.setVisible(true);

		jButtonStart = new JButton();

		jButtonStart.setText("START");
		jButtonStart.setMnemonic(KeyEvent.VK_S);
		jButtonStart.addActionListener(this);
		jButtonStart.setSize(70, 50);
		jButtonStart.setLocation(new Point(5, 5));
		jButtonStart.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		jButtonStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jButtonStart.setFont(new Font("Tahoma", Font.BOLD, 11));

		startPanel.add(jButtonStart);
		startPanel.add(new JLabel());

		logPanel = new JPanel();
		logPanel.setSize(760, 420);
		logPanel.setLocation(20, 280);
		logPanel.setOpaque(true);
		logPanel.setDoubleBuffered(true);
		logPanel.setLayout(new BorderLayout());
		logPanel.setBorder(BorderFactory.createTitledBorder("Log"));
		logPanel.setVisible(true);

		jTextLog = new JTextArea();
		jLogScroll = new JScrollPane(jTextLog);
		jLogScroll.createVerticalScrollBar();
		jLogScroll.setSize(720, 380);
		jLogScroll.setLocation(20, 20);
//		jLogScroll.

		logPanel.add(jLogScroll);
		logPanel.add(new JLabel());

		buildingPanel = new BuildingPanel(Runner.STORIES_NUMBER);
		buildingPanel.setName("building");

		buildingView.getContentPane().add(startPanel);
		buildingView.getContentPane().add(logPanel);

		buildingView.getContentPane().add(buildingPanel.getBuildingPanel());

		buildingView.getContentPane().add(new JLabel());
		buildingView.repaint();
		BuildingView.getBuildingPanel().repaint();

	}

	private static Component getBuildingPanel() {
		// TODO Auto-generated method stub
		return buildingPanel;
	}

	public static JButton getJButtonStart() {
		if (jButtonStart == null) {
			jButtonStart = new JButton();
			jButtonStart.setText("START");
			jButtonStart.setMnemonic(KeyEvent.VK_S);
			jButtonStart.addActionListener(null);
		}
		return jButtonStart;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File file = new File(Constants.LOG_FILE);
		if (e.getSource() == getJButtonStart()) {
			try {
				if (getJButtonStart().getText().equals("START")) {
					Runner.startPresenter();
					getJButtonStart().setText("ABORT");
				} else if (getJButtonStart().getText().equals("ABORT")) {
					Presenter.abortTransportation();
					getJButtonStart().setText("VIEW LOG FILE");
				} else if (getJButtonStart().getText().equals("VIEW LOG FILE")) {
					Process process = Runtime.getRuntime().exec(
							"cmd /c notepad.exe " + file.getAbsolutePath());
					
					process.waitFor();
				}
			} catch (Exception ex) {
				getJButtonStart().setText("VIEW LOG FILE");
			}
		}
	}

	public static JTextArea getjTextLog() {
		return jTextLog;
	}

	public static void setElevatorPanel(JPanel elevatorPanel) {
		BuildingView.elevatorPanel = elevatorPanel;
	}

	public static JPanel getElevatorPanel() {
		return elevatorPanel;
	}
}
