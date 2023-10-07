package jfx.fractal.explorer.drawing.colorwheel;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.FillColorType;

public class ColorWheelPreference extends FractalDrawingPreference {
	private static final double MAX_RADIUS = 0.98;
	private int numberOfColors = 360;
	private double rotation = 50.0;
	private double timerDelay = 0.01;
	private FillColorType colorType = FillColorType.RAINBOW_COLOR;
	private int radiusPercent = 100;
	
	private static ColorWheelPreference instance;
	
	private ColorWheelPreference() {
		
	}
	
	public static ColorWheelPreference getInstance() {
		if(instance == null) {
			instance = new ColorWheelPreference();
		}
		
		return instance;
	}
	public int getNumberOfColors() {
		return numberOfColors;
	}
	
	public void setNumberOfColors(int numberOfColors) {
		this.numberOfColors = numberOfColors;
		invalidate("NumberOfColors");
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
		invalidate("Rotation");
	}
	
	public double getTimerDelay() {
		return timerDelay;
	}
	
	public void setTimerDelay(double timerDelay) {
		this.timerDelay = timerDelay;
	}
	
	
	public FillColorType getColorType() {
		return colorType;
	}

	public void setColorType(FillColorType colorType) {
		this.colorType = colorType;
		invalidate("FillColorType");
	}

	public int getRadiusPercent() {
		return radiusPercent;
	}

	public void setRadiusPercent(int radiusPercent) {
		this.radiusPercent = radiusPercent;
		invalidate("RadiusPercent");
	}
	
	public double getRadius() {
		return radiusPercent/100.0*MAX_RADIUS;
	}
}
