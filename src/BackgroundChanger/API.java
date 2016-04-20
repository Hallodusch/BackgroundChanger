package BackgroundChanger;

import java.awt.image.BufferedImage;
import java.net.URL;


public interface API {

	String requestLink(String url) throws Exception;

	URL giveLinkToImage(String url);

	BufferedImage requestData(URL imageUrl);

	String makeUrl(String subUrl);

}
