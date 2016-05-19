package BackgroundChanger;

/**
 * Definiert, wie Reddit Daten geholt werden.
 */
public class RedditAPI implements API {

    /**
     * Gibt den Typ der API zurück.
     *
     * @return String "Reddit"
     */
	@Override
	public String getTypeOfAPI() {
		return "Reddit";
	}

    /**
     * Baut den Link zum Subreddit mit der subUrl.
     *
     * @param subUrl Sublink, der von Benutzer eingegeben wurde.
     * @return String: Den vollen Link zu einem Subreddit mit Filteroptionen.
     */
    @Override
	public String makeUrl(String subUrl) {
		return "http://www.reddit.com/r/".concat(subUrl).concat("/search.json?limit=3&restrict_sr=true&sort=new");
	}

}
