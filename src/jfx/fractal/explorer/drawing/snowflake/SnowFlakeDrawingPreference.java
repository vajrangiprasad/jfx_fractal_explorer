package jfx.fractal.explorer.drawing.snowflake;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.PenColorType;

public class SnowFlakeDrawingPreference extends FractalDrawingPreference {
	public static double MAX_LENGTH=200.0;
	public static double MAX_PENSIZE=10.0;
	private int lengthPercent = 100;;
	private double angle = 30.0;
	private double penSize=5.0;
	private int iteration = 8;
	private double delay = 0.0;
	private PenColorType penColorType = PenColorType.RAINBOW_COLOR;
	
	private static SnowFlakeDrawingPreference snowFlakeDrawingPreference;
	
	private SnowFlakeDrawingPreference() {
		
	}
	
	public static SnowFlakeDrawingPreference getInstance() {
		if(snowFlakeDrawingPreference == null) {
			snowFlakeDrawingPreference = new SnowFlakeDrawingPreference();
		}
		
		return snowFlakeDrawingPreference;
	}
	
	public int getLengthPercent() {
		return lengthPercent;
	}

	public void setLengthPercent(int lengthPercent) {
		this.lengthPercent = lengthPercent;
		invalidate("tLengthPercent");
	}

	public double getLength() {
		return MAX_LENGTH*lengthPercent/100.0;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
		invalidate("Angle");
	}
	
	public double getPenSize() {
		return penSize;
	}
	
	public void setPenSize(double penSize) {
		this.penSize = penSize;
		invalidate("Pen Size");
	}
	
	public int getIteration() {
		return iteration;
	}
	
	public void setIteration(int iteration) {
		this.iteration = iteration;
		invalidate("Iteration");
	}
	
	public PenColorType getPenColorType() {
		return penColorType;
	}
	
	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("Pen Color Type");
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}
	
	
}
