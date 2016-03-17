package BackgroundChanger;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class BackgroundChanger extends Thread{
	private static MainWindow window;
	private UserInteraction interaction;
	
	
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
		
		API redditAPI = new RedditAPI();
		
		redditAPI.requestData("http://www.reddit.com/r/wallpapers/search.json?sort=best&limit=2");
//		try {
//			System.out.println(redditAPI.getToken().toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		window = new MainWindow(new Dimension(600,400));
		
//		interaction = new UserInteraction();
//		Thread t = new Thread(interaction);
//		t.start();

		
		
	}

}
