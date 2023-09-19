package jfx.fractal.explorer.drawing.templefractal;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;

public class TempleFractalPreference extends FractalDrawingPreference {
	private static double MAX_RADIUS=110.0;
	public static int MAX_COLORS = 500;
	private int radiusPercent = 100;
	private double direction = 90.0;
	private boolean drawFull = false;
	private int numberOfColors = MAX_COLORS;
	private double delay = 0.0001;
	
	private PenColorType penColorType = PenColorType.PALETTE_COLR;
	private FillColorType fillColorType = FillColorType.NO_FILL;
	
	private static TempleFractalPreference templeFractalPreference;
	
	
	private TempleFractalPreference() {
		
	}
	
	public static TempleFractalPreference getInstance() {
		if(templeFractalPreference == null) {
			templeFractalPreference = new TempleFractalPreference();
		}
		
		return templeFractalPreference;
	}
	
	public int getRadiusPercent() {
		return radiusPercent;
	}
	
	public double getRadius() {
		return radiusPercent/100.0*MAX_RADIUS;
	}
	
	public void setRadiusPercent(int radiusPercent) {
		this.radiusPercent = radiusPercent;
		invalidate("Radius");
	}
	
	public double getDirection() {
		return direction;
	}
	
	public void setDirection(double direction) {
		this.direction = direction;
		invalidate("Diection");
	}
	
	public boolean isDrawFull() {
		return drawFull;
	}
	
	public void setDrawFull(boolean drawFull) {
		this.drawFull = drawFull;
		invalidate("DrawFull");
	}

	public PenColorType getPenColorType() {
		return penColorType;
	}

	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("PenColorType");
	}

	public FillColorType getFillColorType() {
		return fillColorType;
	}

	public void setFillColorType(FillColorType fillColorType) {
		this.fillColorType = fillColorType;
		invalidate("FillColorType");
	}

	public int getNumberOfColors() {
		return numberOfColors;
	}

	public void setNumberOfColors(int numberOfColors) {
		this.numberOfColors = numberOfColors;
		invalidate("NumberOfColors");
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}
}
