package BackgroundChanger;

import java.io.IOException;
import org.json.JSONObject;

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
	public String requestData(String url) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
