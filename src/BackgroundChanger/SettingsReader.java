package BackgroundChanger;


import clover.org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public class SettingsReader {

	public SettingsReader(){


		try {
			Ini ini = new Ini(new File("src/settings.ini"));
			System.out.println(ini.get("settings", "interval"));
		} catch (IOException e) {
			e.printStackTrace();
		}


	}




}
