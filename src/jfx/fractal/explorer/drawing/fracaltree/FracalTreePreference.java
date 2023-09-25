package jfx.fractal.explorer.drawing.fracaltree;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.PenColorType;

public class FracalTreePreference extends FractalDrawingPreference {
	private int iterations = 10;
	private double stemWidth = 10.0;
	private double angle = 25.0;
	private double animationDelay = 0.1;
	private FractalTreeAnimationType animationType = FractalTreeAnimationType.ITERATION;
	private PenColorType penColorType = PenColorType.PALETTE_COLR;
	private static FracalTreePreference instance;
	
	private FracalTreePreference() {
		
	}
	
	public static FracalTreePreference getInstance() {
		if(instance == null) {
			instance = new FracalTreePreference();
		}
		
		return instance;
	}
	
	public int getIterations() {
		return iterations;
	}
	
	public void setIterations(int iterations) {
		this.iterations = iterations;
		invalidate("Iterations");
	}
	
	public double getStemWidth() {
		return stemWidth;
	}
	
	public void setStemWidth(double stemWidth) {
		this.stemWidth = stemWidth;
		invalidate("StemWidth");
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
		invalidate("Angle");
	}

	public PenColorType getPenColorType() {
		return penColorType;
	}

	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("PenColor");
	}

	public FractalTreeAnimationType getAnimationType() {
		return animationType;
	}

	public void setAnimationType(FractalTreeAnimationType animationType) {
		this.animationType = animationType;
	}

	public double getAnimationDelay() {
		return animationDelay;
	}

	public void setAnimationDelay(double animationDelay) {
		this.animationDelay = animationDelay;
	}
	
	
}
