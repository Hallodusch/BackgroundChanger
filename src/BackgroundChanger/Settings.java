package BackgroundChanger;

public enum Settings {
	Autostart ("autostart"),
	Interval ("interval"),
	IntervalToggle ("intervalEnabled"),
	SaveLocation ("saveLocation"),
	LocalSaveToggle ("saveImages");

	private final String name;
	Settings(String s){name = s;}
	public String toString() { return this.name; }

}
