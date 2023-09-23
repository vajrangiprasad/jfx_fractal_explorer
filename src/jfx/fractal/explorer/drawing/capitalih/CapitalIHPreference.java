package jfx.fractal.explorer.drawing.capitalih;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.PenColorType;

public class CapitalIHPreference extends FractalDrawingPreference {
	public static final int MAX_ITERATIONS=15;
	public static final double MAX_SIZE=165.0;
	
	private int iterations = 6;
	private CapitalIHType type = CapitalIHType.I;
	private int sizePercent = 100;
	private boolean showTurtle = true;
	private PenColorType penColorType = PenColorType.PALETTE_COLR;
	
	private static CapitalIHPreference capitalIHPreference;
	
	private CapitalIHPreference() {
		
	}
	
	public static CapitalIHPreference getInstance() {
		if(capitalIHPreference == null) {
			capitalIHPreference = new CapitalIHPreference();
		}
		
		return capitalIHPreference;
	}
	
	public int getIterations() {
		return iterations;
	}
	
	public void setIterations(int iterations) {
		this.iterations = iterations;
		invalidate("Iterations");
	}
	
	public CapitalIHType getType() {
		return type;
	}
	
	public void setType(CapitalIHType type) {
		this.type = type;
		invalidate("Type");
	}
	
	public int getSizePercent() {
		return sizePercent;
	}
	
	public void setSizePercent(int sizePercent) {
		this.sizePercent = sizePercent;
		invalidate("SizePercent");
	}
	
	public boolean isShowTurtle() {
		return showTurtle;
	}
	
	public void setShowTurtle(boolean showTurtle) {
		this.showTurtle = showTurtle;
		invalidate("ShowTurtle");
	}
	
	public PenColorType getPenColorType() {
		return penColorType;
	}
	
	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("PenColorType");
	}
	
	public double getSize() {
		return (double)sizePercent/100.0*MAX_SIZE;
	}
}
