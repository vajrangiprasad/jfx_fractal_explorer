package jfx.fractal.explorer.drawing.gardi;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.PenColorType;

public class GardiFractalPreference extends FractalDrawingPreference{
	public static double MAX_RADIUS=200.0;
	public static int MAX_ITERATION=10;
	private double radius = MAX_RADIUS;
	private int iterations = 5;
	private PenColorType penColorType = PenColorType.RAINBOW_COLOR;
	private GardiFractalOrientation orientation =GardiFractalOrientation.HORIZONTAL;
	
	private static GardiFractalPreference gardiFractalPreference;
	
	private GardiFractalPreference() {
		
	}
	
	public static GardiFractalPreference getInstance() {
		if(gardiFractalPreference == null) {
			gardiFractalPreference = new GardiFractalPreference();
		}
		
		return gardiFractalPreference;
	}
	
	public int getIterations() {
		return iterations;
	}
	
	public void setIterations(int iterations) {
		this.iterations = iterations;
		invalidate("Iterations");
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
		invalidate("Radius");
	}
	
	
	public GardiFractalOrientation getOrientation() {
		return orientation;
	}

	public void setOrientation(GardiFractalOrientation orientation) {
		this.orientation = orientation;
		invalidate("Orientation");
	}

	
	public PenColorType getPenColorType() {
		return penColorType;
	}

	public void setPenColorType(PenColorType penColorType) {
		this.penColorType = penColorType;
		invalidate("PenColorType");
	}

}
