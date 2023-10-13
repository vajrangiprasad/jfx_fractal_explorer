package jfx.fractal.explorer.drawing.mandelbrotset;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.util.ComplexNumber;

public class MandelbrotCanvas extends Canvas {
	private static Point2D TOP_LEFT_MANDEL = new Point2D(-2.0, 1.4);
	private static Point2D BOTTOM_RIGHT_MANDEL = new Point2D(0.8, -1.4);
	private static Point2D TOP_LEFT_JULIA = new Point2D(-1.5, 1.5);
	private static Point2D BOTTOM_RIGHT_JULIA = new Point2D(1.5, -1.5);
	
	private MandelbrotPreference preference = MandelbrotPreference.getInstance();
	private int numberOfThreads = Runtime.getRuntime().availableProcessors();
	private Point2D topLeft,bottomRight;
	private boolean drawingCompleted = false;
	private Point2D topLeftMandel,bottomRightMandel;
	private Point2D topLeftJulia,bottomRightJulia;
	private JFXFractalExplorer jfxFractalExplorer;
	private GraphicsContext gc;
	private boolean needsRedraw = false;
	private boolean applyColorPalette = false;
	private boolean isJuliaSet = false;
	private long startTime;
	private ExecutorService executerService ;
	private ArrayList<MandelbrotTaskResponse> taskResponseList = new ArrayList<MandelbrotTaskResponse>();
	private CompletionService<MandelbrotTaskResponse> completionService;
	private ComplexNumber c;
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private int numberOfTasks = 0;
	private boolean rendermiscImage = false;
	private Stack<MandelbrotCordinates> undoBuffer = new Stack<>();
	private Canvas mouseCanvas;
	private MouseHandler mouseHandler;
	private GraphicsContext mouseGC;
	private int pointsOnOrbit = 1000;
	
	public MandelbrotCanvas(JFXFractalExplorer jfxFractalExplorer) {
		super(FractalConstants.FRACTAL_DISPLAY_SIZE,FractalConstants.FRACTAL_DISPLAY_SIZE);
		this.jfxFractalExplorer = jfxFractalExplorer;
		this.topLeft = TOP_LEFT_MANDEL;
		this.bottomRight = BOTTOM_RIGHT_MANDEL;
		this.topLeftMandel = TOP_LEFT_MANDEL;
		this.bottomRightMandel = BOTTOM_RIGHT_MANDEL;
		this.topLeftJulia = TOP_LEFT_JULIA;
		this.bottomRightJulia = BOTTOM_RIGHT_JULIA;
		needsRedraw = true;
		gc = getGraphicsContext2D();
		jfxFractalExplorer.getFractalScreen().getChildren().clear();
		jfxFractalExplorer.getFractalScreen().getChildren().add(this);
		mouseCanvas = new Canvas(FractalConstants.FRACTAL_DISPLAY_SIZE,FractalConstants.FRACTAL_DISPLAY_SIZE);
		mouseCanvas.setStyle("-fx-background-color:transparent;");
		mouseGC = mouseCanvas.getGraphicsContext2D();
		jfxFractalExplorer.getFractalScreen().getChildren().add(mouseCanvas);
		mouseHandler = new MouseHandler();
		addMouseListners();
	}
	
	public void setCoordinates(Point2D topLeft,Point2D bottomRight) {
		this.topLeft = new Point2D(topLeft.getX(), topLeft.getY());
		this.bottomRight = new Point2D(bottomRight.getX(), bottomRight.getY());
		clearDisplay();
		draw();
	}
	
	public void draw() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				__draw();
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	public double getDY() {
		return Math.abs((topLeft.getY()-bottomRight.getY())/(getHeight()-1));
	}
	
	public double getDX() {
		return Math.abs((bottomRight.getX()-topLeft.getX())/(getWidth()-1));
	}
	
	public double getMWidth() {
		return Math.abs(bottomRight.getX()-topLeft.getX())/2;
	}
	
	public double getMHeight() {
		return Math.abs(bottomRight.getY()-topLeft.getY())/2;
	}
	
	public ComplexNumber getC() {
		return c;
	}

	public void setC(ComplexNumber c) {
		this.c = c;
	}

	public boolean isJuliaSet() {
		return isJuliaSet;
	}

	public void setJuliaSet(boolean isJuliaSet) {
		this.isJuliaSet = isJuliaSet;
	}

