package Main;

import java.util.Random;

public class Enemy {

	public Random ran;
	private static int SIZE = 30;
	public float xLocation;
	public float yLocation;
	public float xVector;
	public float yVector;
	public float xDistance;
	public float yDistance;
	public float tangent;
	private boolean collision;
	public boolean isFreezing;
	public long startFreezing;
	public long passFreezing;
	public boolean enable;
	private boolean playerMode1;
	private boolean playerMode2;
	private float xPlayer;
	private float yPlayer;
	private int playerSize;

	public Enemy(int xPlayer, int yPlayer) {
		ran = new Random();
		xLocation = ran.nextInt(940) + 30;
		yLocation = ran.nextInt(640) + 30;
		setPlayerLocation(xPlayer, yPlayer);
		this.xPlayer = xPlayer;
		this.yPlayer = yPlayer;
		xDistance = xPlayer - xLocation;
		yDistance = yPlayer - yLocation;
		tangent = (float) Math.sqrt(yDistance * yDistance + xDistance
				* xDistance);
		checkLocation();
		enable = true;
	}

	public void setPlayerLocation(int xLocation, int yLocation) {
		xPlayer = xLocation;
		yPlayer = yLocation;
	}

	public void checkLocation()
	{
		while(tangent < 300)
		{
			xLocation = ran.nextInt(940) + 30;
			yLocation = ran.nextInt(640) + 30;
			xDistance = xPlayer - xLocation;
			yDistance = yPlayer - yLocation;
			tangent = (float) Math.sqrt(yDistance * yDistance + xDistance
					* xDistance);
		}
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

	public void trigger() {
		if (enable == true) {
			isFreezing = true;
			startFreezing = System.currentTimeMillis();
			enable = false;
		}
	}

	public void countTime() {
		passFreezing = (System.currentTimeMillis() - startFreezing) / 1000;
		if (passFreezing >= 5 && isFreezing) {
			isFreezing = false;
			enable = true;
		}
	}

}
