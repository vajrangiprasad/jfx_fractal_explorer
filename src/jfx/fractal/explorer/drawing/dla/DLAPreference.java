package jfx.fractal.explorer.drawing.dla;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.PenColorType;

public class DLAPreference extends FractalDrawingPreference{
	private boolean isSymmetric = false;
	private int numberOfColors = 256;
	private int launchRow = 10;
	private double radius = 10.0;
	private int colorFactor = 256;
	private PenColorType penColorType = PenColorType.PALETTE_COLR;
	private static  DLAPreference instance;
	
	private  DLAPreference() {
		
	}
	
	public static DLAPreference getInstance() {
		if(instance == null) {
			instance = new DLAPreference();
		}
		
		return instance;
	}
	
	public boolean isSymmetric() {
		return isSymmetric;
	}
	
	public void setSymmetric(boolean isSymmetric) {
		this.isSymmetric = isSymmetric;
		invalidate("Symetric");
	}
	
	public int getNumberOfColors() {
		return numberOfColors;
	}
	
	public void setNumberOfColors(int numberOfColors) {
		this.numberOfColors = numberOfColors;
		invalidate("NumberOfColors");
	}
	
	public int getLaunchRow() {
		return launchRow;
	}
	
	
	public void setLaunchRow(int launchRow) {
		this.launchRow = launchRow;
		invalidate("LaunchRow");
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
		invalidate("Radius");
	}
	
	public int getColorFactor() {
		return colorFactor;
	}
	
	public void setColorFactor(int colorFactor) {
		this.colorFactor = colorFactor;
		invalidate("ColorFactor");
	}
	
	public PenColorType getPenColorType() {
		return penColorType;
	}
	
	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("PenColorType");
	}
}
