package BackgroundChanger;

public class FlickrAPI extends API {

	public FlickrAPI(String api_key, String api_secret) {
		super(api_key, api_secret);
		setRequestBehaviour(new FlickrBehaviour());
	}

}
