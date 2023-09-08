package jfx.fractal.explorer.turtle;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TurtleTrail {
	private String name;
	private Point2D center;
	private Point2D oldPosition;
	private Point2D position;
	private double direction = 0.0;
	private Color penColor;
	private Color fillColor;
	private boolean turtleVisible;
	private boolean penDown;
	private boolean startFilling = false;
	private boolean endFilling = false;
	private double radius;
	private double penSize;
	private double width;
	private double height;
	private TurtleStrokeType strokeType;
	private TurtleShape shape;
	private List<Double> xList = new ArrayList<Double>();
	private List<Double> yList = new ArrayList<Double>();
	private GraphicsContext drawingGC;
	private GraphicsContext turtleGC;
	
	public TurtleTrail(GraphicsContext drawingGC,GraphicsContext turtleGC) {
		this.drawingGC = drawingGC;
		this.turtleGC = turtleGC;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Point2D getCenter() {
		return center;
	}
	public void setCenter(Point2D center) {
		this.center = center;
	}
	public Point2D getOldPosition() {
		return oldPosition;
	}
	public void setOldPosition(Point2D oldPosition) {
		this.oldPosition = oldPosition;
	}
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
	public boolean isTurtleVisible() {
		return turtleVisible;
	}
	public void setTurtleVisible(boolean turtleVisible) {
		this.turtleVisible = turtleVisible;
	}
	public boolean isPenDown() {
		return penDown;
	}
	public void setPenDown(boolean penDown) {
		this.penDown = penDown;
	}
	public boolean isStartFilling() {
		return startFilling;
	}
	public void setStartFilling(boolean startFilling) {
		this.startFilling = startFilling;
	}
	public boolean isEndFilling() {
		return endFilling;
	}
	public void setEndFilling(boolean endFilling) {
		this.endFilling = endFilling;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public TurtleShape getShape() {
		return shape;
	}
	public void setShape(TurtleShape shape) {
		this.shape = shape;
	}

	public double getPenSize() {
		return penSize;
	}

	public void setPenSize(double penSize) {
		this.penSize = penSize;
	}

	public TurtleStrokeType getStrokeType() {
		return strokeType;
	}

	public void setStrokeType(TurtleStrokeType strokeType) {
		this.strokeType = strokeType;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public List<Double> getxList() {
		return xList;
	}

	public List<Double> getyList() {
		return yList;
	}
	
	
	public GraphicsContext getDrawingGC() {
		return drawingGC;
	}

	public void setDrawingGC(GraphicsContext drawingGC) {
		this.drawingGC = drawingGC;
	}

	public GraphicsContext getTurtleGC() {
		return turtleGC;
	}

	public void setTurtleGC(GraphicsContext turtleGC) {
		this.turtleGC = turtleGC;
	}

	@Override
	public String toString() {
		return position.toString();
	}
}
