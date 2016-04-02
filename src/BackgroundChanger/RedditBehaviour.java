package BackgroundChanger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

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
