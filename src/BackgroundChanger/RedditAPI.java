package BackgroundChanger;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class RedditAPI extends API {	
	
	private final static String OAUTH_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
	private final static String MY_APP_ID = "SduQIWWRJirbUQ";
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	
	public String requestLink(String url) throws Exception{
		//Open the connection
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestProperty("User-Agent", "HTTP/1.1");

		System.out.println("\nSending 'GET' request to URL : " + url);
		BufferedReader in = null;
		//Get the JSON Data
		try{
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		StringBuffer response = new StringBuffer();
		
		//save everything into the String
		response.append(in.readLine());
		in.close();
		
		//get the last link in the response(always the link to image)
		int lastIndex = response.lastIndexOf("http://") > response.lastIndexOf("https://") ?
				response.lastIndexOf("http://") : response.lastIndexOf("https://");
		
		//get the String from start to end of last link
		String link = response.subSequence(lastIndex, response.indexOf("\"", lastIndex)).toString();
		System.out.println(link);
		
		//if the link is for an album, add a suffix
		if(!(link.contains(".jpg") | link.contains(".png"))) link += ".jpg";
		
		return link;
	}


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
