package views.components;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.SchedulerController;
import schedulers.Scheduler;
import views.ProcessesTableModel;

public class ExecutionPanel extends BasePanel {
    public ExecutionPanel(ProcessesTableModel processesTableModel, SchedulerController schedulerController) {
    	this.setBackground(new Color(84,101,116));  
        JLabel title = addLabelTitle("Executar");
        title.setForeground(Color.WHITE);
        
        JButton buttonStart = addButton("Iniciar");
        buttonStart.addActionListener(e -> {
            try {
                HashMap<String, Number> results = schedulerController.startProcesses(ProcessorPanel.selectedAlgorithm);
                JOptionPane.showMessageDialog(this,
                    "Tempo de execução total: " + results.get("totalTime") +
                    "\nTempo médio de execução: " + results.get("averageExecutionTime") +
                    "\nTempo médio de espera: " + results.get("averageWaitingTime") +
                    "\nTrocas de contexto: " + results.get("contextChangeCounter"),
                    "Resultados", JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JTable table = new JTable(processesTableModel);
        table.getColumnModel().getColumn(1).setCellRenderer(new ProcessesTableModel.StatusCellRenderer());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(xLabel, y, 700, 200);
        add(scrollPane);
    }
}
