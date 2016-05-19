package BackgroundChanger;

/**
 * Helferklasse zum Speichern der Einträge im ini-File
 */
class DataHelper {

	private final String key;
	private final String value;

	public DataHelper(String s1, String s2){
		this.key = s1;
		this.value = s2;
	}

	public String getKey(){ return this.key; }
	public String getValue(){ return this.value; }

}
