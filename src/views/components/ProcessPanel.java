package views.components;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import models.Process;
import schedulers.Scheduler;
import controller.SchedulerController;

public class ProcessPanel extends BasePanel {
    private SchedulerController schedulerController;

    public ProcessPanel(SchedulerController schedulerController) {
        this.schedulerController = schedulerController;
        addLabelTitle("Processo");
        
        addLabel("Tempo de chegada:", defaultFont);
        JTextField fieldArrivalTime = addTextField();

        addLabel("Tempo de execução:",defaultFont);
        JTextField fieldExecutionTime = addTextField();

        JButton buttonAddProcess = addButton("Adicionar Processo");
        buttonAddProcess.addActionListener(e -> {
            int arrivalTime = parseInteger(fieldArrivalTime.getText());
            int executionTime = parseInteger(fieldExecutionTime.getText());
            schedulerController.addProcess(arrivalTime, executionTime);
            
            JOptionPane.showMessageDialog(this, "Processo adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private int parseInteger(String text) {
        try {
            return !text.isEmpty() ? Integer.parseInt(text) : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}

