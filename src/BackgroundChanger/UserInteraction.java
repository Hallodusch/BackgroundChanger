package BackgroundChanger;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UserInteraction{
	
	static RequestBehaviour requestData;
	static URL linkToImage;
	static BufferedImage image;
	
	public static void setImageFromReddit(String subreddit, String savePath){
		
		requestData = new RedditBehaviour();
		
		//Get the URL to the actual image
		linkToImage = requestData.giveLinkToImage(makeRedditUrl(subreddit));
		
		//Get the image data from the link
		image = requestData.requestData(linkToImage);
		

		if(image instanceof BufferedImage){
			try{
				//Save the image to the local hard disk
				saveToDisk(image, linkToImage);
				
				//change the background
				Changer.changeBackground(new File(makeLocalPath(linkToImage.toString())));
				
			}catch(IOException e){	
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	static void saveToDisk(BufferedImage img, URL path) throws IOException{
		
		//configure image correctly and prepare for writing
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		
		File outputfile = new File(makeLocalPath(path.toString()));
		try {
//			System.out.println(makeLocalPath(path.toString()));
			
			//save the image into outputfile
			ImageIO.write(bimage, getSuffix(path.toString()), outputfile);
		} catch (IOException e) {
			throw new IOException();
		}
	}

	
	//make the local path to the image
	static String makeLocalPath(String linkToImage){
		return BackgroundChanger.SAVE_LOCATION.concat("/pic.").concat(getSuffix(linkToImage.toString()));
	}
	
	//build the reddit URL with the filters
	static String makeRedditUrl(String subreddit){
		return "http://www.reddit.com/r/".concat(subreddit).concat("/search.json?limit=1&restrict_sr=true&sort=new");
	}
	
	//return the suffix of the image (e.g. 'jpg')
	static String getSuffix(String url){
		return url.substring(url.lastIndexOf(".") + 1, url.length());
	}
	
}
