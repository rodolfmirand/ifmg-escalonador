package views.components;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;

public abstract class BasePanel extends JPanel {
    protected final Font defaultFont = new Font("Arial", Font.PLAIN, 14);
    protected final Font titleFont = new Font("Arial", Font.BOLD, 16);
    protected final int componentsWidth = 250;
    protected final int componentsHeight = 25;
    protected final int margin = 10;
    protected final int xLabel = 50;
    protected final int xField = xLabel + componentsWidth + margin;
    protected int y = 10;

    public BasePanel() {
        setLayout(null);
    }

    protected JLabel addLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(xLabel, y, componentsWidth, componentsHeight);
        label.setFont(font);
        add(label);
        return label;
    }
    
    protected JLabel addLabelTitle(String text) {
    	JLabel labelTitle = addLabel(text, titleFont);
    	updateY();
    	
    	return labelTitle;
    }

    protected JTextField addTextField() {
        JTextField textField = new TextField();
        textField.setBounds(xField, y, componentsWidth, componentsHeight);
        textField.setFont(defaultFont);
        add(textField);
        updateY();
        return textField;
    }

    protected JButton addButton(String text) {
        JButton button = new Button(text);
        button.setBounds(xLabel, y, componentsWidth, componentsHeight);
        button.setFont(defaultFont);
        add(button);
        updateY();
        return button;
    }

    protected JComboBox<String> addALgorithmsComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBounds(xField, y, componentsWidth, componentsHeight);
        add(comboBox);
        updateY();
        return comboBox;
    }

    protected JComboBox<Integer> addProcessorCoresComboBox() {
        JComboBox<Integer> comboBoxProcessors = new JComboBox<>();
        for (int i = 1; i <= Runtime.getRuntime().availableProcessors(); i++) {
            comboBoxProcessors.addItem(i);
        }
        comboBoxProcessors.setBounds(xField, y, componentsWidth, componentsHeight);
        add(comboBoxProcessors);
        updateY();
        return comboBoxProcessors;
    }
    
    protected void updateY() {
        y += componentsHeight + margin;
    }
    
    protected void updateY(int componentHeight) {
        y += componentHeight + margin;
    }
}

