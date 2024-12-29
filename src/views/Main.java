package views;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Main {
	public static int FRAME_WIDTH = 800;
	public static int FRAME_HEIGHT = 800;
	public static void main (String[] args) {
		UIManager.put("OptionPane.background", Color.WHITE); // COR DE FUNDO DO PAINEL
        UIManager.put("Panel.background", Color.WHITE); // COR DE FUNDO DO CONTEÚDO
        UIManager.put("OptionPane.messageForeground", Color.BLACK); // COR DO TEXTO
		
		JFrame frame = new JFrame("Algoritmos de escalonamento");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        
        Settings panel = new Settings();
        frame.add(panel);
        
        panel.setBackground(Color.white);
        
        frame.setVisible(true);
	}
}
