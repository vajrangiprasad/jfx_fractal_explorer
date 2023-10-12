package jfx.fractal.explorer.drawing.mandelbrotset;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.PenColorType;

public class MandelbrotPreference extends FractalDrawingPreference {
	private int maxIterations = 100;
	private int numberOfColors = 100;
	private PenColorType penColorType = PenColorType.RAINBOW_COLOR;
	private MandelbrotMouseActionType mouseActionType = MandelbrotMouseActionType.ZOOM_IN;
	
	private static MandelbrotPreference instance;
	
	private  MandelbrotPreference() {
		
	}
	
	public static MandelbrotPreference getInstance() {
		if(instance == null) {
			instance = new MandelbrotPreference();
		}
		
		return instance;
	}
	
	
	public PenColorType getPenColorType() {
		return penColorType;
	}

	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
	}

	public int getMaxIterations() {
		return maxIterations;
	}
	
	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}
	
	public int getNumberOfColors() {
		return numberOfColors;
	}
	
	public void setNumberOfColors(int numberOfColors) {
		this.numberOfColors = numberOfColors;
	}
	
	public MandelbrotMouseActionType getMouseActionType() {
		return mouseActionType;
	}
	
	public boolean isShowJuliaSet() {
		return mouseActionType == MandelbrotMouseActionType.SHOW_JULIA_SET;
	}
	
	public void setMouseActionType(MandelbrotMouseActionType mouseActionType) {
		this.mouseActionType = mouseActionType;
	}
}
