package views;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

import models.Process;
import schedulers.Scheduler;

public class Settings extends JPanel{
	public Settings() {
		this.setLayout(null);
		
		this.init();
	}
	
	private void init() {
	    setLayout(null);
	    
	    // Defina a fonte uma vez
	    ProcessesTableModel processesTableModel = new ProcessesTableModel(Scheduler.PROCESSES);
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
	    
	    // Campo de texto para o número de processadores
	    JTextField fieldNumProcessors = new JTextField();
	    fieldNumProcessors.setBounds(xField, y, fieldWidth, fieldHeight);
	    fieldNumProcessors.setFont(defaultFont); 
	    add(fieldNumProcessors);
	    y += fieldHeight + margin;

	    // Rótulo "Número de processos em status pronto"
	    JLabel labelNumProcesses = new JLabel("Número de processos em status pronto:");
	    labelNumProcesses.setBounds(xLabel, y, labelWidth, fieldHeight);
	    labelNumProcesses.setFont(defaultFont);
	    add(labelNumProcesses);

	    // Campo de texto para o número de processos
	    JTextField fieldNumProcesses = new JTextField();
	    fieldNumProcesses.setBounds(xField, y, fieldWidth, fieldHeight);
	    fieldNumProcesses.setFont(defaultFont); 
	    add(fieldNumProcesses);
	    y += fieldHeight + margin;

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

	    // Botão "Adicionar processo"
	    JButton buttonSubmit = new JButton("Adicionar processo:");
	    buttonSubmit.setBounds(xLabel, y, fieldWidth, fieldHeight);
	    buttonSubmit.setFont(defaultFont);
	    add(buttonSubmit);
	    y += fieldHeight + margin;
	    
	    // Separador horizontal
	    separator = new JSeparator();
	    separator.setBounds(0, y, Main.FRAME_WIDTH, 10);
	    this.add(separator);
	    y += 10 + margin;

	    // Ação do botão
	    buttonSubmit.addActionListener(e -> {
	        String numProcessors = fieldNumProcessors.getText();
	        String numProcesses = fieldNumProcesses.getText();
	        
	        int arrivalTime = !fieldArrivalOrder.getText().isEmpty() ? Integer.parseInt(fieldArrivalOrder.getText()) : 0;
	        int executionTime = !fieldExecutionTime.getText().isEmpty()  ? Integer.parseInt(fieldExecutionTime.getText()) : 0;
	        int quantum = !fieldQuantum.getText().isEmpty() ? Integer.parseInt(fieldQuantum.getText()) : 0;
	        
	        Process process = new Process(1, arrivalTime, executionTime, quantum);
	        Scheduler.PROCESSES.add(process);
	        
	        processesTableModel.fireTableDataChanged();
	        JOptionPane.showMessageDialog(this, process, "Processo adicionado!", JOptionPane.INFORMATION_MESSAGE);
	    });
	    
	    
	    JTable processTable = new JTable(processesTableModel);
	    
	    JScrollPane scrollPane = new JScrollPane(processTable);
        scrollPane.setBounds(50, 350, 600, 200);  
        add(scrollPane);
	}

}
