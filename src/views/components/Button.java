package views.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class Button extends JButton{
	private Color backgroundColor;
	private Color color;
	private Color borderColor;
	
	public Button(String text) {
		super(text);
		this.backgroundColor = new Color(0x28a745);
		this.borderColor = backgroundColor;
		this.color = Color.WHITE;
		this.setButtonSettings();
	}
	
	public Button(String text, Color backgroundColor) {
		super(text);
		this.backgroundColor = backgroundColor;
		this.borderColor = backgroundColor;
		color = Color.WHITE;
		
		this.setButtonSettings();
	}
	
	private void setButtonSettings() {
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.onHover();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics2d.setColor(this.backgroundColor);
		graphics2d.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
		
		graphics2d.setColor(this.borderColor);
        graphics2d.setStroke(new BasicStroke(2));
        graphics2d.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 20, 20);
		
		graphics2d.setColor(this.color);
		graphics2d.setFont(getFont());
		
		FontMetrics fontMetrics = graphics2d.getFontMetrics();
	    int textWidth = fontMetrics.stringWidth(getText());
	    int textHeight = fontMetrics.getAscent();
	    int x = (getWidth() - textWidth) / 2;  
	    int y = (getHeight() + textHeight) / 2; 

	    graphics2d.drawString(getText(), x, y);
		
	}
	
	private void onHover() {
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				color = backgroundColor;
				borderColor = color;
				backgroundColor = Color.WHITE;
				repaint();
			}
			
			@Override
            public void mouseExited(MouseEvent e) {
                backgroundColor = color;
                borderColor = backgroundColor;
                color = Color.WHITE;
                repaint(); 
            }
		});
	}
}
