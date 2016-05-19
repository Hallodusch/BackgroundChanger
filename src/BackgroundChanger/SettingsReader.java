package BackgroundChanger;


import org.ini4j.Ini;
import org.ini4j.Profile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Klasse zum lesen des ini-Files.
 */
class SettingsReader {

    /**
     * Liest die Einstellungen aus dem ini-File.
     *
     * @param setting Die gewünschte Einstellung.
     * @return String: Den Inhalt der Einstellungen.
     */
	public String readSettings(Settings setting){

		Ini ini = null;
		try {
			ini = new Ini(SettingsWindow.SETTINGS);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ini != null ? ini.get("settings", setting.toString()) : null;

	}

    /**
     * Gibt den Verlauf der letzt benutzten Webseiten zurück.
     *
     * <p>
     *     Der Key ist immer ein Zahlenstring, der den Speicherzeitpunkt darstellt.
     *     Das Value ist ein String mit Typ der Website und deren subURL.
     * </p>
     *
     *
     * @return @code strings Die Liste des Verlaufs mit Key und Value.
     */
	public List<DataHelper> readHistory(){

		Ini ini;
		List<DataHelper> strings = new ArrayList<DataHelper>();

		try {
			ini = new Ini(SettingsWindow.SETTINGS);
			Set<String> set = ini.keySet();
			Set<String> subSet;
			for (String sectionName: set) {

				//if the section is not history, skip next steps
				if(!"history".equals(sectionName)) continue;

				Profile.Section section = (Profile.Section) ini.get(sectionName);
				subSet = section.keySet();

				//add all the key-value pairs into the List
				strings.addAll(subSet.stream().map(
                        optionKey -> new DataHelper(optionKey.toString(), section.get(optionKey).toString()))
                        .collect(Collectors.toList()));
				//exit loop after history section
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strings;

	}


}
