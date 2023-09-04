package jfx.fractal.explorer.resources;

import java.util.ResourceBundle;

public class JFXResourceBundle {
	private static final String BASE_NAME = "jfx/fractal/explorer/resources/jfx_fractal_resources";

	public static String getString(String key) {
		return ResourceBundle.getBundle(BASE_NAME).getString(key);
	}
}
