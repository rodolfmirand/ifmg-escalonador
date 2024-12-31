package views;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import models.Process;
import views.components.ProcessesTableModel;
import controller.SchedulerController;

public class Main {
	public static int FRAME_WIDTH = 800;
	public static int FRAME_HEIGHT = 800;
	public static void main (String[] args) {
		defineJOptionPanelSettings();
		JFrame frame = createMainFrame();

		/* Scheduler Controller */
		ProcessesTableModel processesTableModel = new ProcessesTableModel(new ArrayList<Process>());
		SchedulerController schedulerController = new SchedulerController(processesTableModel);
        
		Settings panel = new Settings(schedulerController, processesTableModel);
        frame.add(panel);
        frame.setVisible(true);
	}
	
	private static JFrame createMainFrame() {
		JFrame frame = new JFrame("Algoritmos de escalonamento");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        
        return frame;
	}
	
	private static void defineJOptionPanelSettings() {
		UIManager.put("OptionPane.background", Color.WHITE); 
	    UIManager.put("Panel.background", Color.WHITE);
	    UIManager.put("OptionPane.messageForeground", Color.BLACK);
	}
}
