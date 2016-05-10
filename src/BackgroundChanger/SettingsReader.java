package BackgroundChanger;


import org.ini4j.Ini;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SettingsReader {



	public String readInterval(){

		try{

			Ini ini = new Ini(SettingsWindow.SETTINGS);
			String interval = ini.get("settings", "interval");
//			System.out.println(interval);

			for(String s : SettingsWindow.INTERVALS)
				if (s.equals(interval)) return interval;

		}catch(IOException e){
			e.printStackTrace();
		}

		return "60min";

	}

	public Boolean readAutostart(){

		try{

			Ini ini = new Ini(SettingsWindow.SETTINGS);
			String as = ini.get("settings", "autostart").toLowerCase();
			if ("false".equals(as))
				return false;
			else if ("true".equals(as))
				return true;

		}catch(IOException e){
			e.printStackTrace();
		}

		return false;

	}




}
