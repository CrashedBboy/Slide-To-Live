package Main;

public class Explosion {
	public int xLocation;
	public int yLocation;
	public static int SIZE0 = 500;
	public static int SIZE1 = 490;
	public static int SIZE2 = 480;
	public static int SIZE3 = 470;
	public long startTime;
	public long passTime;
	public int explosionStyle;

	public Explosion() {

	}

	public void setLocation(int xPlayer, int yPlayer) {
		xLocation = xPlayer;
		yLocation = yPlayer;
	}

	public boolean Iscollision(int Distance) {
		boolean collision;
		if(Distance <= 250 )
			collision = true;
		else
			collision = false;
		return collision;
	}
}
