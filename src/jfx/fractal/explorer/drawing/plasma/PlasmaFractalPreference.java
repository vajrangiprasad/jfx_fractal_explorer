package jfx.fractal.explorer.drawing.plasma;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;

public class PlasmaFractalPreference extends FractalDrawingPreference {
	public static double MAX_SIZE=450.0;
	private double sizePercent = 100.0;
	private double minSize = 0.75;
	private double saturation = 1.0;
	private double brightness = 1.0;
	private double standardDeviation = 1.0;
	private boolean grayScale = false;
	private static PlasmaFractalPreference instance ;
	
	private PlasmaFractalPreference() {
		
	}
	
	public static PlasmaFractalPreference getInstance() {
		if(instance == null) {
			instance = new PlasmaFractalPreference();
		}
		
		return instance;
	}
	
	public double getSize() {
		return sizePercent/100.0*MAX_SIZE;
	}
	
	public double getSizePercent() {
		return sizePercent;
	}

	public void setSizePercent(double sizePercent) {
		this.sizePercent = sizePercent;
		invalidate("SizePercent");
	}

	public double getMinSize() {
		return minSize;
	}
	
	public void setMinSize(double minSize) {
		this.minSize = minSize;
		invalidate("MinSize");
	}
	
	public double getSaturation() {
		return saturation;
	}
	
	public void setSaturation(double saturation) {
		this.saturation = saturation;
		invalidate("Saturation");
	}
	
	public double getBrightness() {
		return brightness;
	}
	
	public void setBrightness(double brightness) {
		this.brightness = brightness;
		invalidate("Brightness");
	}
	
	public double getStandardDeviation() {
		return standardDeviation;
	}
	
	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
		invalidate("StandardDeviation");
	}

	public boolean isGrayScale() {
		return grayScale;
	}

	public void setGrayScale(boolean grayScale) {
		this.grayScale = grayScale;
		invalidate("GrayScale");
	}
	
	
}
