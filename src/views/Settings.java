package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import models.Quantum;
import schedulers.Scheduler;
import views.components.Button;
import views.components.TextField;

public class Settings extends JPanel{
	private String scheduler = "Round Roubin";
	private int processorNumbers = 1;
	private int processCounter = 1;

	public Settings() {		
		this.setLayout(null);
		this.init();
	}
	
	private void init() {
		setLayout(null);

		// Defina a fonte uma vez
		JLabel totalExecutionTime = new JLabel();
		ProcessesTableModel processesTableModel = new ProcessesTableModel(new ArrayList<Process>());
		Font defaultFont = new Font("Arial", Font.PLAIN, 14);

		int labelWidth = 250, fieldWidth = 200, fieldHeight = 25;
		int margin = 10;

		int xLabel = 50, xField = xLabel + labelWidth + margin;
		int y = 20;

		ImageIcon imageIcon = null;
		try {
			imageIcon = new ImageIcon(ImageIO.read(new File("src/assets/logo.png")));
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.print(imageIcon.getIconWidth());
		JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        imageLabel.setBounds(xLabel, y, 50, 50);
        this.add(imageLabel);
		
        // Título "Escalonadores"
 		JLabel title = new JLabel("Escalonadores");
 		title.setBounds(xLabel + 70, y + 15, 300, fieldHeight);
 		title.setFont(new Font("Arial", Font.BOLD, 16));
 		add(title);
 		y += 50 + margin;
 		
 		JSeparator separator = new JSeparator();
		separator.setBounds(0, y, Main.FRAME_WIDTH, 10);
		this.add(separator);
		y += 10 + margin;
        
		// Título "Processador"
		title = new JLabel("Processador");
		title.setBounds(xLabel, y, 300, fieldHeight);
		title.setFont(new Font("Arial", Font.BOLD, 16));
		add(title);
		y += 30;

		// Rótulo "Número de processadores"
		JLabel labelNumProcessors = new JLabel("Número de processadores");
		labelNumProcessors.setBounds(xLabel, y, labelWidth, fieldHeight);
		labelNumProcessors.setFont(defaultFont);
		add(labelNumProcessors);

		Integer[] processors = new Integer[Runtime.getRuntime().availableProcessors()];

		for(int i =0; i < processors.length;i++) processors[i] = i + 1;

		JComboBox<Integer> comboBoxNumProcessors  = new JComboBox<>(processors);
		comboBoxNumProcessors .setBounds(xField, y, 200, 25);
		add(comboBoxNumProcessors );
		y += 25 + margin;

		comboBoxNumProcessors .addActionListener(e -> {
            this.processorNumbers = (Integer) comboBoxNumProcessors.getSelectedItem();
		});
		
		JLabel labelQuantum = new JLabel("Quantum:");
		JTextField fieldQuantum = new TextField();
		
		// Rótulo "Algoritmo"
			JLabel labelScheduler = new JLabel("Algoritmo:");
			labelScheduler.setBounds(xLabel, y, labelWidth, fieldHeight);
			labelScheduler.setFont(defaultFont);
			add(labelScheduler);
			
			// Combo box para "Algoritmo"
			JComboBox<String> comboBox = new JComboBox<>(new String[] {"Round Roubin", "Menor Tarefa Primeiro"});
			comboBox.setBounds(xField, y, 200, 25);
			add(comboBox);
			y += 25 + margin;

			comboBox.addActionListener(e -> {
				this.scheduler = (String) comboBox.getSelectedItem();
				System.out.println(this.scheduler);
				if(this.scheduler == "Round Roubin") {
					labelQuantum.setVisible(true);
					fieldQuantum.setVisible(true);
				}else {
					labelQuantum.setVisible(false);
					fieldQuantum.setVisible(false);
				}
			});

		// Rótulo "Quantum"
		
		labelQuantum.setBounds(xLabel, y, labelWidth, fieldHeight);
		labelQuantum.setFont(defaultFont);
		add(labelQuantum);

		// Campo de texto para o quantum
		fieldQuantum.setBounds(xField, y, fieldWidth, fieldHeight);
		fieldQuantum.setFont(defaultFont);
		add(fieldQuantum);
		y += fieldHeight + margin;
		

		JButton buttonProcessor = new Button("Salvar");
		buttonProcessor.setBounds(xLabel, y, fieldWidth, fieldHeight);
		buttonProcessor.setFont(defaultFont);
		add(buttonProcessor);
		y += fieldHeight + margin;

		buttonProcessor.addActionListener(e -> {
			int quantumValue = !fieldQuantum.getText().isEmpty() ? Integer.parseInt(fieldQuantum.getText()) : 0;
			Quantum.setValue(quantumValue);
			
			JOptionPane.showMessageDialog(this, "Número de processadores: " + this.processorNumbers + "\nAlgoritmo: "+ this.scheduler, "Processador", JOptionPane.INFORMATION_MESSAGE);
		});

		// Separador horizontal
		separator = new JSeparator();
		separator.setBounds(0, y, Main.FRAME_WIDTH, 10);
		this.add(separator);
		y += 10 + margin;

		// Título "Processo"
		title = new JLabel("Processo");
		title.setBounds(xLabel, y, 300, fieldHeight);
		title.setFont(new Font("Arial", Font.BOLD, 16));
		add(title);
		y += 30;

		// Rótulo "Tempo de chegada"
		JLabel labelArrivalOrder = new JLabel("Tempo de chegada:");
		labelArrivalOrder.setBounds(xLabel, y, labelWidth, fieldHeight);
		labelArrivalOrder.setFont(defaultFont);
		add(labelArrivalOrder);

		// Campo de texto para o tempo de chegada
		JTextField fieldArrivalOrder = new TextField();
		fieldArrivalOrder.setBounds(xField, y, fieldWidth, fieldHeight);
		fieldArrivalOrder.setFont(defaultFont);
		add(fieldArrivalOrder);
		y += fieldHeight + margin;

		// Rótulo "Tempo de execução"
		JLabel labelExecutionTime = new JLabel("Tempo de execução:");
		labelExecutionTime.setBounds(xLabel, y, labelWidth, fieldHeight);
		labelExecutionTime.setFont(defaultFont);
		add(labelExecutionTime);

		// Campo de texto para o tempo de execução
		JTextField fieldExecutionTime = new TextField();
		fieldExecutionTime.setBounds(xField, y, fieldWidth, fieldHeight);
		fieldExecutionTime.setFont(defaultFont);
		add(fieldExecutionTime);
		y += fieldHeight + margin;

		// Botão "Adicionar processo"
		JButton buttonAddProcess = new Button("Adicionar processo");
		buttonAddProcess.setBounds(xLabel, y, fieldWidth, fieldHeight);
		buttonAddProcess.setFont(defaultFont);
		add(buttonAddProcess);
		y += fieldHeight + margin;


		// Ação do botão
		buttonAddProcess.addActionListener(e -> {
			int arrivalTime = !fieldArrivalOrder.getText().isEmpty() ? Integer.parseInt(fieldArrivalOrder.getText()) : 0;
			int executionTime = !fieldExecutionTime.getText().isEmpty()  ? Integer.parseInt(fieldExecutionTime.getText()) : 0;
			int quantum = !fieldQuantum.getText().isEmpty() ? Integer.parseInt(fieldQuantum.getText()) : 0;

			Process process = new Process(processCounter++, arrivalTime, executionTime);
			Scheduler.PROCESSES.add(process);
			processesTableModel.addProcess(process);
			processesTableModel.fireTableDataChanged();
			
			//processesTableModel.fireTableDataChanged();
			JOptionPane.showMessageDialog(this, process, "Processo adicionado!", JOptionPane.INFORMATION_MESSAGE);
		});
		
		JPanel executePanel = new JPanel();
		executePanel.setLayout(null);
		executePanel.setBackground(new Color(84,101,116));  
		executePanel.setBounds(0, y, Main.FRAME_WIDTH, 400);
		y = 10;
		
		// Título "Executar"
		title = new JLabel("Executar");
		title.setBounds(xLabel, 10, 300, fieldHeight);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD, 16));
		executePanel.add(title);
		y += 30;

		JButton buttonInit = new Button("Iniciar");
		buttonInit.setBounds(xLabel, y, fieldWidth, fieldHeight);
		buttonInit.setFont(defaultFont);
		executePanel.add(buttonInit);
		y += fieldHeight + margin;

		buttonInit.addActionListener(e -> {
			if(!Scheduler.PROCESSES.isEmpty()) {
				ArrayList<Thread> threads = new ArrayList<>();
				for (int i = 1; i <= this.processorNumbers; i++) {
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
		JScrollPane scrollPane = new JScrollPane(processTable);
		scrollPane.setBounds(xLabel, y, 700, 200);
		executePanel.add(scrollPane);
		y += 210;
		
		
		totalExecutionTime.setForeground(Color.WHITE);
		totalExecutionTime.setBounds(xLabel, y, labelWidth, fieldHeight);
		executePanel.add(totalExecutionTime);
		this.add(executePanel);
	}
}