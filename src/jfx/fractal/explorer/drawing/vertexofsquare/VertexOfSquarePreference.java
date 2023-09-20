package jfx.fractal.explorer.drawing.vertexofsquare;

import jfx.fractal.explorer.drawing.FractalDrawingPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.preference.PenColorType;

public class VertexOfSquarePreference extends FractalDrawingPreference{
	public static double MAX_SIZE = 220.0;
	public static int MAX_COLORS = 500;
	private int sizePercent = 100;
	private double sizeDelta = 0.1;
	private double angleDelta = 3.0;
	private int numberOfColors=100;
	
	private PenColorType penColorType = PenColorType.TURTLE_PEN_COLOR;
	private FillColorType fillColorType = FillColorType.RANDOM_COLR;
	
	private static VertexOfSquarePreference vertexOfSquarePreference;
	
	private VertexOfSquarePreference() {
		
	}
	
	public static VertexOfSquarePreference getInstance() {
		if(vertexOfSquarePreference == null) {
			vertexOfSquarePreference = new VertexOfSquarePreference();
		}
		
		return vertexOfSquarePreference;
	}
	
	public int getSizePercent() {
		return sizePercent;
	}
	public void setSizePercent(int sizePercent) {
		this.sizePercent = sizePercent;
		invalidate("SizPercent");
	}
	
	public double getSizeDelta() {
		return sizeDelta;
	}
	
	public void setSizeDelta(double sizeDelta) {
		this.sizeDelta = sizeDelta;
		invalidate("SizDelata");
	}
	
	public double getAngleDelta() {
		return angleDelta;
	}
	
	public void setAngleDelta(double angleDelta) {
		this.angleDelta = angleDelta;
		invalidate("AngleDelta");
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
	
	public double getSize() {
		return sizePercent/100.0*MAX_SIZE;
	}

	public int getNumberOfColors() {
		return numberOfColors;
	}

	public void setNumberOfColors(int numberOfColors) {
		this.numberOfColors = numberOfColors;
		invalidate("NumberOfColors");
	}
}
