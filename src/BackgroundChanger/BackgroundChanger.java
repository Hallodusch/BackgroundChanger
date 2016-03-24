package BackgroundChanger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.JSONObject;

import com.google.appengine.api.urlfetch.URLFetchServicePb.URLFetchRequest;


public class BackgroundChanger extends Thread{
	private static MainWindow window;
	private static UserInteraction interaction;


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

		window = new MainWindow(new Dimension(600,400));
		
	}

	public static final File convertToJPG(File f){
		BufferedImage bufferedImage;
		String directory = f.getParent();
		String name = f.getName();
		//if the image is jpg already
		if(name.substring(name.indexOf("."), name.length()).equals(".jpg")){
			return f;
		}else{
			//save the file name
			name = name.substring(0, name.indexOf("."));
			try {
				//read image file
				bufferedImage = ImageIO.read(f);

				// create a blank, RGB, same width and height, and a white background
				BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
						bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

				// write to jpeg file
				File newFile =  new File(directory + "\\" + name + ".jpg");
				ImageIO.write(newBufferedImage, "jpg", newFile);

				System.out.println("Done");
				return newFile;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}






}
