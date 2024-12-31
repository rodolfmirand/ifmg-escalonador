package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.SchedulerController;
import factories.SchedulerFactory;
import models.Process;
import models.Processor;
import schedulers.Scheduler;
import views.components.Button;
import views.components.ExecutionPanel;
import views.components.HeaderPanel;
import views.components.ProcessPanel;
import views.components.ProcessesTableModel;
import views.components.ProcessorPanel;
import views.components.TextField;

public class Settings extends JPanel {
    public Settings(SchedulerController schedulerController, ProcessesTableModel processesTableModel) {
        setLayout(null);

        HeaderPanel headerPanel = new HeaderPanel();
        headerPanel.setBounds(0, 0, Main.FRAME_WIDTH, 60);
        add(headerPanel);
        
        this.addSeparator(60);
        
        ProcessorPanel processorPanel = new ProcessorPanel();
        processorPanel.setBounds(0, 80, 800, 180);
        add(processorPanel);

        this.addSeparator(280);
        
        ProcessPanel processPanel = new ProcessPanel(schedulerController);
        processPanel.setBounds(0, 280, 800, 150);
        add(processPanel);
        
        ExecutionPanel executionPanel = new ExecutionPanel(processesTableModel, schedulerController);
        executionPanel.setBounds(0, 450, 800, 350);
        add(executionPanel);
    }
    
    private void addSeparator(int y) {
        JSeparator separator = new JSeparator();
        separator.setBounds(0, y, Main.FRAME_WIDTH, 10);
        add(separator);

    }
}
