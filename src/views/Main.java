package views;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static int FRAME_WIDTH = 800;
	public static int FRAME_HEIGHT = 800;
	public static void main (String[] args) {
		JFrame frame = new JFrame("Algoritmos de escalonamento");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        Settings panel = new Settings();
        frame.add(panel);
        
        frame.setVisible(true);
	}
}
