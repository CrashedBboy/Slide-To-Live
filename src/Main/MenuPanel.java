package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import Main.GamePanel.ChangeDetecter;
import Main.ScorePanel.Mouse;
import Main.ScorePanel.MouseUpdate;
import Main.ScorePanel.MoveAction;

public class MenuPanel extends JPanel {

	private static int Y = 130;
	private static int X = 450;
	private boolean isShowing;
	private int xCursor;
	private int yCursor;
	private Timer updateMouse;
	private boolean buttonALight;
	private boolean buttonBLight;
	private boolean buttonCLight;
	private static int buttonX = 405;
	private static int buttonAY = 340;
	private static int buttonCY = 420;
	private static int BUTTON_WIDTH = 190;
	private static int BUTTON_HEIGHT = 60;
	private GamePanel game;
	private Timer change;// For change Panel
	private ImageIcon menuTitle;
	private ImageIcon focus;
	private ImageIcon button;
	private ImageIcon start;
	private ImageIcon help;
	private ImageIcon quit;
	private ImageIcon startSelect;
	private ImageIcon helpSelect;
	private ImageIcon quitSelect;
	private ImageIcon explode;

	public MenuPanel() {
		// Panel's set
		setSize(1000, 700);
		setLayout(null);
		setLocation(0, 0);
		this.setOpaque(false);
		menuTitle = new ImageIcon(getClass().getResource("Image/menuTitle.gif"));
		focus = new ImageIcon(getClass().getResource("Image/selected.gif"));
		button = new ImageIcon(getClass().getResource("Image/button.gif"));
		start = new ImageIcon(getClass().getResource("Image/start.gif"));
		quit = new ImageIcon(getClass().getResource("Image/quit.gif"));
		startSelect = new ImageIcon(getClass().getResource("Image/startSelect.gif"));
		quitSelect = new ImageIcon(getClass().getResource("Image/quitSelect.gif"));
		explode = new ImageIcon(getClass().getResource("Image/explode.gif"));
		
		// Mouse
		Mouse mouse = new Mouse();
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		updateMouse = new Timer(50, new MouseUpdate());
		updateMouse.start();
		// showing
		isShowing = true;
		setVisible(isShowing);
		// Add game panel
		game = new GamePanel();
		game.gameStop();
		add(game);
		// Add help panel
		change = new Timer(50, new ChangeDetecter());
		change.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isShowing) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.rotate(-0.07, 300, 150);
			g.drawImage(explode.getImage(), buttonX-100, buttonAY-300, null);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRoundRect(370, 200, 260, 300, 10, 10);
			g2d.drawImage(menuTitle.getImage(), 260, 155, null);

			if (buttonALight) {
				g.drawImage(button.getImage(), buttonX, buttonCY, null);
				g.drawImage(quit.getImage(), buttonX +55, buttonCY + 15, null);

				g.drawImage(button.getImage(), buttonX, buttonAY, null);
				g.drawImage(startSelect.getImage(), buttonX + 45, buttonAY + 15, null);
				g.drawImage(focus.getImage(), 335, buttonAY - 25, null);

			} else if (buttonBLight) {
				g.drawImage(button.getImage(), buttonX, buttonAY, null);
				g.drawImage(button.getImage(), buttonX, buttonCY, null);
				g.drawImage(start.getImage(), buttonX + 45, buttonAY + 15, null);
				g.drawImage(quit.getImage(), buttonX +55, buttonCY + 15, null);
			} else if (buttonCLight) {
				g.drawImage(button.getImage(), buttonX, buttonAY, null);
				g.drawImage(start.getImage(), buttonX + 45, buttonAY + 15, null);

				g.drawImage(button.getImage(), buttonX, buttonCY, null);
				g.drawImage(quitSelect.getImage(), buttonX +55, buttonCY + 15, null);
				g.drawImage(focus.getImage(), 335, buttonCY - 25, null);
			} else {
				g.drawImage(button.getImage(), buttonX, buttonAY, null);
				g.drawImage(button.getImage(), buttonX, buttonCY, null);
				g.drawImage(start.getImage(), buttonX + 45, buttonAY + 15, null);
				g.drawImage(quit.getImage(), buttonX +55, buttonCY + 15, null);
			}
			
			g2d.dispose();
		}
	}

	public boolean isOnButton(int xLocation, int yLocation, int width,
			int height, int xCursor, int yCursor) {
		boolean isOn;
		if (xCursor >= xLocation && xCursor <= xLocation + width
				&& yCursor >= yLocation && yCursor <= yLocation + height) {
			isOn = true;
		} else {
			isOn = false;
		}
		return isOn;
	}

	public void gameStart() {
		isShowing = false;
		game.restart();
		updateMouse.stop();
	}

	/**************************************************************************************/
	public class Mouse implements MouseMotionListener, MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			xCursor = e.getX();
			yCursor = e.getY();
			if (isOnButton(buttonX, buttonAY, BUTTON_WIDTH, BUTTON_HEIGHT,
					xCursor, yCursor)) {
				gameStart();
			}
			if (isOnButton(buttonX, buttonCY, BUTTON_WIDTH, BUTTON_HEIGHT,
					xCursor, yCursor)) {
				System.exit(0);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			xCursor = e.getX();
			yCursor = e.getY();
		}

	}

	/************************************************************************************************/
	public class MouseUpdate implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			repaint();
			buttonALight = isOnButton(buttonX, buttonAY, BUTTON_WIDTH,
					BUTTON_HEIGHT, xCursor, yCursor);
			buttonCLight = isOnButton(buttonX, buttonCY, BUTTON_WIDTH,
					BUTTON_HEIGHT, xCursor, yCursor);
		}
	}

	/************************************************************************************************/
	public class ChangeDetecter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (game.isBackToMenu == true) {
				game.isBackToMenu = false;
				isShowing = true;
				setVisible(isShowing);
				updateMouse.start();

			}
		}
	}
}
