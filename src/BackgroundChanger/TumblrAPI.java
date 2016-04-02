package BackgroundChanger;

import org.json.JSONObject;

import java.io.IOException;


public class TumblrAPI extends API {

	public TumblrAPI(String api_key) {
		setRequestBehaviour(new TumblrBehaviour());
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
