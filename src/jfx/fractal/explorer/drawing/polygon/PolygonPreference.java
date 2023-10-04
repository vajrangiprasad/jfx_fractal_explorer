package jfx.fractal.explorer.drawing.polygon;

import jfx.fractal.explorer.drawing.AnimationType;
import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;

public class PolygonPreference extends FractalDrawingPreference {
	public static double MAX_SIZE = 300;
	private int sides = 4;
	private int sizePercent = 100;
	private double sizeDelta = 0.1;
	private double rotation = 0.0;
	private double angleDelta = 0.1;
	private double delay = 0.01;
	private boolean connectVertices = false;
	
	private PenColorType penColorType = PenColorType.PALETTE_COLR;
	private FillColorType fillColorType = FillColorType.NO_FILL;
	private AnimationType animationType = AnimationType.SPIRAL;
	
	private static PolygonPreference instance;
	
	private PolygonPreference() {
		
	}
	
	public static PolygonPreference getInstance() {
		if(instance == null) {
			instance = new PolygonPreference();
		}
		
		return instance;
	}
	
	public double getSize() {
		return sizePercent/100.0*MAX_SIZE;
	}
	public int getSides() {
		return sides;
	}
	
	public void setSides(int sides) {
		this.sides = sides;
		invalidate("Sides");
	}
	
	public int getSizePercent() {
		return sizePercent;
	}
	
	public void setSizePercent(int sizePercent) {
		this.sizePercent = sizePercent;
		invalidate("SizePercent");
	}
	
	public double getSizeDelta() {
		return sizeDelta;
	}
	
	public void setSizeDelta(double sizeDelta) {
		this.sizeDelta = sizeDelta;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
		invalidate("Rotation");
	}
	
	public double getAngleDelta() {
		return angleDelta;
	}
	
	public void setAngleDelta(double angleDelta) {
		this.angleDelta = angleDelta;
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
	
	public AnimationType getAnimationType() {
		return animationType;
	}
	
	public void setAnimationType(AnimationType animationType) {
		this.animationType = animationType;
		invalidate("AnimationType");
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	public boolean isConnectVertices() {
		return connectVertices;
	}

	public void setConnectVertices(boolean connectVertices) {
		this.connectVertices = connectVertices;
		invalidate("ConnectVertices");
	}
	
	
}
