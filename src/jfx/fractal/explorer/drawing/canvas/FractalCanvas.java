package jfx.fractal.explorer.drawing.canvas;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.turtle.TurtleCommand;
import jfx.fractal.explorer.turtle.TurtleRefreshMode;

public class FractalCanvas  {
	public static final Color DEFAULT_PEN_COLOR = Color.RED;
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
	public static final double DEFAULT_PEN_SIZE=1.0;
	protected Canvas canvas;
	protected GraphicsContext gc;
	protected double width,height;
	protected double x1,y1,x2,y2,w,h;
	protected Color penColor = DEFAULT_PEN_COLOR;
	protected Color fillColor = DEFAULT_PEN_COLOR;
	protected Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
	protected double xmin,xmax,ymin,ymax;
	protected double penSize = DEFAULT_PEN_SIZE;
	private FractalCanvasRefreshMode refreshMode = FractalCanvasRefreshMode.ON_CHANGE;
	protected JFXFractalExplorer jfxFractalExplorer;
	private ArrayBlockingQueue<FractalCanvasCommand> fractalCanvasCommandsQueue = new ArrayBlockingQueue<>(FractalConstants.TURTLE_COMMAND_QUEUE_SIZE);
	private ArrayBlockingQueue<FractalCanvasCommand> onDemandFractalCanvasCommandsQueue = new ArrayBlockingQueue<>(FractalConstants.TURTLE_COMMAND_QUEUE_SIZE);
	private FractalCanvasDisplayHandler fractalCanvasDisplayHandler;
	
	public FractalCanvas(JFXFractalExplorer jfxFractalExplorer) {
		this(jfxFractalExplorer,FractalConstants.FRACTAL_DISPLAY_SIZE,FractalConstants.FRACTAL_DISPLAY_SIZE,FractalCanvasRefreshMode.ON_CHANGE);
	}
	
	public FractalCanvas(JFXFractalExplorer jfxFractalExplorer,double width,double height,FractalCanvasRefreshMode refreshMode) {
		this.width = width;
		this.height = height;
		this.jfxFractalExplorer = jfxFractalExplorer;
		this.refreshMode = refreshMode;
		double size = Math.min(width,height);
		setScale(-size/2,size/2);
		canvas = new Canvas(width, height);
		gc = canvas.getGraphicsContext2D();
		StackPane fractalScreen = jfxFractalExplorer.getFractalScreen();
		fractalScreen.getChildren().clear();
		fractalScreen.getChildren().add(canvas);
		
		fractalCanvasDisplayHandler = new FractalCanvasDisplayHandler();
		fractalCanvasDisplayHandler.start();
	}
	
	
	public FractalCanvasRefreshMode getRefreshMode() {
		return refreshMode;
	}

