package jfx.fractal.explorer.drawing.mandelbrotset;

import javafx.geometry.Point2D;
import jfx.fractal.explorer.util.ComplexNumber;

public class MandelbrotCordinates {
	private Point2D topLeft ;
	private Point2D bottomRight;
	private boolean isJuliaSet;
	private ComplexNumber c;
	
	public MandelbrotCordinates(Point2D topLeft,
			Point2D bottomRight,
			boolean isJuliaSet,
			ComplexNumber c) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.c = c;
		this.isJuliaSet = isJuliaSet;
	}

	
	public boolean isJuliaSet() {
		return isJuliaSet;
	}


	public void setJuliaSet(boolean isJuliaSet) {
		this.isJuliaSet = isJuliaSet;
	}


	public ComplexNumber getC() {
		return c;
	}


	public void setC(ComplexNumber c) {
		this.c = c;
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
