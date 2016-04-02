package BackgroundChanger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundChanger extends Thread{
	
	public static final String SAVE_LOCATION = "C:/Users/vmadmin/Desktop";

	private static MainWindow window;
	
	public static void main(String[] args) {

		//Set Window look and feel (=style)
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

	static File convertToJPG(File f){
		BufferedImage bufferedImage;
		String directory = f.getParent();
		String name = f.getName();
		boolean isJpg;
		System.out.println(name);
		try{
			isJpg = ".jpg".equals(name.substring(name.indexOf("."), name.length()));
		}catch(StringIndexOutOfBoundsException e){
			isJpg = false;
			System.out.println("nicht jpg");
		}
		//if the image is jpg already
		if(isJpg){
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
