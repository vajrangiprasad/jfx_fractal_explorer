package jfx.fractal.explorer.drawing.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FractalCanvasStroke {
	private double x1,y1,x2,y2,size,w,h;
	private Color penColor,fillColor,backgroundColor;
	private double penSize;
	private FractalCanvasStrokeType type;
	private GraphicsContext gc;
	
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public double getX1() {
		return x1;
	}
	public void setX1(double x1) {
		this.x1 = x1;
	}
	public double getY1() {
		return y1;
	}
	public void setY1(double y1) {
		this.y1 = y1;
	}
	public double getX2() {
		return x2;
	}
	public void setX2(double x2) {
		this.x2 = x2;
	}
	public double getY2() {
		return y2;
	}
	public void setY2(double y2) {
		this.y2 = y2;
	}
	public Color getPenColor() {
		return penColor;
	}
	public void setPenColor(Color penColor) {
		this.penColor = penColor;
	}
	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	public double getPenSize() {
		return penSize;
	}
	public void setPenSize(double penSize) {
		this.penSize = penSize;
	}
	public FractalCanvasStrokeType getType() {
		return type;
	}
	public void setType(FractalCanvasStrokeType type) {
		this.type = type;
	}
	public GraphicsContext getGc() {
		return gc;
	}
	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public double getW() {
		return w;
	}
	public void setW(double w) {
		this.w = w;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	
	
}
