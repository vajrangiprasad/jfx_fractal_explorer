package jfx.fractal.explorer.drawing.squaretree;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;

public class SquareTreePreference extends FractalDrawingPreference {
	private int iterations = 10;
	private double animationDelay = 0.1;
	boolean showTurtle = false;
	private SquareTreeAnimationType animationType = SquareTreeAnimationType.ITERATION;
	private PenColorType penColorType = PenColorType.PALETTE_COLR;
	private FillColorType fillColorType = FillColorType.PALETTE_COLR;
	private static SquareTreePreference instance;
	
	private SquareTreePreference() {
		
	}
	
	public static SquareTreePreference getInstance() {
		if(instance == null) {
			instance = new SquareTreePreference();
		}
		
		return instance;
	}
	
	
	public boolean isShowTurtle() {
		return showTurtle;
	}

	public void setShowTurtle(boolean showTurtle) {
		this.showTurtle = showTurtle;
		invalidate("ShowTurtle");
	}

	public int getIterations() {
		return iterations;
	}
	
	public void setIterations(int iterations) {
		this.iterations = iterations;
		invalidate("Iterations");
	}
	
	public PenColorType getPenColorType() {
		return penColorType;
	}

	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("PenColor");
	}

	public SquareTreeAnimationType getAnimationType() {
		return animationType;
	}

	public void setAnimationType(SquareTreeAnimationType animationType) {
		this.animationType = animationType;
	}

	public double getAnimationDelay() {
		return animationDelay;
	}

	public void setAnimationDelay(double animationDelay) {
		this.animationDelay = animationDelay;
	}

	public FillColorType getFillColorType() {
		return fillColorType;
	}

	public void setFillColorType(FillColorType fillColorType) {
		this.fillColorType = fillColorType;
		invalidate("FillColorType");
	}
}
