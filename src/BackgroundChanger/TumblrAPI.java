package BackgroundChanger;

public class TumblrAPI extends API {

	public TumblrAPI(String api_key, String api_secret) {
		super(api_key, api_secret);
		setRequestBehaviour(new TumblrBehaviour());
	}

}
