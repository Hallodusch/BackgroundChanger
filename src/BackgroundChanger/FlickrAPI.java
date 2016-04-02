package BackgroundChanger;

import org.json.JSONObject;

import java.io.IOException;

public class FlickrAPI extends API {

	public FlickrAPI(String api_key) {
		setRequestBehaviour(new FlickrBehaviour());
	}

	@Override
	public JSONObject getToken() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String requestLink(String url) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
