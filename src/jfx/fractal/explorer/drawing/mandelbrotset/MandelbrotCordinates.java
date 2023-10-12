package jfx.fractal.explorer.drawing.mandelbrotset;

import javafx.geometry.Point2D;

public class MandelbrotCordinates {
	private Point2D topLeft ;
	private Point2D bottomRight;
	
	public MandelbrotCordinates(Point2D topLeft,Point2D bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public Point2D getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(Point2D topLeft) {
		this.topLeft = topLeft;
	}

	public Point2D getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(Point2D bottomRight) {
		this.bottomRight = bottomRight;
	}
}
