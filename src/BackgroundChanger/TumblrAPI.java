package BackgroundChanger;

/**
 * Definiert, wie Tumblr Daten geholt werden.
 */
public class TumblrAPI implements API {

    /**
     * Gibt den Typ der API zurück.
     *
     * @return String "Tumblr"
     */
	@Override
	public String getTypeOfAPI() {
		return "Tumblr";
	}

    /**
     * Baut den Link zum Blog mit der subUrl.
     *
     * @param subUrl Sublink, der von Benutzer eingegeben wurde.
     * @return String: Den vollen Link zu einem Tumblr mit Filteroptionen.
     */
	@Override
	public String makeUrl(String subUrl) {
		return "https://api.tumblr.com/v2/blog/" + subUrl + ".tumblr.com/posts/photo?api_key=fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4&limit=3";
	}


}
