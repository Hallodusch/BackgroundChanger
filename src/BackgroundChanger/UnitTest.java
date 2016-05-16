package BackgroundChanger;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

	@Test
	public void test() {
		
		
		API api = new RedditAPI();
		assertEquals("http://www.reddit.com/r/wallpapers/search.json?limit=1&restrict_sr=true&sort=new",
				api.makeUrl("wallpapers"));
		
		assertEquals("http://i.imgur.com/5yeBVeM.jpg", api.giveLinkToImage(
					"https://www.reddit.com/r/wallpapers/search.json?limit=1&restrict_sr=true&sort=top").toString());


		
	}

}
