package BackgroundChanger;

import java.awt.Dimension;


public class BackgroundChanger extends Thread{
	private static GUI myGUI;
	
	
	public static void main(String[] args) {
		MainWindow window = new MainWindow(new Dimension(600,400));
		
		UserInteraction interaction = new UserInteraction();
		Thread t = new Thread(interaction);
		t.start();

	}

}
