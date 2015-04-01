package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel {

	private JLabel FPS;
	// Location of Mouse's cursor
	private float xCursor;
	private float yCursor;
	// motion about the player
	private float xVector;
	private float yVector;
	// location of player
	private float xPlayer;
	private float yPlayer;

	private boolean skill1Flicker;
	private boolean skill2Flicker;
	private boolean skill1Drawing;
	private boolean skill2Drawing;

	private static int PLAYER_SIZE = 30;
	private static int ENEMY_SIZE = 30;
	private static int SKILL_SIZE = 30;
	private static int PLAYER_MODE1_SIZE = 60;
	private static int PLAYER_MODE2_SIZE = 70;
	private static int FREEZE_ENEMY_SIZE = 50;

	private ImageIcon playerMode0;
	private ImageIcon playerMode1;
	private ImageIcon playerMode2;
	private ImageIcon skill1;
	private ImageIcon skill2;
	private ImageIcon freezeEnemy;
	private ImageIcon skill3;
	private ImageIcon Explosion;
	private ImageIcon Explosion1;
	private ImageIcon Explosion2;
	private ImageIcon Explosion3;
	public float degree;

	// Main execution of game
	private Timer main;
	// For FPS
	private Timer cleaner;
	private int fps;
	// Enemy
	private Timer enemyProduce;
	private int enemyNumber;
	private ArrayList<Enemy> enemy;
	public Random ran;
	// Run Time
	private JLabel passTimeLabel;
	private long startTime;
	private long endTime;
	private long passTime;
	private Font timeFont;

	// Skills
	private static final int skillPeriod = 5;
	private long startInvincible;
	private long passInvincible;
	private long startFreezing;
	private long passFreezing;
	private Timer skillProduce;
	private ArrayList<Skill> skills;
	private boolean skillMode1;
	private boolean skillMode2;
	private boolean skillMode3;
	private Timer skillFlicker;
	private ArrayList<Explosion> explosions;

	// Enemy Destroyed
	private int killNumber;
	private JLabel kill;

	// Showing score
	ScorePanel sPanel;
	private JLabel scoreLabel;
	private int score;
	// For change Panel
	private Timer change;

	public boolean isShowing;
	public boolean isBackToMenu;

	public GamePanel() {
		setLayout(null);
		setSize(1000, 700);
		setLocation(0, 0);
		this.setOpaque(false);

		playerMode0 = new ImageIcon(getClass().getResource("Image/cursor.gif"));
		playerMode1 = new ImageIcon(getClass().getResource("Image/cursor1.gif"));
		playerMode2 = new ImageIcon(getClass().getResource("Image/cursor2.gif"));
		skill1 = new ImageIcon(getClass().getResource("Image/invincibleSkill.gif"));
		skill2 = new ImageIcon(getClass().getResource("Image/iceSkill.gif"));
		freezeEnemy = new ImageIcon(getClass().getResource("Image/freezeEnemy.gif"));
		skill3 = new ImageIcon(getClass().getResource("Image/explodeSkill.gif"));
		Explosion = new ImageIcon(getClass().getResource("Image/explodeArea.gif"));
		Explosion1 = new ImageIcon(getClass().getResource("Image/explodeArea1.gif"));
		Explosion2 = new ImageIcon(getClass().getResource("Image/explodeArea2.gif"));
		Explosion3 = new ImageIcon(getClass().getResource("Image/explodeArea3.gif"));

		FPS = new JLabel();
		FPS.setBounds(50, 650, 200, 20);
		FPS.setForeground(Color.red);
		add(FPS);

		passTimeLabel = new JLabel();
		timeFont = new Font(passTimeLabel.getFont().getName(), passTimeLabel
				.getFont().getStyle(), 40);
		passTimeLabel.setBounds(50, 40, 400, 50);
		passTimeLabel.setForeground(Color.red);
		passTimeLabel.setFont(timeFont);
		add(passTimeLabel);
		startTime = System.currentTimeMillis();

		kill = new JLabel();
		kill.setBounds(720, 40, 250, 50);
		kill.setForeground(Color.red);
		kill.setFont(timeFont);
		add(kill);

		scoreLabel = new JLabel();
		scoreLabel.setBounds(350, 40, 350, 50);
		scoreLabel.setForeground(Color.red);
		scoreLabel.setFont(timeFont);
		add(scoreLabel);

		Mouse mouse = new Mouse();
		addMouseMotionListener(mouse);
		xPlayer = 500;
		yPlayer = 350;
		main = new Timer(1, new TimerListener());
		main.start();

		cleaner = new Timer(1000, new FpsListener());
		cleaner.start();

		ran = new Random();
		enemyProduce = new Timer(500, new EnemyProducer());
		enemyProduce.start();
		enemyNumber = 0;
		enemy = new ArrayList<Enemy>();

		skillProduce = new Timer(5000, new SkillProducer());
		skillProduce.start();
		skills = new ArrayList<Skill>();
		skillMode1 = false;
		skillMode2 = false;
		skillMode3 = false;
		killNumber = 0;
		explosions = new ArrayList<Explosion>();

		skillFlicker = new Timer(100, new FlickerPower());
		skillFlicker.start();
		skill1Flicker = false;
		skill2Flicker = false;
		skill1Drawing = true;
		skill2Drawing = true;

		sPanel = new ScorePanel();
		add(sPanel);

		change = new Timer(100, new ChangeDetecter());
		change.start();

		isShowing = false;
		setVisible(isShowing);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isShowing) {

			Graphics2D g2d = (Graphics2D) g.create();
			Graphics2D g2D = (Graphics2D) g.create();
			g2d.rotate((degree + 90) / 57, xPlayer, yPlayer);

			for (int i = 0; i < explosions.size(); i++) {
				if (explosions.get(i).explosionStyle == 0) {
					g2D.drawImage(Explosion.getImage(), (int) (explosions
							.get(i).xLocation - (explosions.get(i).SIZE0) / 2),
							(int) (explosions.get(i).yLocation - (explosions
									.get(i).SIZE0) / 2), null);
				}
				if (explosions.get(i).explosionStyle == 1) {
					g2D.drawImage(Explosion1.getImage(), (int) (explosions
							.get(i).xLocation - (explosions.get(i).SIZE1) / 2),
							(int) (explosions.get(i).yLocation - (explosions
									.get(i).SIZE1) / 2), null);
				}
				if (explosions.get(i).explosionStyle == 2) {
					g2D.drawImage(Explosion2.getImage(), (int) (explosions
							.get(i).xLocation - (explosions.get(i).SIZE2) / 2),
							(int) (explosions.get(i).yLocation - (explosions
									.get(i).SIZE2) / 2), null);
				}
				if (explosions.get(i).explosionStyle == 3) {
					g2D.drawImage(Explosion3.getImage(), (int) (explosions
							.get(i).xLocation - (explosions.get(i).SIZE3) / 2),
							(int) (explosions.get(i).yLocation - (explosions
									.get(i).SIZE3) / 2), null);
				}

			}
			g2d.drawImage(playerMode0.getImage(),
					(int) (xPlayer - PLAYER_SIZE / 2),
					(int) (yPlayer - PLAYER_SIZE / 2), null);
			if (skillMode2 == true && skill2Drawing) {
				g2d.drawImage(playerMode2.getImage(), (int) (xPlayer
						- (PLAYER_MODE2_SIZE) / 2 + 2),
						(int) (yPlayer - (PLAYER_MODE2_SIZE) / 2), null);
			}
			if (skillMode1 == true && skill1Drawing) {
				g2d.drawImage(playerMode1.getImage(), (int) (xPlayer
						- (PLAYER_MODE1_SIZE) / 2 + 2), (int) (yPlayer
						- (PLAYER_MODE1_SIZE) / 2 + 1), null);
			}

			for (int i = 0; i < enemy.size(); i++) {
				if (enemy.get(i).isFreezing) {
					g2D.drawImage(
							freezeEnemy.getImage(),
							(int) (enemy.get(i).xLocation - (FREEZE_ENEMY_SIZE) / 2),
							(int) (enemy.get(i).yLocation - (FREEZE_ENEMY_SIZE) / 2),
							null);
				} else {
					g2D.setColor(Color.WHITE);
					g2D.fillOval(
							(int) (enemy.get(i).xLocation - ENEMY_SIZE / 2),
							(int) (enemy.get(i).yLocation - ENEMY_SIZE / 2),
							ENEMY_SIZE, ENEMY_SIZE);
					g2D.setColor(Color.RED);
					g2D.fillOval(
							(int) (enemy.get(i).xLocation - (ENEMY_SIZE - 4) / 2),
							(int) (enemy.get(i).yLocation - (ENEMY_SIZE - 4) / 2),
							ENEMY_SIZE - 4, ENEMY_SIZE - 4);
				}
			}
			for (int i = 0; i < skills.size(); i++) {
				if (skills.get(i).mode == 1) {
					g2D.drawImage(skill1.getImage(),
							(int) (skills.get(i).xLocation - SKILL_SIZE / 2),
							(int) (skills.get(i).yLocation - SKILL_SIZE / 2),
							null);
				}
				if (skills.get(i).mode == 2) {
					g2D.drawImage(skill2.getImage(),
							(int) (skills.get(i).xLocation - SKILL_SIZE / 2),
							(int) (skills.get(i).yLocation - SKILL_SIZE / 2),
							null);
				}
				if (skills.get(i).mode == 3) {
					g2D.drawImage(skill3.getImage(),
							(int) (skills.get(i).xLocation - SKILL_SIZE / 2),
							(int) (skills.get(i).yLocation - SKILL_SIZE / 2),
							null);
				}
			}

			Graphics2D g2 = (Graphics2D) g;
			float thickness = 4;
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(thickness));
			g2.setColor(Color.WHITE);
			g2.drawRoundRect(50, 100, 900, 550, 20, 20);
			g2.setStroke(oldStroke);
			g2D.dispose();
			g2d.dispose();
		}

	}// paint

	public void restart() {
		xPlayer = 500;
		yPlayer = 350;
		xVector = 0;
		yVector = 0;
		enemy.clear();
		skills.clear();

		killNumber = 0;
		startTime = System.currentTimeMillis();

		main.start();
		cleaner.start();
		enemyProduce.start();
		skillProduce.start();
		isShowing = true;
		setVisible(isShowing);
		sPanel.isShowing = false;
		sPanel.isRetry = false;
	}

	public void gameStop() {
		main.stop();
		cleaner.stop();
		enemyProduce.stop();
		skillProduce.stop();
	}

	/*************************************************************************************************************/
	public class Mouse implements MouseMotionListener, MouseListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			xCursor = e.getX();
			yCursor = e.getY();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			xCursor = e.getX();
			yCursor = e.getY();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	} // for Mouse

	/*************************************************************************************************************/
	public class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			for (int i = 0; i < enemy.size(); i++) {
				enemy.get(i).xDistance = xPlayer - enemy.get(i).xLocation;
				enemy.get(i).yDistance = yPlayer - enemy.get(i).yLocation;
				enemy.get(i).tangent = (float) Math.sqrt(enemy.get(i).yDistance
						* enemy.get(i).yDistance + enemy.get(i).xDistance
						* enemy.get(i).xDistance);
				enemy.get(i).setPlayerState(skillMode1, skillMode2);
				boolean C = enemy.get(i).Iscollision();

				if (C && skillMode2 == true && !enemy.get(i).isFreezing) {

					enemy.get(i).trigger();
					enemy.get(i).countTime();
					enemy.get(i).xDistance = xPlayer - enemy.get(i).xLocation;
					enemy.get(i).yDistance = yPlayer - enemy.get(i).yLocation;
					enemy.get(i).tangent = (float) Math
							.sqrt(enemy.get(i).yDistance
									* enemy.get(i).yDistance
									+ enemy.get(i).xDistance
									* enemy.get(i).xDistance);

					if (skillMode1 == true) {
						enemy.remove(i);
						killNumber++;
					}
				}

				if ((!C || skillMode2 == false) && !enemy.get(i).isFreezing) {

					enemy.get(i).xVector = (enemy.get(i).xDistance / enemy
							.get(i).tangent) * 1 / 3;
					enemy.get(i).yVector = (enemy.get(i).yDistance / enemy
							.get(i).tangent) * 1 / 3;
					enemy.get(i).xLocation = enemy.get(i).xLocation
							+ enemy.get(i).xVector;
					enemy.get(i).yLocation = enemy.get(i).yLocation
							+ enemy.get(i).yVector;
					enemy.get(i).setPlayerState(skillMode1, skillMode2);
					if (enemy.get(i).Iscollision()) {
						if (skillMode1 == false) {
							sPanel.setScore(killNumber, (int) passTime);
							sPanel.move();
							gameStop();
						}
						if (skillMode1 == true) {
							enemy.remove(i);
							killNumber++;
						}
					} else {
						enemy.get(i).countTime();
					}
				}
			}// For loop

			for (int i = 0; i < enemy.size(); i++) {
				enemy.get(i).xDistance = xPlayer - enemy.get(i).xLocation;
				enemy.get(i).yDistance = yPlayer - enemy.get(i).yLocation;
				enemy.get(i).tangent = (float) Math.sqrt(enemy.get(i).yDistance
						* enemy.get(i).yDistance + enemy.get(i).xDistance
						* enemy.get(i).xDistance);
				enemy.get(i).setPlayerState(skillMode1, skillMode2);
				boolean C = enemy.get(i).Iscollision();
				if (enemy.get(i).isFreezing) {
					enemy.get(i).countTime();
					if (C && skillMode1 == true) {
						enemy.remove(i);
						killNumber++;
					}
				}
			}

			for (int i = 0; i < skills.size(); i++) {
				skills.get(i).xDistance = xPlayer - skills.get(i).xLocation;
				skills.get(i).yDistance = yPlayer - skills.get(i).yLocation;
				skills.get(i).tangent = (float) Math
						.sqrt(skills.get(i).yDistance * skills.get(i).yDistance
								+ skills.get(i).xDistance
								* skills.get(i).xDistance);
				skills.get(i).setPlayerState(skillMode1, skillMode2);
				if (skills.get(i).Iscollision()) {
					if (skills.get(i).mode == 1) {
						skillMode1 = true; // For Invincible
						skill1Flicker = false;
						skill1Drawing = true;
						skills.remove(i);
						startInvincible = System.currentTimeMillis();
					} else if (skills.get(i).mode == 2) {
						skillMode2 = true;
						skill2Flicker = false;
						skill2Drawing = true;
						skills.remove(i);
						startFreezing = System.currentTimeMillis();
					} else if (skills.get(i).mode == 3) {
						skillMode3 = true;
						Explosion ex = new Explosion();
						ex.startTime = System.currentTimeMillis();
						ex.setLocation((int) xPlayer, (int) yPlayer);
						explosions.add(ex);
						skills.remove(i);
					}
				}
			}
			endTime = System.currentTimeMillis();
			passTime = (endTime - startTime) / 1000;
			if (skillMode1) {
				passInvincible = (endTime - startInvincible) / 1000;
			}
			if (skillMode2) {
				passFreezing = (endTime - startFreezing) / 1000;
			}

			if (passInvincible >= skillPeriod) {
				skillMode1 = false;
				skill1Flicker = false;
				skill1Drawing = true;
			}
			if (passInvincible >= 4 && passInvincible < 5) {
				skill1Flicker = true;
			}
			if (passFreezing >= skillPeriod) {
				skillMode2 = false;
				skill2Flicker = false;
				skill2Drawing = true;
			}
			if (passFreezing >= 4 && passFreezing < 5) {
				skill2Flicker = true;
			}

			for (int i = 0; i < explosions.size(); i++) {
				for (int k = 0; k < enemy.size(); k++) {
					int xD = explosions.get(i).xLocation
							- (int) enemy.get(k).xLocation;
					int yD = explosions.get(i).yLocation
							- (int) enemy.get(k).yLocation;
					int D = (int) Math.sqrt(xD * xD + yD * yD);
					if (explosions.get(i).Iscollision(D)) {
						enemy.remove(k);
						killNumber++;
					}
				}
				explosions.get(i).passTime = endTime
						- explosions.get(i).startTime;
				if (explosions.get(i).passTime >= 800) {
					explosions.remove(i);
				} else if (explosions.get(i).passTime >= 200
						&& explosions.get(i).passTime <= 300) {
					explosions.get(i).explosionStyle = 1;
				} else if (explosions.get(i).passTime >= 100
						&& explosions.get(i).passTime < 200) {
					explosions.get(i).explosionStyle = 2;
				} else if (explosions.get(i).passTime >= 0
						&& explosions.get(i).passTime < 100) {
					explosions.get(i).explosionStyle = 3;
				} else {
					explosions.get(i).explosionStyle = 0;
				}
			}

			float xDistance = xCursor - xPlayer;
			float yDistance = yCursor - yPlayer;
			float tangent = (float) Math.sqrt(xDistance * xDistance + yDistance
					* yDistance);
			xVector = (xDistance) / 130;
			yVector = (yDistance) / 130;
			float sinTheta = yDistance / tangent;
			float cosTheta = xDistance / tangent;
			float radian = (float) Math.asin(sinTheta);
			float degreeSin = (float) (radian * (180 / Math.PI));
			if (sinTheta > 0 && cosTheta > 0) {
				degree = degreeSin;

			} else if (cosTheta < 0) {
				degree = 180 - degreeSin;
			} else if (sinTheta < 0 && cosTheta > 0) {
				degree = 360 + degreeSin;
			}

			if (xPlayer > 50 && xPlayer < 950) {
				xPlayer = (xPlayer + xVector);
			} else if (xPlayer <= 50) {
				xPlayer = xPlayer + 1;
			} else if (xPlayer >= 950) {
				xPlayer = xPlayer - 1;
			}

			if (yPlayer > 100 && yPlayer < 650) {
				yPlayer = (yPlayer + yVector);
			} else if (yPlayer <= 100) {
				yPlayer = yPlayer + 1;
			} else if (yPlayer >= 650) {
				yPlayer = yPlayer - 1;
			}

			passTimeLabel.setText("Time: " + passTime + " s");
			kill.setText("Killed : " + killNumber);
			score = killNumber * 10 + (int) (passTime);
			scoreLabel.setText("Score : " + score);
			repaint();
			fps++;

		}
	}

	/*************************************************************************************************************/
	public class FpsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			FPS.setText("FPS : " + fps);
			fps = 0;
		}
	}

	/*************************************************************************************************************/
	public class EnemyProducer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (enemy.size() < 80) {
				Enemy boy = new Enemy((int) xPlayer, (int) yPlayer);
				enemy.add(boy);
			}
		}
	}

	/*****************************************************************************************************************/
	public class SkillProducer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Skill bboy = new Skill();
			skills.add(bboy);

		}
	}

	/******************************************************************************************************************/
	public class ChangeDetecter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (sPanel.isRetry == true) {
				restart();
			}
			if (sPanel.isBackToMenu == true) {
				isBackToMenu = true;
				isShowing = false;
				setVisible(isShowing);
				sPanel.isBackToMenu = false;
			}

		}
	}

	/*******************************************************************************************************************/
	public class FlickerPower implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (skill1Flicker) {
				skill1Drawing = !skill1Drawing;
			}
			if (skill2Flicker) {
				skill2Drawing = !skill2Drawing;
			}
		}
	}

}// For MyPanel
