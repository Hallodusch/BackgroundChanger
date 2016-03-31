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
	public BufferedImage requestData(URL imageUrl) {
		BufferedImage image = null;
		
		try {
			//construct the image
			image = ImageIO.read(imageUrl);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(image.getType());
		return image;
	}

	@Override
	public URL giveLinkToImage(String url) {
		RedditAPI redditAPI = new RedditAPI();
		URL imageUrl = null;
		try {
			imageUrl = new URL(redditAPI.requestLink(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return imageUrl;
	}
}
