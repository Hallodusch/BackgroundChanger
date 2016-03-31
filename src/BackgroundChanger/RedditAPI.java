package BackgroundChanger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonToken;
import com.sun.jna.platform.win32.WinUser.HARDWAREINPUT;
import java.util.UUID;



public class RedditAPI extends API {	
	
	private final static String OAUTH_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
	private final static String MY_APP_ID = "SduQIWWRJirbUQ";
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	
	public RedditAPI() {
		setRequestBehaviour(new RedditBehaviour());
	}
	
	public String requestData(String url) throws Exception{
		//Open the conncetion
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestProperty("User-Agent", "HTTP/1.1");

		System.out.println("\nSending 'GET' request to URL : " + url);

		//Get the JSON Data
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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
	
	
	public JSONObject getToken() throws IOException {
		String uuid = UUID.randomUUID().toString();
		System.out.println("Getting token ...");
        GenericUrl url = new GenericUrl(OAUTH_TOKEN_URL);
        Map<String, String> params = new HashMap<String, String>(2);
        params.put("grant_type", "https://oauth.reddit.com/grants/installed_client");
        params.put("device_id", uuid);

        HttpContent hc = new UrlEncodedContent(params);

        HttpRequestFactory requestFactory = HTTP_TRANSPORT
                .createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        request.getHeaders().setBasicAuthentication(MY_APP_ID, "");
                    }
                });

        HttpRequest request = requestFactory.buildPostRequest(url, hc);
        HttpResponse response = request.execute();

        JSONObject jo = null;
        try {
            if (response.isSuccessStatusCode()) {

                String json = response.parseAsString();

                // Parse with org.json
                JSONTokener tokener = null;
                tokener = new JSONTokener( json );
                jo = new JSONObject(tokener);

            } else
                System.out.println("Request failed with " + response.getStatusCode());
        } finally {
            response.disconnect();
        }

        return jo;
    }

}
