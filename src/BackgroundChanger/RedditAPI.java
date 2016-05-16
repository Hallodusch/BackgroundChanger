package BackgroundChanger;


public class RedditAPI implements API {

	@Override
	public String getTypeOfAPI() {
		return "Reddit";
	}

	//build the reddit URL with the filters
	public String makeUrl(String subUrl) {
		return "http://www.reddit.com/r/".concat(subUrl).concat("/search.json?limit=1&restrict_sr=true&sort=new");
	}

}
