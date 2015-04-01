package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	
	private ImageIcon background;
	
	public BackgroundPanel() {
		setSize(1000, 700);
		setLayout(null);
		background = new ImageIcon(getClass().getResource("Image/background.gif"));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(background.getImage(), 0, 0, null);
	}
}
