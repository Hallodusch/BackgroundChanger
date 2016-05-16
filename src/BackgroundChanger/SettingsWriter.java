package BackgroundChanger;


import org.ini4j.Ini;

import java.io.IOException;

class SettingsWriter {



	public void writeSettings(String stringToStore, Settings setting){
		Ini ini = null;
		try{
			ini = new Ini(SettingsWindow.SETTINGS);
			ini.put("settings", setting.toString(), stringToStore);
			ini.store();
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			assert ini != null;
			ini.clear();
		}

	}

	public void writeHistory(String url){
		Ini ini = null;
		try {
			ini = new Ini(SettingsWindow.SETTINGS);
			ini.put("history", Long.toString(System.currentTimeMillis()) , url);
			ini.store();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			assert ini != null;
			ini.clear();
		}
	}


}
