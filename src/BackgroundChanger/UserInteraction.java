package BackgroundChanger;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class UserInteraction{
	
	static RequestBehaviour requestData;
	
	public static void setImageFromReddit(String subreddit, String savePath){
		requestData = new RedditBehaviour();
		Image image = requestData.requestData(subreddit);
		
		if(image != null){
			try{
				saveToDisk(image, savePath);
				Changer.changeBackground(new File(savePath));
			}catch(IOException e){	
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			
		}
		
	}

	static String makeRedditUrl(String subreddit){
		return "http://www.reddit.com/r/".concat(subreddit).concat("/search.json?limit=1&restrict_sr=true&sort=new");
	}
	
	static void saveToDisk(Image img, String path) throws IOException{
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		File outputfile = new File(path);
		try {
			//save the image into outputfile
			ImageIO.write(bimage, getSuffix(path), outputfile);
		} catch (IOException e) {
			throw new IOException();
		}
	}

	static String getSuffix(String url){
		return url.substring(url.lastIndexOf(".") + 1, url.length());
	}
	
}
