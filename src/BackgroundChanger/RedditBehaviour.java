package BackgroundChanger;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.auth.MalformedChallengeException;
import org.apache.*;

import java.net.URL;

import javax.imageio.ImageIO;



public class RedditBehaviour implements RequestBehaviour {

	@Override
	public BufferedImage requestData(String url) {
		RedditAPI redditAPI = new RedditAPI();
		BufferedImage image = null;
		try {
			//get the URL of the image
			URL imageUrl = new URL(redditAPI.requestData(url));

			//construct the image
			image = ImageIO.read(imageUrl);
		}catch(Exception e){
			e.printStackTrace();
		}
		return image;
	}
}
