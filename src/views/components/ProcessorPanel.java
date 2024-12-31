package views.components;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import models.Processor;

public class ProcessorPanel extends BasePanel {
	protected static String selectedAlgorithm = "Round Robin";
	
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
			selectedAlgorithm = (String) comboBoxAlgorithm.getSelectedItem();
			int quantumValue = !fieldQuantum.getText().isEmpty() ? Integer.parseInt(fieldQuantum.getText()) : 1;
			Processor.setQuantum(quantumValue);
			
			JOptionPane.showMessageDialog(null, "Número de processadores: " + Processor.getProcessorsNumbers() + "\nAlgoritmo: "+ selectedAlgorithm, "Processador", JOptionPane.INFORMATION_MESSAGE);
		});
        
    }
}
