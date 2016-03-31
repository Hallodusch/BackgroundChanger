package BackgroundChanger;

import java.awt.image.BufferedImage;
import java.net.URL;



public interface RequestBehaviour{
	
	public BufferedImage requestData(URL url);
	
	public URL giveLinkToImage(String link);
}
