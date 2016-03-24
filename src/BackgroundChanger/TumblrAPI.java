package BackgroundChanger;

import java.io.IOException;

import org.json.JSONObject;

public class TumblrAPI extends API {

	public TumblrAPI(String api_key) {
		super(api_key);
		setRequestBehaviour(new TumblrBehaviour());
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
