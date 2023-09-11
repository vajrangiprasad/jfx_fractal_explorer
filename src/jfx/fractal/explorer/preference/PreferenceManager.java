package jfx.fractal.explorer.preference;

public class PreferenceManager {
	private static PreferenceManager preferenceManager;
	private ColorPreference colorPreference;
	private TurtlePreference turtlePreference;
	
	private PreferenceManager() {
		colorPreference =ColorPreference.getInstance();
		turtlePreference = TurtlePreference.getInstance();
	}
	
	public static PreferenceManager getInstance() {
		if(preferenceManager == null) {
			preferenceManager = new PreferenceManager();
		}
		
		return preferenceManager;
	}
	
	public ColorPreference getColorPreference() {
		return colorPreference;
	}

	public TurtlePreference getTurtlePreference() {
		return turtlePreference;
	}
	
	
	
}
