package jfx.fractal.explorer.turtle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.preference.PreferenceManager;

/**
 * A Turtle is a drawing area in which a virtual "turtle" moves around, possibly leaving a trail as it moves.
 * The Turtle has a pen that can be raised and lowered. When pen is down, it leaves a trail. There are commands
 * available for moving the turtle ,set direction of movement, set pen color, set fill color, line width , 
 * set turtle shape, control the visibility of turtle shape.Initially when the turtle is created, turtle is positioned 
 * at center (0,0) and facing right.
 *
 */
public class Turtle {
	private JFXFractalExplorer fractalExplorer ;
	private ColorPreference colorPreference = PreferenceManager.getInstance().getColorPreference();
	private String name;
	private TurtleShape shape = TurtleShape.getShape("Turtle");
	private Point2D center;
	private Point2D oldPosition;
	private Point2D position;
	private Canvas drawingCanvas;
	private Canvas turtleCanvas;
	private GraphicsContext drawingGC ;
	private GraphicsContext turtleGC ;
	private Color penColor;
	private Color fillColor;
	private double direction = 0.0;
	private boolean turtleVisible = true;
	private boolean penDown = true;
	private boolean startFilling = false;
	private boolean endFilling = false;
	private double penSize = 5.0;
	private double radius = 0.0;
	private List<Double> xList = new ArrayList<Double>();
	private List<Double> yList = new ArrayList<Double>();
	
	private TurtleRenderAnimationHandler turtleRenderAnimationHandler;
	private ColorPreferenceChangeHandler colorPreferenceChangeHandler ;
	private ArrayBlockingQueue<TurtleCommand> turtleCommandsQueue = new ArrayBlockingQueue<>(FractalConstants.TURTLE_COMMAND_QUEUE_SIZE);
	
	public Turtle(JFXFractalExplorer fractalExplorer) {
		this(fractalExplorer,"Unknown");
	}
	
	public Turtle(JFXFractalExplorer fractalExplorer,String name) {
		this.fractalExplorer = fractalExplorer;
		center = new Point2D(0.0, 0.0);
		oldPosition = new Point2D(0.0, 0.0);
		position = new Point2D(0, 0);
		initialize();
	}
	
	public void dispose() {
		turtleRenderAnimationHandler.stop();
		colorPreference.removeListener(colorPreferenceChangeHandler);
		fractalExplorer.getFractalScreen().getChildren().remove(drawingCanvas);
		fractalExplorer.getFractalScreen().getChildren().remove(turtleCanvas);
	}
	
	
	public Color getPenColor() {
		return penColor;
	}

