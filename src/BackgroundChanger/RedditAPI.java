package BackgroundChanger;

public class RedditAPI extends API {

	public RedditAPI(String api_key, String api_secret) {
		super(api_key, api_secret);
		setRequestBehaviour(new RedditBehaviour());
	}

}
