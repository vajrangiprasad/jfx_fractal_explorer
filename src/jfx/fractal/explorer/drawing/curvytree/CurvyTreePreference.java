package jfx.fractal.explorer.drawing.curvytree;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.drawing.FractalDrawingPreference;

public class CurvyTreePreference extends FractalDrawingPreference {
	public static final int MAX_LENGTH = 300;
	private int lengthPercent = 100;
	private int angle1 = 25;
	private int angle2 = 90;
	private Color stemColor = Color.RED;
	private Color leafColor = Color.GREEN;
	private boolean showTurtle = true;
	private double animationDelay = 0.1;
	private CurvyTreeAnimationType animationType = CurvyTreeAnimationType.LENGTH;
	
	private static CurvyTreePreference instance;
	
	private CurvyTreePreference() {
		
	}
	
	public static CurvyTreePreference getInstance() {
		if(instance == null) {
			instance = new CurvyTreePreference();
		}
		
		return instance;
	}
	
	public int getLengthPercent() {
		return lengthPercent;
	}
	
	public void setLengthPercent(int lengthPercent) {
		this.lengthPercent = lengthPercent;
		invalidate("LengthPercent");
	}
	
	public int getAngle1() {
		return angle1;
	}
	
	public void setAngle1(int angle1) {
		this.angle1 = angle1;
		invalidate("Angle1");
	}
	
	public int getAngle2() {
		return angle2;
	}
	
	public void setAngle2(int angle2) {
		this.angle2 = angle2;
		invalidate("Angle2");
	}
	
	public Color getStemColor() {
		return stemColor;
	}
	
	public void setStemColor(Color stemColor) {
		this.stemColor = stemColor;
		invalidate("StemColor");
	}
	
	public Color getLeafColor() {
		return leafColor;
	}
	
	public void setLeafColor(Color leafColor) {
		this.leafColor = leafColor;
	}
	
	public boolean isShowTurtle() {
		return showTurtle;
	}
	
	public void setShowTurtle(boolean showTurtle) {
		this.showTurtle = showTurtle;
		invalidate("ShowTurtle");
	}
	
	public CurvyTreeAnimationType getAnimationType() {
		return animationType;
	}
	public void setAnimationType(CurvyTreeAnimationType animationType) {
		this.animationType = animationType;
	}
	
	public double getLength() {
		return lengthPercent/100.0*MAX_LENGTH;
	}

	public double getAnimationDelay() {
		return animationDelay;
	}

	public void setAnimationDelay(double animationDelay) {
		this.animationDelay = animationDelay;
	}
}
