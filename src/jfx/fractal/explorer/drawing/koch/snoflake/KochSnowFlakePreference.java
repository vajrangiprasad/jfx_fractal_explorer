package jfx.fractal.explorer.drawing.koch.snoflake;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;

public class KochSnowFlakePreference extends FractalDrawingPreference {
	public static int MAX_LEVEL = 8;
	public static double MAX_SIZE = 320;
	private int iterations = 4;
	private int sizePercent = 100;
	private double dealy = 0.001;
	private PenColorType penColorType = PenColorType.TURTLE_PEN_COLOR;
	private KochSnowFlakeType type = KochSnowFlakeType.SQUARE_QUADRATIC_SNOWFLAKE;
	
	private static  KochSnowFlakePreference instance ;
	
	private KochSnowFlakePreference() {
		
	}
	
	public static KochSnowFlakePreference getInstance() {
		if(instance == null) {
			instance = new KochSnowFlakePreference();
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
	
	public int getSizePercent() {
		return sizePercent;
	}
	
	public void setSizePercent(int sizePercent) {
		this.sizePercent = sizePercent;
		invalidate("SizePercent");
	}
	
	public double getSize() {
		return sizePercent/100.0*MAX_SIZE;
	}
	public PenColorType getPenColorType() {
		return penColorType;
	}
	
	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("PenColorType");
	}
	
	public KochSnowFlakeType getType() {
		return type;
	}
	
	public void setType(KochSnowFlakeType type) {
		this.type = type;
		invalidate("Type");
	}

	public double getDealy() {
		return dealy;
	}

	public void setDealy(double dealy) {
		this.dealy = dealy;
	}
	
	
}
