package views.components;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import models.Processor;

public class ProcessorPanel extends BasePanel {
    public ProcessorPanel() {
    	addLabelTitle("Processador");
        addLabel("Número de processadores:", defaultFont);

        JComboBox<Integer> comboBoxProcessors = addProcessorCoresComboBox();
        comboBoxProcessors.addActionListener(e -> 
            Processor.setProcessorsNumbers((Integer) comboBoxProcessors.getSelectedItem())
        );

        addLabel("Algoritmo:", defaultFont);
        JComboBox<String> comboBoxAlgorithm = addALgorithmsComboBox(
            new String[]{"Round Robin", "Menor Tarefa Primeiro"}
        );
        
        JLabel labelQuantum = new JLabel("Quantum");
        labelQuantum.setBounds(xLabel, y, componentsWidth, componentsHeight);
        labelQuantum.setFont(defaultFont);
        
        JTextField fieldQuantum = new TextField();
        fieldQuantum.setBounds(xField, y, componentsWidth, componentsHeight);
        fieldQuantum.setFont(defaultFont);
        this.updateY();
        
        add(labelQuantum);
        add(fieldQuantum);
        comboBoxAlgorithm.addActionListener(e -> {
        	if("Menor Tarefa Primeiro".equals((String) comboBoxAlgorithm.getSelectedItem())) {
        		labelQuantum.setVisible(false);
        		fieldQuantum.setVisible(false);
        	}else {
        		labelQuantum.setVisible(true);
        		fieldQuantum.setVisible(true);
        	}
        });
        
        JButton buttonProcessor = this.addButton("Salvar");
		buttonProcessor.addActionListener(e -> {
			int quantumValue = !fieldQuantum.getText().isEmpty() ? Integer.parseInt(fieldQuantum.getText()) : 0;
			Processor.setQuantum(quantumValue);
			
			JOptionPane.showMessageDialog(this, "Número de processadores: " + Processor.getProcessorsNumbers() + "\nAlgoritmo: "+ "Round Roubin", "Processador", JOptionPane.INFORMATION_MESSAGE);
		});
        
    }
}
