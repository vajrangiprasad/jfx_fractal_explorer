package jfx.fractal.explorer.preference;

import javafx.scene.effect.BlendMode;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class TurtlePreference {
	private double penSize = 2.5;
	private boolean showTurtle;
	private double delay = 0.0;
	private BlendMode blendMode = BlendMode.SRC_OVER;
	private StrokeLineJoin strokeLineJoin = StrokeLineJoin.MITER;
	private StrokeLineCap strokeLineCap = StrokeLineCap.SQUARE;
	private double alpha = 1.0;
	private static TurtlePreference turtlePreference;
	
	
	private TurtlePreference() {
		
	}
	
	public static TurtlePreference getInstance() {
		if(turtlePreference == null) {
			turtlePreference = new TurtlePreference();
		}
		
		return turtlePreference;
	}
	
	public double getPenSize() {
		return penSize;
	}
	public void setPenSize(double penSize) {
		this.penSize = penSize;
	}
	public boolean isShowTurtle() {
		return showTurtle;
	}
	public void setShowTurtle(boolean showTurtle) {
		this.showTurtle = showTurtle;
	}
	public double getDelay() {
		return delay;
	}
	public void setDelay(double delay) {
		this.delay = delay;
	}
	public BlendMode getBlendMode() {
		return blendMode;
	}
	public void setBlendMode(BlendMode blendMode) {
		this.blendMode = blendMode;
	}
	public StrokeLineJoin getStrokeLineJoin() {
		return strokeLineJoin;
	}
	public void setStrokeLineJoin(StrokeLineJoin strokeLineJoin) {
		this.strokeLineJoin = strokeLineJoin;
	}
	public StrokeLineCap getStrokeLineCap() {
		return strokeLineCap;
	}
	public void setStrokeLineCap(StrokeLineCap strokeLineCap) {
		this.strokeLineCap = strokeLineCap;
	}
	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
}
