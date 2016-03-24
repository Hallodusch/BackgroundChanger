package BackgroundChanger;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class UserInteraction{
	
	public static void setImageFromReddit(String subreddit, String savePath){
		RedditAPI redditAPI = new RedditAPI();
		
		//format the String
		String RedditUrl = "http://www.reddit.com/r/" + subreddit;
		RedditUrl += "/search.json?limit=1&restrict_sr=true&sort=new";
		try {
			
			//get the URL of the image
			URL url = new URL(redditAPI.requestData(RedditUrl));
			
			//construct the image
			BufferedImage image = ImageIO.read(url);
			String path =  savePath + "/pic." + getSuffix(url.getPath());
			System.out.println(path);
			
			//save the image at the specified path
			saveToDisk(image, path);
			
			//update the wallpaper
			Changer.changeBackground(new File(path));
			
		} catch (IOException e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static void saveToDisk(Image img, String path){
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		File outputfile = new File(path);
		try {
			//save the image into outputfile
			ImageIO.write(bimage, getSuffix(path), outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String getSuffix(String url){
		return url.substring(url.lastIndexOf(".") + 1, url.length());
	}
	
}
