package BackgroundChanger;

import static org.junit.Assert.*;

import org.junit.Test;

public class UnitTest {

	@Test
	public void test() {
		
		
		API api = new RedditAPI();
		assertEquals("http://www.reddit.com/r/wallpapers/search.json?limit=1&restrict_sr=true&sort=new",
				api.makeUrl("wallpapers"));
		
		assertEquals("http://i.imgur.com/5yeBVeM.jpg", api.giveLinkToImage(
					"https://www.reddit.com/r/wallpapers/search.json?limit=1&restrict_sr=true&sort=top").toString());
		
		
		SettingsReader reader = new SettingsReader();
		assertEquals(reader.readAutostart(), false);
		assertNotEquals(reader.readAutostart(), true);
		
		assertEquals(reader.readInterval(), "30min");
		assertNotEquals(reader.readInterval(), "123");
		
	}

}