	private void __draw() {
		taskResponseList.clear();
		drawingCompleted = false;
		clearDisplay();
		
		ExecutorService threadExecutorPool = Executors.newFixedThreadPool(numberOfThreads);
		
		List<Future<MandelbrotTaskResponse>> taskList = new ArrayList<Future<MandelbrotTaskResponse>>();
		int rows = (int)getHeight();
		int columns = (int)getWidth();
		
		double dy = getDY();
		for (int i = 0; i <= rows; i++) {
			double yval = topLeft.getY() - (dy*i);
			MandelbrotTask task = new MandelbrotTask(topLeft.getX(), bottomRight.getX(), yval, columns, i, preference.getMaxIterations());
			task.setJuliaSet(preference.isShowJuliaSet());
			task.setCj(c);
			taskList.add(threadExecutorPool.submit(task));
		}
		
		float tasks = taskList.size();
		float task = 1;
		
		paletteColors = ColorPreference.getInstance().getSelectedColorPalette().makeRGBs(preference.getMaxIterations(), 0);
		rainbowColors = ColorPreference.createRainbowColors(preference.getMaxIterations());
		for(Future<MandelbrotTaskResponse> future : taskList) {
			float percent = task/tasks*100.0f;
			jfxFractalExplorer.updateProgress(percent);
			jfxFractalExplorer.updateStatusMessage("Rendering Mandelbrot Set... " + String.format("%.2f", percent)+" %");
			try {
				MandelbrotTaskResponse resp = future.get();
				taskResponseList.add(resp);
				int y = resp.getRow();
				ArrayList<Integer> iterationCounts = resp.getIterationCounts();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						__drawRow(iterationCounts,  y);
					}
				});

			} catch (Exception e) {
				jfxFractalExplorer.showExceptionMessage(e);
			}
			task++;
		}
	}
	
	private void __drawRow(ArrayList<Integer> iterationCounts,double y) {
		for(int x = 0;x<iterationCounts.size();x++) {
			int iterationCount = iterationCounts.get(x);
			
			gc.setStroke(getColor(iterationCount));
			gc.strokeOval(x, y, 1, 1);
		}
	}
	
	private Color getColor(int iterationCount) {
		if(iterationCount == preference.getMaxIterations()) {
			return Color.BLACK;
		}
		
		Color c = Color.RED;
		
		switch(preference.getPenColorType()) {
		case GRADIENT_COLOR:
			c = ColorPreference.getGradientColor(iterationCount, 
					preference.getNumberOfColors(), 
						ColorPreference.getInstance().getAlternateColor1(), 
						ColorPreference.getInstance().getAlternateColor2());
			break;
		case PALETTE_COLR:
			c = paletteColors[iterationCount%preference.getNumberOfColors()];
			break;
		case RAINBOW_COLOR:
			c = rainbowColors[iterationCount%preference.getNumberOfColors()];
			break;
		case RANDOM_COLR:
			c = ColorPreference.getRandomColor();
			break;
		case TURTLE_PEN_COLOR:
			c = ColorPreference.getInstance().getPenColor();
			break;
		case TWO_COLOR:
			c = iterationCount %2  == 0 ? ColorPreference.getInstance().getAlternateColor1():ColorPreference.getInstance().getAlternateColor2();
		}
		
		return c;
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

	public boolean isDrawingCompleted() {
		return drawingCompleted;
	}

	public void setDrawingCompleted(boolean drawingCompleted) {
		this.drawingCompleted = drawingCompleted;
	}

	public Point2D getTopLeftMandel() {
		return topLeftMandel;
	}

	public void setTopLeftMandel(Point2D topLeftMandel) {
		this.topLeftMandel = topLeftMandel;
	}

	public Point2D getBottomRightMandel() {
		return bottomRightMandel;
	}

	public void setBottomRightMandel(Point2D bottomRightMandel) {
		this.bottomRightMandel = bottomRightMandel;
	}

	public Point2D getTopLeftJulia() {
		return topLeftJulia;
	}

	public void setTopLeftJulia(Point2D topLeftJulia) {
		this.topLeftJulia = topLeftJulia;
	}

	public Point2D getBottomRightJulia() {
		return bottomRightJulia;
	}

	public void setBottomRightJulia(Point2D bottomRightJulia) {
		this.bottomRightJulia = bottomRightJulia;
	}

	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	public void clearDisplay() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				gc.clearRect(0, 0, getWidth(), getHeight());
			}
		});
	}
	
	private void addMouseListners() {
		mouseCanvas.setOnMouseMoved(mouseHandler);
		mouseCanvas.setOnMousePressed(mouseHandler);
		mouseCanvas.setOnMouseDragged(mouseHandler);
		mouseCanvas.setOnMouseReleased(mouseHandler);
	}
	
	private void updatePosition(double x, double y) {
		Point2D p = getPosition(x, y);
		
		jfxFractalExplorer.updateStatusMessage("Mandelbrot Set Position (" + p.getX()+","+p.getY()+")");
	}
	
	private Point2D getPosition(double x, double y) {
		double size = FractalConstants.FRACTAL_DISPLAY_SIZE;
		double dx  = Math.abs((bottomRight.getX()-topLeft.getX())/(size-1));
		double dy = Math.abs((topLeft.getY()-bottomRight.getY())/(size-1));
		double newX = topLeft.getX() + (dx*x);
		double newY = topLeft.getY() - (dy*y);
		
		return new Point2D(newX, newY);
	}
	
	public Point2D getPosition(double x, double y,MandelbrotMouseActionType type) {
		double size = FractalConstants.FRACTAL_DISPLAY_SIZE;
		double dx  = Math.abs((bottomRight.getX()-topLeft.getX())/(size-1));
		double dy = Math.abs((topLeft.getY()-bottomRight.getY())/(size-1));
		double newX = 0.0;
		double newY = 0.0;
		if(type == MandelbrotMouseActionType.ZOOM_IN) {
			 newX = topLeft.getX() + (dx*x);
			 newY = topLeft.getY() - (dy*y);
		}
		
		return new Point2D(newX, newY);
	}
	
	public void showOrbit(double x,double y) {
		double bigd = 100.0;
		double dx  = getDX();
		double dy = getDY();
		Point2D tl = getTopLeft();
		Point2D br  = getBottomRight();
		Point2D position = getPosition(x, y);
		double zx = position.getX();
		double zy = position.getY();
		
		ArrayList<Point2D> orbitPoints = new ArrayList<>();
		orbitPoints.add(new Point2D(x, y));
		for(int i = 0;i<pointsOnOrbit;i++) {
			double zx2 = zx*zx;
			double zy2 = zy*zy;
			if(Math.abs(zx2+zy2) > bigd) {
				break;
			}
			double newzx = zx2-zy2+position.getX();
			double newzy = 2.0*zx*zy+position.getY();
			zx = newzx;
			zy = newzy;
			
			if(zx >= tl.getX() &&
			   zx <= br.getX() &&
			   zy >= br.getY() &&
			   zy <= tl.getY()) {
				double px = (zx-tl.getX())/dx;
				double py = (tl.getY()-zy)/dy;
				orbitPoints.add(new Point2D(px, py));
			}
		}
		
		__drawOrbitPoints(orbitPoints);
	}
	
	private void __drawOrbitPoints(ArrayList<Point2D> orbitPoints) {
		mouseGC.clearRect(0, 0, FractalConstants.FRACTAL_DISPLAY_SIZE,FractalConstants.FRACTAL_DISPLAY_SIZE);
		for(Point2D p : orbitPoints) {
			double x = p.getX();
			double y = p.getY();
			mouseGC.setFill(Color.BLACK);
			mouseGC.fillRect(x-1, y-3, 3, 7);
			mouseGC.fillRect(x-3, y-1, 7, 3);
			mouseGC.setFill(Color.WHITE);
			mouseGC.fillRect(x, y-2, 1, 5);
			mouseGC.fillRect(x-2, y, 5, 1);
		}
	}
	
	public void doZoom(double x, double y,double zoomFactor) {
		System.out.println("doZoom " + x+":"+y+":"+zoomFactor);
	}
	
	public void showOrHideJuliaSet(double x, double y,boolean showJuliaSet) {
		if(showJuliaSet) {
			Point2D p = getPosition(x, y);	
			setC(new ComplexNumber(p.getX(),p.getY()));	
		}
		
		setJuliaSet(showJuliaSet);
		draw();
	}
	
	public void undoZoom() {
		if(undoBuffer.size() == 0) {
			return;
		}
		MandelbrotCordinates mc = undoBuffer.pop();
		setCoordinates(mc.getTopLeft(),mc.getBottomRight());
	}
	
	public class MouseHandler implements EventHandler<MouseEvent> {
		private double tlx,tly,brx,bry;
		private Point2D startPoint;
		private double dragSize = 0;
		private boolean dragged = false;
		private MandelbrotMouseActionType mouseAction;
		
		private void mouseDragged(MouseEvent event) {
			dragged = true;
			double x = event.getX();
			double y = event.getY();
			if(mouseAction == MandelbrotMouseActionType.ZOOM_IN ) {
				double offsetX = Math.abs(x - startPoint.getX());
				double offsetY = Math.abs(y - startPoint.getY());
				dragSize = Math.max(offsetX, offsetY);
				
				if(x < tlx) {
					tlx = x;
				}
				if(y < tly) {
					tly = y;
				}
				
				brx = tlx+dragSize;
				bry = tly+dragSize;
				
				mouseGC.clearRect(0, 0, mouseCanvas.getWidth(), mouseCanvas.getHeight());
				mouseGC.setStroke(Color.BLACK);
				mouseGC.strokeRect(tlx, tly, dragSize, dragSize);
				mouseGC.setStroke(Color.WHITE);
				mouseGC.strokeRect(tlx-1, tly-1, dragSize+2, dragSize+2);
				mouseGC.strokeRect(tlx+1, tly+1, dragSize-2, dragSize-2);
				
				return;
			}
			
			if(mouseAction == MandelbrotMouseActionType.SHOW_ORBIT ||
					mouseAction == MandelbrotMouseActionType.SHOW_JULIA_SET	) {
				showOrbit(x, y);
			}
		}
		
		private void mouseReleased(MouseEvent event) {
			mouseGC.clearRect(0, 0, mouseCanvas.getWidth(), mouseCanvas.getHeight());
			if(event.getButton() == MouseButton.PRIMARY) {
				if(event.getClickCount() == 2) {
					doZoom(event.getX(), event.getY(), 0.5);
				}else {
					if(!dragged) {
						return;
					}
					
					undoBuffer.push(new MandelbrotCordinates(topLeft, bottomRight,isJuliaSet,c));
					if(mouseAction == MandelbrotMouseActionType.ZOOM_IN) {
						setCoordinates(getPosition(tlx, tly,MandelbrotMouseActionType.ZOOM_IN),getPosition(brx, bry,MandelbrotMouseActionType.ZOOM_IN));
					}else if(mouseAction == MandelbrotMouseActionType.SHOW_JULIA_SET) {
						showOrHideJuliaSet(event.getX(), event.getY(),true);
					}
					
					dragged = false;
				}
			}else 	if(event.getButton() == MouseButton.SECONDARY) {
				if(mouseAction == MandelbrotMouseActionType.SHOW_JULIA_SET) {
					showOrHideJuliaSet(event.getX(), event.getY(),false);
				} else {
					undoZoom();
				}
			} 
			
			return;
		}
		
		private void mousePressed(MouseEvent event) {
			double x,y;
			x = event.getX();
			y = event.getY();
			if(x < 0) {
				x = 0;
			}
			if(x > mouseCanvas.getWidth()) {
				x = mouseCanvas.getWidth();
			}
			if(y < 0) {
				y = 0.0;
			}
			if(y > mouseCanvas.getHeight()) {
				y = mouseCanvas.getHeight();
			}
			startPoint = new Point2D(x, y);
			tlx = x;
			tly = y;
			
			if(preference.getMouseActionType() == MandelbrotMouseActionType.SHOW_ORBIT ||
					preference.getMouseActionType() == MandelbrotMouseActionType.SHOW_JULIA_SET	) {
				dragged = true;
				showOrbit(x, y);
			}
			
			return;
		}
		@Override
		public void handle(MouseEvent event) {
			mouseAction = preference.getMouseActionType();
			if(event.getEventType().getName().equals("MOUSE_MOVED")) {
				updatePosition(event.getX(), event.getY());
				return;
			}
			
			if(event.getEventType().getName().equals("MOUSE_PRESSED")) {
				mousePressed(event);
			}
			
			if(event.getEventType().getName().equals("MOUSE_RELEASED")) {
				mouseReleased(event);
			}
			if(event.getEventType().getName().equals("MOUSE_DRAGGED")) {
				mouseDragged(event);
			}
		}
	}

}
