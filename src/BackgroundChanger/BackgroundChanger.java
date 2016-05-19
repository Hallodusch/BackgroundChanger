package BackgroundChanger;

import org.ini4j.Ini;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main Klasse der Applikation. 
 * 
 * <p>
 * Erstellt das ini-File.
 * Öffnet das GUI.
 * Startet einen Thread für automatischen Ablauf.
 *
 */
class BackgroundChanger extends Thread{

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
	private static MyWindow window;

	/**
	 * main-Methode. 
	 * 
	 * <p>
	 * Erstellt das ini-File.
	 * Öffnet das GUI.
	 * Startet einen Thread für automatischen Ablauf.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		initializeIniFile();

		if(isAutostart()) enableAutostart();
		else disableAutostart();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}

		window = new MyWindow();

		scheduleImageChange(false);

	}

	/**
	 * Startet einen Thread für einen automatischen Bildwechsel.
	 * 
	 * @param wasInvokedFromSettings True = Methode wurde aus dem Einstellungs-Fenster aufgerufen.
	 */
	static void scheduleImageChange(boolean wasInvokedFromSettings){

		if(isIntervalEnabled()){

			Runnable runnable = () -> updateImage(getLastAction());
			int interval = getInterval();

			if(interval == 0 && ! wasInvokedFromSettings) updateImage(getLastAction());
			else if(interval != 0){
				scheduler.scheduleAtFixedRate(runnable, interval, interval, TimeUnit.MINUTES);
			}

		}

	}

	/**
	 * Wechselt das Hintergrundbild.
	 *
	 * @param lastAction Die letzt benutzte Website und dessen Sublink.
	 */
	private static void updateImage(String[] lastAction){

		if(lastAction[0].equals("Reddit")) window.setInternetImage(lastAction[1], new RedditAPI());
		else if(lastAction[0].equals("Tumblr")) window.setInternetImage(lastAction[1], new TumblrAPI());

	}

	/**
	 * Gibt die zuletzt benutzte Website und deren Sublink zurück.
	 *
	 * @return @code actionToDo
	 */
	private static String[] getLastAction(){

		SettingsReader reader = new SettingsReader();
		String[] actionToDo = new String[2];
		List<DataHelper> list = reader.readHistory();
		Collections.reverse(list);
		if(list.get(0).getValue().contains("Reddit") || list.get(0).getValue().contains("Tumblr")){
			actionToDo = list.get(0).getValue().split("\\s+");
		}
		actionToDo[0].trim();
		actionToDo[1].trim();
		return actionToDo;
	}

	/**
	 * Gibt das momentan ausgewählte Intervall zurück.
	 *
	 * @return @code interval
	 */
	private static int getInterval(){

		SettingsReader reader = new SettingsReader();
		String intervalString = reader.readSettings(Settings.Interval);
		int interval = 0;

		if(intervalString.contains("h")){
			intervalString = intervalString.substring(0, intervalString.indexOf('h'));
			return (Integer.parseInt(intervalString) * 60);
		}else if(intervalString.contains("min")){
			intervalString = intervalString.substring(0, intervalString.indexOf('m'));
			return Integer.parseInt(intervalString);
		}else{
			return interval;
		}

	}

	/**
	 * Deaktiviert die Autostartfunktionalität.
	 *
	 */
	private static void disableAutostart(){
		File batchFile = new File(System.getProperty("user.home") +
										  "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/BackgroundChanger.bat");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(batchFile));
			writer.write("");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				assert writer != null;
				writer.close();
			} catch (Exception ignored) {}
		}
	}

	/**
	 * Schaut, ob die Autostartfunktionalität aktiviert ist.
	 *
	 * @return true, wenn die Autostartfunktionalität aktiviert ist.
	 */
	private static boolean isAutostart(){
		SettingsReader reader = new SettingsReader();
		return Boolean.parseBoolean(reader.readSettings(Settings.Autostart));
	}

	/**
	 * Aktiviert die Autostart funktion.
	 *
	 */
	private static void enableAutostart(){
		File batchFile = new File(System.getProperty("user.home") +
				"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/BackgroundChanger.bat");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(batchFile));
			writer.write("start \"Background Changer\" /D \"" + System.getenv("ProgramFiles(x86)") + "\\BackgroundChanger\"" + " " + "\"BackgroundChanger.exe\"");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				assert writer != null;
				writer.close();
			} catch (Exception ignored) {}
		}

	}

	/**
	 * Schaut, ob die Intervallfunktionalität aktiviert ist.
	 *
	 * @return true, wenn die Intervallfunktionalität aktiviert ist
	 */
	static boolean isIntervalEnabled(){
		SettingsReader reader = new SettingsReader();
		return Boolean.parseBoolean(reader.readSettings(Settings.IntervalToggle));
	}

	/**
	 * Erstellt das ini-File, falls keines existiert.
	 * 
	 * <p>
	 * Default Values für Einstellungen:
	 * <ul>
	 * <li> Autostart: true
	 * <li> Intervall: true
	 * <li> Intervall: 10 Minuten
	 * <li> Speicherplatz: gleiches Verzeichnis
	 * <li> Lokales Speichern: Aktiviert
	 * 
	 */
	private static void initializeIniFile(){

		Ini ini;
		File iniFile = new File(SettingsWindow.SETTINGS.toString());
		if(!iniFile.exists()){
			System.out.println("writing stuff");
			try{
				iniFile.createNewFile();
				ini = new Ini(SettingsWindow.SETTINGS);
				ini.add("settings");
				ini.store();
				ini.put("settings", Settings.Autostart.toString(), "true");
				ini.put("settings", Settings.Interval.toString(), "10min");
				ini.put("settings", Settings.IntervalToggle.toString(), "false");
				ini.put("settings", Settings.LocalSaveToggle.toString(), "true");
				ini.put("settings", Settings.SaveLocation.toString(), ".");
				ini.store();
				ini.clear();
			}catch(IOException e){
				e.printStackTrace();
			}
		}

	}

}
