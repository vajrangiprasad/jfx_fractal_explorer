package jfx.fractal.explorer.drawing.mandelbrotset;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
	private boolean preview = false;
	private boolean isJuliaSet = false;
	private ArrayList<MandelbrotTaskResponse> taskResponseList = new ArrayList<MandelbrotTaskResponse>();
	private ComplexNumber c;
	private Color[] paletteColors;
	private Color[] rainbowColors;
	
	public MandelbrotCanvas(JFXFractalExplorer jfxFractalExplorer) {
		super(FractalConstants.FRACTAL_DISPLAY_SIZE,FractalConstants.FRACTAL_DISPLAY_SIZE);
		this.jfxFractalExplorer = jfxFractalExplorer;
		this.topLeft = TOP_LEFT_MANDEL;
		this.bottomRight = BOTTOM_RIGHT_MANDEL;
		
		gc = getGraphicsContext2D();
	}
	
	public void setCoordinates(Point2D topLeft,Point2D bottomRight) {
		this.topLeft = new Point2D(topLeft.getX(), topLeft.getY());
		this.bottomRight = new Point2D(bottomRight.getX(), bottomRight.getY());
		clearDisplay();
		draw();
	}
	
	public void draw() {
		if(preview) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					__draw();
				}
			});
			return;
		}
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

}
