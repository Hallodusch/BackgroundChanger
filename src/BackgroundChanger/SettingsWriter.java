package BackgroundChanger;


import org.ini4j.Ini;

import java.io.IOException;

public class SettingsWriter {

	public void writeInterval(String interval){

		try {

			Ini ini = new Ini(SettingsWindow.SETTINGS);
			ini.put("settings", "interval", interval);
			System.out.println(interval);
			ini.store();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeAutostart(boolean autostart){

		try {

			Ini ini = new Ini(SettingsWindow.SETTINGS);
			ini.put("settings", "autostart", Boolean.toString(autostart));
			ini.store();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