	public void setPenColor(Color penColor) {
		this.penColor = penColor;
		postCommand(creteTurtleCommand(TurtleStrokeType.NONE));
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		postCommand(creteTurtleCommand(TurtleStrokeType.NONE));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TurtleShape getShape() {
		return shape;
	}

	public void setShape(TurtleShape shape) {
		this.shape = shape;
	}

	public boolean isTurtleVisible() {
		return turtleVisible;
	}

	public boolean isPenDown() {
		return penDown;
	}


	public double getPenSize() {
		return penSize;
	}

	public void setPenSize(double penSize) {
		this.penSize = penSize;
	}

	public Point2D getPosition() {
		return position;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	private void postCommand(TurtleCommand command) {
		try {
			turtleCommandsQueue.put(command);
		} 
		catch (InterruptedException e) {
		}
	}
	
	
	public void beginFilling() {
		this.startFilling = true;
		this.endFilling = false;
		xList.clear();
		yList.clear();
		postCommand(creteTurtleCommand(TurtleStrokeType.NONE));
	}
	
	public void endFilling() {
		this.endFilling = true;
		postCommand(creteTurtleCommand(TurtleStrokeType.LINE));
		this.startFilling = false;
		this.endFilling = false;
		xList.clear();
		yList.clear();
	}
	
	
	public void penUp() {
		this.penDown = false;
	}
	
	public void penDown() {
		this.penDown = true;
	}
	
	public void home() {
		penUp();
		direction = 0.0;
		moveTo(0.0, 0.0);
		penDown();
	}
	
	public void moveTo(double x, double y) {
		oldPosition = new Point2D(position.getX(), position.getY());
		position = new Point2D(x, y);
		postCommand(creteTurtleCommand(TurtleStrokeType.LINE));
	}
	
	public void circle(double radius) {
		this.radius = radius;
		postCommand(creteTurtleCommand(TurtleStrokeType.CIRCLE));
	}
	
	public void filledCircle(double radius) {
		this.radius = radius;
		postCommand(creteTurtleCommand(TurtleStrokeType.FILLED_CIRCLE));
	}
	
	public void forward(double distance) {
		double x = position.getX();
		double y = position.getY();
		oldPosition = new Point2D(x, y);
		double angle = Math.toRadians(direction);
		x += distance*Math.cos(angle);
		y -= distance*Math.sin(angle);
		position = new Point2D(x, y);
		postCommand(creteTurtleCommand(TurtleStrokeType.LINE));
	}
	
	public void backward(double distance) {
		forward(-distance);
	}
	
	public void right(double angle) {
		this.direction += angle;
		postCommand(creteTurtleCommand(TurtleStrokeType.NONE));
	}
	
	public void left(double angle) {
		this.direction -= angle;
		postCommand(creteTurtleCommand(TurtleStrokeType.NONE));
	}
	
	public void clear() {
		postCommand(creteTurtleCommand(TurtleStrokeType.CLEAR));
		home();
	}
	private void initialize() {
		StackPane fractalScreen = fractalExplorer.getFractalScreen();
		
		penColor = colorPreference.getPenColor();
		fillColor = colorPreference.getFillColor();
		drawingCanvas = new Canvas(FractalConstants.FRACTAL_DISPLAY_SIZE, FractalConstants.FRACTAL_DISPLAY_SIZE);
		turtleCanvas = new Canvas(FractalConstants.FRACTAL_DISPLAY_SIZE,FractalConstants.FRACTAL_DISPLAY_SIZE);
		drawingCanvas.setStyle("-fx-background-color:transparent;");
		turtleCanvas.setStyle("-fx-background-color:transparent;");
		
		drawingGC = drawingCanvas.getGraphicsContext2D();
		turtleGC = turtleCanvas.getGraphicsContext2D();
		
		fractalScreen.getChildren().add(drawingCanvas);
		fractalScreen.getChildren().add(turtleCanvas);
		
		turtleRenderAnimationHandler = new TurtleRenderAnimationHandler();
		turtleRenderAnimationHandler.start();
		
		colorPreferenceChangeHandler = new ColorPreferenceChangeHandler();
		colorPreference.addListener(colorPreferenceChangeHandler);

		home();
	}
	
	private TurtleCommand creteTurtleCommand(TurtleStrokeType strokeType) {
		TurtleTrail trail = new TurtleTrail(drawingGC,turtleGC);
		
		trail.setCenter(new Point2D(center.getX(),center.getY()));
		trail.setDirection(direction);
		trail.setEndFilling(endFilling);
		trail.setName(name);
		trail.setOldPosition(new Point2D(oldPosition.getX(), oldPosition.getY()));
		trail.setPenColor(penColor);
		trail.setFillColor(fillColor);
		trail.setPenDown(penDown);
		trail.setPosition(new Point2D(position.getX(), position.getY()));
		trail.setRadius(radius);
		trail.setShape(shape);
		trail.setStartFilling(startFilling);
		trail.setTurtleVisible(turtleVisible);
		trail.setPenSize(penSize);
		trail.setStrokeType(strokeType);
		trail.setWidth(FractalConstants.FRACTAL_DISPLAY_SIZE);
		trail.setHeight(FractalConstants.FRACTAL_DISPLAY_SIZE);
		if(!endFilling && startFilling) {
			double x = (int)((position.getX() - center.getX())  + FractalConstants.FRACTAL_DISPLAY_SIZE / 2);
		    double y = (int)((position.getY() - center.getY())*(-1) + FractalConstants.FRACTAL_DISPLAY_SIZE / 2);
		    xList.add(x);
		    yList.add(y);
		}
		
		if(endFilling) {
			trail.getxList().clear();
			trail.getxList().addAll(xList);
			trail.getyList().clear();
			trail.getyList().addAll(yList);
		}
		
		return new TurtleCommand(trail);
	}
	
	private class TurtleRenderAnimationHandler extends AnimationTimer {
		@Override
		public void handle(long now) {
			ArrayList<TurtleCommand> turtleCommands = new ArrayList<TurtleCommand>();
			turtleCommandsQueue.drainTo(turtleCommands);
			if(turtleCommands.size() > 0) {
				for(TurtleCommand turtleCommand : turtleCommands) {
					turtleCommand.run();
				}
				turtleCommands.clear();
			}
		}
		
	}
	
	private class ColorPreferenceChangeHandler implements InvalidationListener {
		@Override
		public void invalidated(Observable observable) {
			setPenColor(colorPreference.getPenColor());
			setFillColor(colorPreference.getFillColor());
		}
	}
}
