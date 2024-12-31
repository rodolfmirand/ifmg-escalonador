package views.components;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HeaderPanel extends BasePanel {
    public HeaderPanel() {
        // Adiciona o ícone
        addSchedulersIcon();

        // Adiciona o título
        addLabel("Escalonadores", xLabel + 70);

        // Atualiza o espaçamento após o título
        updateY(50);
    }

    private void addLabel(String text, int x) {
    	JLabel label = new JLabel(text);
		label.setBounds(x, y, this.componentsWidth ,this.componentsHeight);
		label.setFont(titleFont);
		add(label);
	}

	private void addSchedulersIcon() {
        try {
            ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File("src/assets/logo.png")));
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setBounds(xLabel, y, 50, 50);
            add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
