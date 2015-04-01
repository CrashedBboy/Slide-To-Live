package Main;

import java.util.Random;

public class Skill {
	private Random ran;
	public float xLocation;
	public float yLocation;
	public float xDistance;
	public float yDistance;
	private boolean playerMode1;
	private boolean playerMode2;
	private static int SIZE = 30;
	private int playerSize;
	public float tangent;
	private boolean collision;
	public int mode;

	Skill() {
		ran = new Random();
		mode = ran.nextInt(3)+1 ;
		xLocation = ran.nextInt(800) + 100;
		yLocation = ran.nextInt(450) + 150;
	}

	public void setPlayerState(boolean skillMode1, boolean skillMode2) {
		playerMode1 = skillMode1;
		playerMode2 = skillMode2;
		if (playerMode1 == false && playerMode2 == false)
			playerSize = 30;
		else
			playerSize = 60;
	}

	public boolean Iscollision() {
		if (playerMode1 == false && playerMode2 == false) {
			if (tangent <= (playerSize + SIZE) / 2) {
				collision = true;
			} else {
				collision = false;
			}
		} else {
			if (tangent <= (playerSize + SIZE) / 2) {
				collision = true;
			} else {
				collision = false;
			}
		}
		return collision;
	}
}
