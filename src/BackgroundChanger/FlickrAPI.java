package BackgroundChanger;

import java.io.IOException;

import org.json.JSONObject;

public class FlickrAPI extends API {

	public FlickrAPI(String api_key) {
		super(api_key);
		setRequestBehaviour(new FlickrBehaviour());
	}

	@Override
	public JSONObject getToken() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