	public void setRefreshMode(FractalCanvasRefreshMode refreshMode) {
		this.refreshMode = refreshMode;
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

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public double getPenSize() {
		return penSize;
	}

	public void setPenSize(double penSize) {
		this.penSize = penSize;
	}

	public void clear() {
		FractalCanvasCommand command = createFractalCanvasCommand(FractalCanvasStrokeType.CLEAR);
		postCommand(command);
	}
	
	public double getXmin() {
		return xmin;
	}

	public double getXmax() {
		return xmax;
	}

	public double getYmin() {
		return ymin;
	}

	public double getYmax() {
		return ymax;
	}
	
	public void setScale(double min, double max) {
		setXScale(min, max);
		setYScale(min, max);
	}
	
	public void setXScale(double min, double max) {
		double size = max - min;
		if(size == 0.0) {
			throw new IllegalArgumentException("X min and X Max cannot be same");
		}
		xmin = min;
		xmax = max;
		
	}
	
	public void setYScale(double min, double max) {
		double size = max - min;
		if(size == 0.0) {
			throw new IllegalArgumentException("Y min and Y Max cannot be same");
		}
		ymin = min;
		ymax = max;
	}
	
	public double scaleX(double x) {
		 return width  * (x - xmin) / (xmax - xmin); 
	}
	
	public double scaleY(double y) {
		return height * (ymax - y) / (ymax - ymin); 
	}
	
	public double factorX(double w) {
		return w * width  / Math.abs(xmax - xmin);  
	}
	
	public double factorY(double h) {
		return h * height / Math.abs(ymax - ymin);  
	}
	
	public double userX(double x) {
		return xmin + x * (xmax - xmin) / width;    
	}
	
	public double userY(double y) {
		return ymax - y * (ymax - ymin) / height;   
	}
	
	
	public GraphicsContext getGc() {
		return gc;
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
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

	public void drawPixel(double x, double y) {
		this.x1 = x;
		this.y1 = y;
		FractalCanvasCommand command = createFractalCanvasCommand(FractalCanvasStrokeType.PIXEL);
		postCommand(command);
	}
	
	public void drawLine(double x1,double y1,double x2,double y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		FractalCanvasCommand command = createFractalCanvasCommand(FractalCanvasStrokeType.LINE);
		postCommand(command);
	}
	
	public void drawSquare(double x,double y,double size) {
		x1 = x;
		y1 = y;
		w = size;
		h = size;
		
		FractalCanvasCommand command = createFractalCanvasCommand(FractalCanvasStrokeType.SQUARE);
		postCommand(command);
	}
	
	public void fillSquare(double x,double y,double size) {
		x1 = x;
		y1 = y;
		w = size;
		h = size;
		
		FractalCanvasCommand command = createFractalCanvasCommand(FractalCanvasStrokeType.FILLED_SQUARE);
		postCommand(command);
	}
	
	public void dispose() {
		clear();
		fractalCanvasDisplayHandler.stop();
		jfxFractalExplorer.getFractalScreen().getChildren().remove(canvas);
	}
	
	private FractalCanvasCommand createFractalCanvasCommand(FractalCanvasStrokeType type) {
		return new FractalCanvasCommand(createStroke(type));
	}
	
	protected FractalCanvasStroke createStroke(FractalCanvasStrokeType type) {
		FractalCanvasStroke stroke = new FractalCanvasStroke();
		stroke.setPenColor(penColor);
		stroke.setFillColor(fillColor);
		stroke.setPenSize(penSize);
		stroke.setBackgroundColor(backgroundColor);
		stroke.setGc(gc);
		stroke.setType(type);
		stroke.setX1(scaleX(x1));
		stroke.setY1(scaleY(y1));
		stroke.setX2(scaleX(x2));
		stroke.setY2(scaleY(y2));
		stroke.setW(factorX(w));
		stroke.setH(factorY(h));
		stroke.setSize(width);
		return stroke;
	}
	
	protected void postCommand(FractalCanvasCommand command) {
		try {
			if(refreshMode == FractalCanvasRefreshMode.ON_CHANGE) {
				fractalCanvasCommandsQueue.put(command);
			}
			
			if(refreshMode == FractalCanvasRefreshMode.ON_DEMAND) {
				if(onDemandFractalCanvasCommandsQueue.size() == (FractalConstants.TURTLE_COMMAND_QUEUE_SIZE)) {
					refreshScreen();
				}
				onDemandFractalCanvasCommandsQueue.put(command);
			}
		} 
		catch (InterruptedException e) {
		}
	}
	
	public void refreshScreen() {
		ArrayList<FractalCanvasCommand> commandList = new ArrayList<>();
		onDemandFractalCanvasCommandsQueue.drainTo(commandList);
		commandList.forEach(command -> {
			try {
				fractalCanvasCommandsQueue.put(command);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	private class FractalCanvasDisplayHandler extends AnimationTimer {
		private long lastUpdate = 0;
		private long sleepTime = 1;
		@Override
		public void handle(long now) {
			if(now-lastUpdate < sleepTime) {
				return;
			}
			lastUpdate = now;
			ArrayList<FractalCanvasCommand> fractalCanvasCommands = new ArrayList<FractalCanvasCommand>();
			fractalCanvasCommandsQueue.drainTo(fractalCanvasCommands);
			if(fractalCanvasCommands.size() > 0) {
				for(FractalCanvasCommand turtleCommand : fractalCanvasCommands) {
					turtleCommand.run();
				}
				fractalCanvasCommands.clear();
			}
		}
	}
}
