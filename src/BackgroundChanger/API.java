package BackgroundChanger;

import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


abstract public class API {
	
	abstract public String requestLink(String url) throws Exception;
	
	abstract public URL giveLinkToImage(String url);

	abstract public BufferedImage requestData(URL imageUrl);

}
