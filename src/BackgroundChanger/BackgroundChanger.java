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

class BackgroundChanger extends Thread{

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
	private static MyWindow window;

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

	private static void updateImage(String[] lastAction){

		if(lastAction[0].equals("Reddit")) window.setInternetImage(lastAction[1], new RedditAPI());
		else if(lastAction[0].equals("Tumblr")) window.setInternetImage(lastAction[1], new TumblrAPI());

	}

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

	private static boolean isAutostart(){
		SettingsReader reader = new SettingsReader();
		return Boolean.parseBoolean(reader.readSettings(Settings.Autostart));
	}

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

	static boolean isIntervalEnabled(){
		SettingsReader reader = new SettingsReader();
		return Boolean.parseBoolean(reader.readSettings(Settings.IntervalToggle));
	}

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
