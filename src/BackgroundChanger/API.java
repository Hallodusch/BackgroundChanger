package BackgroundChanger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Definiert, wie die Websiten ihre Daten holen.
 */
interface API {

    /**
     * Gibt die Art von API der Klasse zurück.
     *
     * @return Die Art von API dieser Klasse.
     */
	String getTypeOfAPI();

    /**
     * Nimmt einen Teil des Links und wandelt zu einem vollen Link um.
     *
     * @param subUrl Sublink, der von Benutzer eingegeben wurde.
     * @return String: Voller Link, z.B. zum Tumblr-Blog.
     */
	String makeUrl(String subUrl);

    /**
     * Gibt den Link zu einem individuellen Bild zurück.
     *
     * <p>
     *     Nimmt den vollen String, z.B. zu einem Blog, und fordert den Link zun einem Bild.
     * </p>
     *
     * @param url Link zum Blog, Subreddit oder ähnlichem.
     * @return @code link Der Link zum Bild.
     * @throws Exception
     */
	default String requestLink(String url) throws Exception {
		//Open the connection
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestProperty("User-Agent", "HTTP/1.1");

		System.out.println("\nSending 'GET' request to URL : " + url);
		BufferedReader in = null;
		//Get the JSON Data
		try {
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		StringBuilder response = new StringBuilder();

		//save everything into the String
		response.append(in != null ? in.readLine() : null);
		assert in != null;
		in.close();

		//get the last link in the response(always the link to image)
		int lastIndex = response.lastIndexOf("http:") > response.lastIndexOf("https:") ?
						response.lastIndexOf("http:") : response.lastIndexOf("https:");

		//get the String from start to end of last link
		String link = response.subSequence(lastIndex, response.indexOf("\"", lastIndex)).toString();
		link = link.replace("\\/", "/");
		System.out.println(link);

		//if the link is for an album, add a suffix
		if (!(link.contains(".jpg") | link.contains(".png"))) link += ".jpg";

		return link;
	}

    /**
     * Wandelt den Link als String zu einer URL um.
     *
     * @param url Der Link in String form.
     * @return @code imageUrl Link in URL form.
     */
	default URL giveLinkToImage(String url) {
		URL imageUrl = null;
		try {
			imageUrl = new URL(requestLink(url));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return imageUrl;
	}

    /**
     * Nimmt die URL zu einem Bild und lädt das Bild herunter.
     *
     * @param imageUrl Die URL zum Bild.
     * @return @code image Das Bild der Website.
     */
	default BufferedImage requestData(URL imageUrl) {

		BufferedImage image = null;

		try {
			//construct the image
			image = ImageIO.read(imageUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(image != null ? image.getType() : 0);
		return image;

	}


}
