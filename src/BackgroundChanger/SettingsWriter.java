package BackgroundChanger;


import org.ini4j.Ini;

import java.io.IOException;

/**
 * Klasse zum schreiben von Daten in das ini-File.
 */
class SettingsWriter {

    /**
     * Speichert einen String in eine Einstellung des ini-Files.
     *
     * @param stringToStore Der String, der gespeichert werden soll.
     * @param setting Die Einstellung, die verändert wird.
     */
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


    /**
     * Speichert einen String unter dem jetzigen Zeitpunkt in den Verlauf des ini-Files.
     *
     * @param url Die Url, die zuletzt benutzt wurde.
     */
    public void writeHistory(String url){
		Ini ini = null;
		try {
			ini = new Ini(SettingsWindow.SETTINGS);
			try{
				ini.getAll("history");
			}catch(NullPointerException e){
				ini.add("history");
				ini.store();
			}
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
