package BackgroundChanger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RedditAPI implements API {

	//build the reddit URL with the filters
	public String makeUrl(String subUrl) {
		return "http://www.reddit.com/r/".concat(subUrl).concat("/search.json?limit=1&restrict_sr=true&sort=new");
	}

}
