package jfx.fractal.explorer.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class FractalUtility {
	public static String getWebColor(Color color) {
		String strColor = color.toString();
		
		if(strColor.contains("0x")) {
			strColor = strColor.replace("0x", "#");
		}
		return strColor;
	}
	
	public static ImageView getImageView(String url) {
		Image image = new Image(FractalUtility.class.getResourceAsStream(url));
		
		return new ImageView(image);
	}
}
