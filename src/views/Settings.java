package views;

import java.awt.Font;
import java.util.ArrayList;

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
		ProcessesTableModel processesTableModel = new ProcessesTableModel(new ArrayList<Process>());
		Font defaultFont = new Font("Arial", Font.PLAIN, 14);

		int labelWidth = 250, fieldWidth = 200, fieldHeight = 25;
		int margin = 10;

		int xLabel = 50, xField = xLabel + labelWidth + margin;
		int y = 20;

		// Título "Processador"
		JLabel title = new JLabel("Processador");
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

		// Rótulo "Quantum"
		JLabel labelQuantum = new JLabel("Quantum:");
		labelQuantum.setBounds(xLabel, y, labelWidth, fieldHeight);
		labelQuantum.setFont(defaultFont);
		add(labelQuantum);

		// Campo de texto para o quantum
		JTextField fieldQuantum = new JTextField();
		fieldQuantum.setBounds(xField, y, fieldWidth, fieldHeight);
		fieldQuantum.setFont(defaultFont);
		add(fieldQuantum);
		y += fieldHeight + margin;

		JButton buttonProcessor = new JButton("Salvar");
		buttonProcessor.setBounds(xLabel, y, fieldWidth, fieldHeight);
		buttonProcessor.setFont(defaultFont);
		add(buttonProcessor);
		y += fieldHeight + margin;

		buttonProcessor.addActionListener(e -> {
			int quantumValue = !fieldQuantum.getText().isEmpty() ? Integer.parseInt(fieldQuantum.getText()) : 0;
			Quantum.setValue(quantumValue);
		});

		// Separador horizontal
		JSeparator separator = new JSeparator();
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
		JTextField fieldArrivalOrder = new JTextField();
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
		JTextField fieldExecutionTime = new JTextField();
		fieldExecutionTime.setBounds(xField, y, fieldWidth, fieldHeight);
		fieldExecutionTime.setFont(defaultFont);
		add(fieldExecutionTime);
		y += fieldHeight + margin;

		// Botão "Adicionar processo"
		JButton buttonAddProcess = new JButton("Adicionar processo:");
		buttonAddProcess.setBounds(xLabel, y, fieldWidth, fieldHeight);
		buttonAddProcess.setFont(defaultFont);
		add(buttonAddProcess);
		y += fieldHeight + margin;

		// Separador horizontal
		separator = new JSeparator();
		separator.setBounds(0, y, Main.FRAME_WIDTH, 10);
		this.add(separator);
		y += 10 + margin;

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

		JLabel labelScheduler = new JLabel("Algoritmo:");
		labelScheduler.setBounds(xLabel, y, labelWidth, fieldHeight);
		labelScheduler.setFont(defaultFont);
		add(labelScheduler);

		JComboBox<String> comboBox = new JComboBox<>(new String[] {"Round Roubin", "Menor Tarefa Primeiro"});
		comboBox.setBounds(xField, y, 200, 25);
		add(comboBox);
		y += 25 + margin;

		comboBox.addActionListener(e -> {
			this.scheduler = (String) comboBox.getSelectedItem();
		});

		JButton buttonInit = new JButton("Iniciar:");
		buttonInit.setBounds(xLabel, y, fieldWidth, fieldHeight);
		buttonInit.setFont(defaultFont);
		add(buttonInit);
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
			}
		});
		
		JTable processTable = new JTable(processesTableModel);
		JScrollPane scrollPane = new JScrollPane(processTable);
		scrollPane.setBounds(50, y, 600, 200);
		add(scrollPane);
	}
}