package jfx.fractal.explorer.drawing.island;


import javafx.scene.paint.Color;
import jfx.fractal.explorer.drawing.FractalDrawingPreference;

public class FractalIslandPreference extends FractalDrawingPreference {
	private double ratio = 0.5;
	private int sizePercent = 100;
	private int edgeWidth = 1;
	private Color edgeColor = Color.RED;
	private Color fillColor = Color.GREEN;
	private Color backgroundColor = Color.BLACK;
	
	private static FractalIslandPreference instance;
	
	private FractalIslandPreference() {
		
	}
	
	public static FractalIslandPreference getInstance() {
		if(instance == null) {
			instance = new FractalIslandPreference();
		}
		
		return instance;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
		invalidate("Ratio");
	}
	public int getSizePercent() {
		return sizePercent;
	}
	public void setSizePercent(int sizePercent) {
		this.sizePercent = sizePercent;
		invalidate("SizePercent");
	}
	public int getEdgeWidth() {
		return edgeWidth;
	}
	public void setEdgeWidth(int edgeWidth) {
		this.edgeWidth = edgeWidth;
		invalidate("EdgeWidth");
	}
	public Color getEdgeColor() {
		return edgeColor;
	}
	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
		invalidate("EdgeColor");
	}
	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		invalidate("FillColor");
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		invalidate("BackgroundColor");
	}
}
