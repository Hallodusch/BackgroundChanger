package BackgroundChanger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;


public class TumblrAPI implements API {

	@Override
	public String makeUrl(String subUrl) {
		return "https://api.tumblr.com/v2/blog/" + subUrl + ".tumblr.com/posts/photo?api_key=fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4&limit=1";
	}


}
