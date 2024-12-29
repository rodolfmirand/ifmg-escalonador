package views.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class TextField extends JTextField{
	public TextField() {
		this.setFont(new Font("Arial", Font.PLAIN, 16));
		
		this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));  // Borda verde ao ganhar o foco
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));  // Borda cinza ao perder o foco
            }
        });
	}
}
