package jfx.fractal.explorer.drawing.mandelbrotset;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.PenColorType;

public class MandelbrotPreference extends FractalDrawingPreference {
	private int maxIterations = 100;
	private int numberOfColors = 100;
	private double dragSize = 100.0;
	
	private PenColorType penColorType = PenColorType.RAINBOW_COLOR;
	private MandelbrotMouseActionType mouseActionType = MandelbrotMouseActionType.ZOOM_IN;
	private MandelbrotType type = MandelbrotType.MANDELBROT_SET;
	
	private static MandelbrotPreference instance;
	
	private  MandelbrotPreference() {
		
	}
	
	public static MandelbrotPreference getInstance() {
		if(instance == null) {
			instance = new MandelbrotPreference();
		}
		
		return instance;
	}
	
	
	
	public double getDragSize() {
		return dragSize;
	}

	public void setDragSize(double dragSize) {
		this.dragSize = dragSize;
	}

	public PenColorType getPenColorType() {
		return penColorType;
	}

	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("PenColorType");
	}

	public int getMaxIterations() {
		return maxIterations;
	}
	
	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
		invalidate("MaxIterations");
	}
	
	public int getNumberOfColors() {
		return numberOfColors;
	}
	
	public void setNumberOfColors(int numberOfColors) {
		this.numberOfColors = numberOfColors;
		invalidate("NumberOfColors");
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

	public MandelbrotType getType() {
		return type;
	}

	public void setType(MandelbrotType type) {
		this.type = type;
		invalidate("Type");
	}
}
