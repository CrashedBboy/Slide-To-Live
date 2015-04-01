package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ScorePanel extends JPanel {

	private static int RED = 240;
	private static int GREEN = 240;
	private static int BLUE = 240;
	private static int OPACITY = 180;
	private int xCursor;
	private int yCursor;
	// Player's record
	private int killNumber;
	private int passTime;
	private int score;

	public int yLocation;
	private Font font;
	private Timer moving;
	private Timer updateMouse;
	private boolean buttonALight;
	private boolean buttonBLight;
	public boolean isShowing;
	public boolean isRetry;
	public boolean isBackToMenu;

	private int origin;
	private boolean isHighScore;

	public ScorePanel() {
		setSize(300, 700);
		setLayout(null);
		yLocation = 130;
		setLocation(350, 0);
		this.setOpaque(false);
		moving = new Timer(1, new MoveAction());
		Mouse mouse = new Mouse();
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		isShowing = false;
		setVisible(isShowing);
		buttonALight = false;
		buttonBLight = false;
		updateMouse = new Timer(50, new MouseUpdate());
		updateMouse.start();
		isRetry = false;
		isHighScore = false;
	}

	public void retry() {
		yLocation = 130;
		isShowing = false;
		setVisible(isShowing);
		killNumber = 0;
		passTime = 0;
		score = 0;
		moving.stop();

		isRetry = true;
		isHighScore = false;
	}

	public void backToMenu() {
		yLocation = 130;
		isShowing = false;
		setVisible(isShowing);
		killNumber = 0;
		passTime = 0;
		score = 0;
		moving.stop();

		isBackToMenu = true;
		isHighScore = false;
	}

	public void setScore(int killNumber, int passTime) {
		this.killNumber = killNumber;
		this.passTime = passTime;
		this.score = killNumber * 10 + passTime;

		File scoreFile = new File("score.txt");
		if (scoreFile.exists()) {
			try {
				Scanner input = new Scanner(scoreFile);
				String temp = input.nextLine();
				origin = Integer.parseInt(temp);
				if (checkHighScore(origin)) {
					isHighScore = true;
					PrintWriter output = new PrintWriter(scoreFile);
					output.println("" + score);
					output.close();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else {
			PrintWriter output;
			try {
				output = new PrintWriter(scoreFile);
				output.println("" + score);
				output.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	public void move() {
		if (yLocation < 200) {
			moving.start();
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isShowing) {
			if(isHighScore)
			{
				ImageIcon highScore = new ImageIcon(getClass().getResource("Image/newRecord.gif"));
				g.drawImage(highScore.getImage(), 10, yLocation + 250, null);
			}
			g.setFont(new Font(null, Font.BOLD, 20));
			g.setColor(new Color(RED, GREEN, BLUE, OPACITY));
			g.fillRect(0, yLocation, 500, 250);
			g.setColor(Color.BLACK);
			g.drawString("Enemy Killed : " + killNumber, 20, yLocation + 40);
			g.drawString("Time : " + passTime + " s", 20, yLocation + 80);
			g.drawString("Score : " + score, 20, yLocation + 120);
			g.drawString("High Score : " + origin, 20, yLocation + 160);
			

			if (buttonALight) {
				g.setColor(new Color(RED - 150, GREEN, BLUE - 150, OPACITY ));
				g.fillRoundRect(20, yLocation + 200, 80, 30, 10, 10);
				g.setColor(new Color(RED - 100, GREEN, BLUE - 100, OPACITY - 30));
				g.fillRoundRect(190, yLocation + 200, 80, 30, 10, 10);
			} else if (buttonBLight) {
				g.setColor(new Color(RED - 100, GREEN, BLUE - 100, OPACITY - 30));
				g.fillRoundRect(20, yLocation + 200, 80, 30, 10, 10);
				g.setColor(new Color(RED - 150, GREEN, BLUE - 150, OPACITY ));
				g.fillRoundRect(190, yLocation + 200, 80, 30, 10, 10);
			} else {
				g.setColor(new Color(RED - 100, GREEN, BLUE - 100, OPACITY - 30));
				g.fillRoundRect(20, yLocation + 200, 80, 30, 10, 10);
				g.fillRoundRect(190, yLocation + 200, 80, 30, 10, 10);
			}
			g.setColor(Color.red);
			g.drawString("Retry", 30, yLocation + 220);
			g.drawString("Menu", 200, yLocation + 220);
		}
	}

	public boolean checkHighScore(int _origin) {
		if (_origin < score) {
			origin = score;
			return true;
		} else {
			return false;
		}
	}

	/**************************************************************************************/
	public class MoveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (yLocation < 200) {
				yLocation = yLocation + 3;
				isShowing = true;
				setVisible(isShowing);
				repaint();
			} else {
				moving.stop();
			}
		}
	}

	/**************************************************************************************/
	public class Mouse implements MouseMotionListener, MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			xCursor = e.getX();
			yCursor = e.getY();
			if (isOnButton(20, yLocation + 200, 80, 30, xCursor, yCursor)) {
				retry();
			}
			if (isOnButton(190, yLocation + 200, 80, 30, xCursor, yCursor)) {
				backToMenu();
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
			buttonALight = isOnButton(20, yLocation + 200, 80, 30, xCursor, yCursor);
			buttonBLight = isOnButton(190, yLocation + 200, 80, 30, xCursor, yCursor);
		}

	}

	/************************************************************************************************/
	public class MouseUpdate implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			repaint();
		}
	}
	/************************************************************************************************/
}
