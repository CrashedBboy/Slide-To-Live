package Main;

public class main {

	public static void main(String[] args) {

		MyFrame frame = new MyFrame();
		MenuPanel menu = new MenuPanel();

		BackgroundPanel bPanel = new BackgroundPanel();
		frame.add(bPanel);
		bPanel.add(menu);
		frame.setVisible(true);
	}
}
