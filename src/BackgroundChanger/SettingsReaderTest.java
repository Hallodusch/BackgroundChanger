package BackgroundChanger;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;


public class SettingsReaderTest {

    static SettingsReader reader = new SettingsReader();
    static SettingsWriter writer = new SettingsWriter();
    @org.junit.Before
    public void setUp() throws Exception {

        writer.writeSettings("Setting 1", Settings.Autostart);
        writer.writeSettings("Setting 2", Settings.Interval);
        writer.writeSettings("Setting 3", Settings.IntervalToggle);
        writer.writeSettings("Setting 4", Settings.LocalSaveToggle);
        writer.writeSettings("Setting 5", Settings.SaveLocation);

        writer.writeHistory("Eintrag 1");
        writer.writeHistory("Eintrag 2");
        writer.writeHistory("Eintrag 3");

    }

    @org.junit.Test
    public void readSettings() throws Exception {

        String resultFromReader = reader.readSettings(Settings.Autostart);
        assertEquals("Setting 1", resultFromReader);
        resultFromReader = reader.readSettings(Settings.Interval);
        assertEquals("Setting 2", resultFromReader);
        resultFromReader = reader.readSettings(Settings.IntervalToggle);
        assertEquals("Setting 3", resultFromReader);
        resultFromReader = reader.readSettings(Settings.LocalSaveToggle);
        assertEquals("Setting 4", resultFromReader);
        resultFromReader = reader.readSettings(Settings.SaveLocation);
        assertEquals("Setting 5", resultFromReader);

    }

    @org.junit.Test
    public void readHistory() throws Exception {
        List<DataHelper> resultFromReader = reader.readHistory();
        String value = resultFromReader.get(0).getValue();
        assertEquals("Eintrag 1", value);

        value = resultFromReader.get(1).getValue();
        assertEquals("Eintrag 2", value);

        value = resultFromReader.get(2).getValue();
        assertEquals("Eintrag 3", value);

        long key = Long.parseLong(resultFromReader.get(1).getKey());
        assertNotSame(1000000, key);

    }

}