package jfx.fractal.explorer.drawing.lsystem;

import javafx.geometry.Point2D;

public class LSystemStackVO {
	private Point2D position;
	private double direction;
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public double getDirection() {
		return direction;
	}
	
	public void setDirection(double direction) {
		this.direction = direction;
	}
}
