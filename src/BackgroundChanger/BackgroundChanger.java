package BackgroundChanger;

import java.awt.Dimension;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class BackgroundChanger extends Thread{
	private static GUI myGUI;
	
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MainWindow window = new MainWindow(new Dimension(600,400));
		
		UserInteraction interaction = new UserInteraction();
		Thread t = new Thread(interaction);
		t.start();

	}

}
