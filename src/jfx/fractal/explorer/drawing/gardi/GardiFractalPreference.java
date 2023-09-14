package jfx.fractal.explorer.drawing.gardi;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class GardiFractalPreference implements Observable{
	public static double MAX_RADIUS=200.0;
	public static int MAX_ITERATION=10;
	private double radius = MAX_RADIUS;
	private int iterations = 5;
	private GardiFractalOrientation orientation =GardiFractalOrientation.HORIZONTAL;
	
	private static GardiFractalPreference gardiFractalPreference;
	private List<InvalidationListener> invalidationListeners = new ArrayList<InvalidationListener>();
	
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
		invalidate();
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
		invalidate();
	}
	
	
	public GardiFractalOrientation getOrientation() {
		return orientation;
	}

	public void setOrientation(GardiFractalOrientation orientation) {
		this.orientation = orientation;
		invalidate();
	}

	@Override
	public void addListener(InvalidationListener listener) {
		invalidationListeners.add(listener);
	}
	
	@Override
	public void removeListener(InvalidationListener listener) {
		invalidationListeners.remove(listener);
	}
	
	
	private void invalidate() {
		invalidationListeners.forEach(listner -> {
			listner.invalidated(this);
		});
	}
}
