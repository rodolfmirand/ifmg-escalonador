package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import factories.SchedulerFactory;
import models.Process;
import models.Processor;
import schedulers.Scheduler;
import views.components.Button;
import views.components.TextField;

public class Settings extends JPanel{
	private String scheduler = "Round Roubin";
	private final Font defaultFont;
	private final Font titleFont;
	private int componentsWidth, componentsHeight, margin, xLabel, xField, y;
	
	public Settings() {
		this.margin = 10;
		this.componentsWidth = 250;
		this.componentsHeight = 25;
		this.xLabel = 50;
		this.xField = this.xLabel + this.componentsWidth + this.margin;
		this.y = 20;
		this.defaultFont = new Font("Arial", Font.PLAIN, 14);
		this.titleFont = new Font("Arial", Font.BOLD, 16);
		this.setLayout(null);
		this.init();
	}
	
	private JLabel addLabelTitle(String title, JPanel parentPanel) {
		JLabel labelTitle = this.addLabel(title, this.xLabel, this.y, this.titleFont, parentPanel);
		this.updateY(this.componentsHeight);
		
		return labelTitle;
	}
	
	private void init() {
		setLayout(null);

		JLabel totalExecutionTime = new JLabel();
		ProcessesTableModel processesTableModel = new ProcessesTableModel(new ArrayList<Process>());

		// Cabeçalho do trabalho
		this.addSchedulersIcon();
 		JLabel labelImageIcon = this.addLabel("Escalonadores", this.xLabel + 70, this.y, this.titleFont, this);
 		this.updateY(50);
 		this.addSeparator();
        
 		// Processador
		JLabel title = null;
		this.addLabelTitle("Processador", this);
		this.addLabel("Número de processadores", this.xLabel, this.y, this.defaultFont, this);
		this.addAvaiableProcessorsComboBox();
		this.addLabel("Algoritmo", this.xLabel, this.y, defaultFont, this);
		JComboBox<String> algorithmsComboBox = this.addAlgorithmComboBox();
		JLabel labelQuantum = this.addLabel("Quantum", this.xLabel, this.y, this.defaultFont, this);
		JTextField fieldQuantum = this.addTextField(this.xField, this.y, this.componentsWidth, this.componentsHeight);
		algorithmsComboBox.addActionListener(e -> {
			this.scheduler = (String) algorithmsComboBox.getSelectedItem();
			if(this.scheduler == "Round Roubin") {
				labelQuantum.setVisible(true);
				fieldQuantum.setVisible(true);
			}else {
				labelQuantum.setVisible(false);
				fieldQuantum.setVisible(false);
			}
		});
		
		JButton buttonProcessor = this.addButton("Salvar", xLabel, y, this);
		buttonProcessor.addActionListener(e -> {
			int quantumValue = !fieldQuantum.getText().isEmpty() ? Integer.parseInt(fieldQuantum.getText()) : 0;
			Processor.setQuantum(quantumValue);
			
			JOptionPane.showMessageDialog(this, "Número de processadores: " + Processor.getProcessorsNumbers() + "\nAlgoritmo: "+ this.scheduler, "Processador", JOptionPane.INFORMATION_MESSAGE);
		});

		this.addSeparator();
		
		// Processo
		this.addLabelTitle("Processo", this);
		this.addLabel("Tempo de chegada:", xLabel, y, this.defaultFont, this);
		JTextField fieldArrivalOrder = this.addTextField(xField, y, componentsWidth, componentsHeight);
		this.addLabel("Tempo de execução:", xLabel, y, this.defaultFont, this);
		JTextField fieldExecutionTime = this.addTextField(xField, y, componentsWidth, componentsHeight);
		JButton buttonAddProcess = this.addButton("Adicionar Processo", xLabel, y, this);
		buttonAddProcess.addActionListener(e -> {
			int arrivalTime = !fieldArrivalOrder.getText().isEmpty() ? Integer.parseInt(fieldArrivalOrder.getText()) : 0;
			int executionTime = !fieldExecutionTime.getText().isEmpty()  ? Integer.parseInt(fieldExecutionTime.getText()) : 0;

			Process process = new Process(++Scheduler.processCounter, arrivalTime, executionTime);
			Scheduler.PROCESSES.add(process);
			processesTableModel.addProcess(process);
			processesTableModel.fireTableDataChanged();
			
			JOptionPane.showMessageDialog(this, process, "Processo adicionado!", JOptionPane.INFORMATION_MESSAGE);
		});
		
		this.updateY(0);
		
		JPanel executePanel = new JPanel();
		executePanel.setLayout(null);
		executePanel.setBackground(new Color(84,101,116));  
		executePanel.setBounds(0, y, Main.FRAME_WIDTH, 400);
		y = 10;
		
		// Título "Executar"
		JLabel executeLabel = this.addLabelTitle("Executar", executePanel);
		executeLabel.setForeground(Color.WHITE);

		JButton buttonInit =  this.addButton("Iniciar", xLabel, y, executePanel);

		buttonInit.addActionListener(e -> {
			if(!Scheduler.PROCESSES.isEmpty()) {
				processesTableModel.clearProcesses();
				ArrayList<Thread> threads = new ArrayList<>();
				for (int i = 1; i <= Processor.getProcessorsNumbers(); i++) {
					Thread threadScheduler = new Thread(SchedulerFactory.createScheduler(this.scheduler, processesTableModel));
					threads.add(threadScheduler);
					threadScheduler.start();
				}
	
				for(Thread thread: threads) {
	                try {
	                    thread.join();
	                } catch (InterruptedException ex) {
	                    throw new RuntimeException(ex);
	                }
	            }
				processesTableModel.fireTableDataChanged();
				totalExecutionTime.setText("Tempo total de execução: "+ Scheduler.executionTimeSpent);
				
				Scheduler.executionTimeSpent = 0;
			}else {
				processesTableModel.clearProcesses();
				JOptionPane.showMessageDialog(this, "Não há processos na fila!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JTable processTable = new JTable(processesTableModel);
		processTable.getColumnModel().getColumn(1).setCellRenderer(new ProcessesTableModel.StatusCellRenderer());
		JScrollPane scrollPane = new JScrollPane(processTable);
		scrollPane.setBounds(xLabel, y, 700, 200);
		executePanel.add(scrollPane);
		y += 210;
		
		totalExecutionTime.setForeground(Color.WHITE);
		totalExecutionTime.setBounds(xLabel, y, componentsWidth, componentsHeight);
		executePanel.add(totalExecutionTime);
		this.add(executePanel);
	}
	
	private JLabel addLabel(String text, int x, int y, Font font, JPanel parentPanel) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, this.componentsWidth, this.componentsHeight);
		label.setFont(font);
		parentPanel.add(label);
 		
 		return label;
	}
	
	private JTextField addTextField(int x, int y, int width, int height) {
		JTextField textField = new TextField();
		textField.setBounds(x, y, this.componentsWidth, this.componentsHeight);
		textField.setFont(this.defaultFont);
		this.add(textField);
		this.updateY();
		
		return textField;
	}
	
	private JButton addButton(String text, int x, int y, JPanel parentPanel) {
		JButton button = new Button(text);
		button.setBounds(x, y, this.componentsWidth, this.componentsHeight);
		button.setFont(this.defaultFont);
		parentPanel.add(button);
		this.updateY();
		
		return button;
	}
	
	private void addSchedulersIcon() {
		ImageIcon imageIcon = null;
		try {
			imageIcon = new ImageIcon(ImageIO.read(new File("src/assets/logo.png")));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        imageLabel.setBounds(this.xLabel, this.y, 50, 50);
        this.add(imageLabel);
	}
	
	private void addSeparator() {
		JSeparator separator = new JSeparator();
		separator.setBounds(0, this.y, Main.FRAME_WIDTH, 10);
		this.add(separator);
		
		this.updateY(separator.getHeight());
	}
	
	private void addAvaiableProcessorsComboBox(){
		Integer[] processors = new Integer[Runtime.getRuntime().availableProcessors()];
		for(int i =0; i < processors.length;i++) processors[i] = i + 1;
		
		JComboBox<Integer> comboBoxNumProcessors  = new JComboBox<>(processors);
		comboBoxNumProcessors.setBounds(xField, y, this.componentsWidth, this.componentsHeight);
		
		comboBoxNumProcessors .addActionListener(e -> {
            Processor.setProcessorsNumbers((Integer) comboBoxNumProcessors.getSelectedItem());
		});
		
		this.updateY();
		add(comboBoxNumProcessors );
	}
	
	private JComboBox<String> addAlgorithmComboBox() {
		JComboBox<String> comboBox = new JComboBox<>(new String[] {"Round Roubin", "Menor Tarefa Primeiro"});
		comboBox.setBounds(xField, y, this.componentsWidth, this.componentsHeight);
		add(comboBox);
		this.updateY();
		
		return comboBox;	
	}
	
	private void updateY() {
		this.y += this.componentsHeight + this.margin;
	}
	
	private void updateY(int componentHeight) {
		this.y += componentHeight + this.margin;
	}
}