package jfx.fractal.explorer.drawing.polygon;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.AnimationType;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.turtle.Turtle;
import jfx.fractal.explorer.util.FractalUtility;

public class PolygonRenderTask extends FractalDrawingRenderTask {
	private Turtle turtle;
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private int iteration = 0;
	private int iterations = 0;
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private PolygonPreference polygonPreference = PolygonPreference.getInstance();
	
	public PolygonRenderTask(JFXFractalExplorer fractalExplorer, FractalRenderTaskType taskType,Turtle turtle) {
		super(fractalExplorer, taskType);
		this.turtle = turtle;
	}

	@Override
	public void draw() {
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(1,0);
		rainbowColors = ColorPreference.createRainbowColors(1);
		iterations = 1;
		iteration = 1;
		_draw(polygonPreference.getSize(),polygonPreference.getRotation());
		updateProgress(0, 100);
	}

	@Override
	public void animate() {
		calculateIterations();
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(iterations,0);
		rainbowColors = ColorPreference.createRainbowColors(iterations);
		iteration = 0;
		double p = 0.0;
		double factor = 0.0;
		double rotation = polygonPreference.getRotation();
		
		if(polygonPreference.getAnimationType() == AnimationType.SIZE || 
				polygonPreference.getAnimationType() == AnimationType.SPIRAL) {
			while(true && !polygonPreference.isJobCanceled()) {
				updateMessage("Animation Running iteration " + iteration + " of " +iterations);
				updateProgress((double)iteration/(double)iterations*100.0, 100);
				factor = (100.0 - p)/100.0;
				if(factor < 0.0) {
					break;
				}
				double size = polygonPreference.getSize()*factor;
				_draw(size,rotation);
				if(polygonPreference.getAnimationType() == AnimationType.SPIRAL) {
					rotation +=polygonPreference.getAngleDelta();
				}
				p+=polygonPreference.getSizeDelta();
				iteration++;
				
				if(polygonPreference.getDelay() > 0.0) {
					try {
						Thread.sleep((long)(polygonPreference.getDelay()*1000));
					} catch (InterruptedException e) {
					}
				}
			}
		}
		
		if (polygonPreference.getAnimationType() == AnimationType.ROTATION) {

			rotation = 0.0;
			while (true && !polygonPreference.isJobCanceled() ) {
				updateMessage("Animation Running iteration " + iteration + " of " +iterations);
				updateProgress((double)iteration/(double)iterations*100.0, 100);
				if (rotation > 360.0) {
					break;
				}

				_draw(polygonPreference.getSize(), rotation);

				rotation += polygonPreference.getAngleDelta();

				iteration++;

				if (polygonPreference.getDelay() > 0.0) {
					try {
						Thread.sleep((long) (polygonPreference.getDelay() * 1000));
					} catch (InterruptedException e) {
					}
				}
			}
		}
		
		updateMessage(null);
		updateProgress(0, 100);
	}
	
	private void _draw(double size,double rotation) {
		try {
			double d = 360.0/(polygonPreference.getSides()*2);
		
			setPenColor(iteration);
			turtle.penUp();
			Point2D origin = new Point2D(0, 0);
			double deltaAngle = 360.0/polygonPreference.getSides();
			if(polygonPreference.getFillColorType() != FillColorType.NO_FILL) {
				setFillColor(iteration);
				turtle.beginFilling();
			}
			Point2D[] points = new Point2D[polygonPreference.getSides()+1];
			for (int i = 0; i <= polygonPreference.getSides(); i++) {
				double x, y;
				
				x = size * Math.cos(Math.toRadians(d));
				y = size * Math.sin(Math.toRadians(d));
				Point2D point = FractalUtility.rotate(new Point2D(x,y), origin, rotation);
				turtle.moveTo(point.getX(),point.getY());
				turtle.penDown();
				points[i] = new Point2D(point.getX(),point.getY());
				d += deltaAngle;
			}
			
			if(polygonPreference.isConnectVertices() && taskType != FractalRenderTaskType.ANIMATE) {
				calculateIterations();
				paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(iterations,0);
				rainbowColors = ColorPreference.createRainbowColors(iterations);
				iteration = 0;
				for(int i = 0; i <= polygonPreference.getSides(); i ++) {
					for(int j = 0; j <= polygonPreference.getSides(); j ++) {
						turtle.penDown();
						turtle.moveTo(points[j].getX(), points[j].getY());
						turtle.penUp();
						setPenColor(iteration);
						turtle.moveTo(points[i].getX(), points[i].getY());
						iteration++;
					}
				}
			}
			if(polygonPreference.getFillColorType() != FillColorType.NO_FILL) {
				turtle.endFilling();
			}
		}catch(Exception ex) {
			fractalExplorer.showExceptionMessage(ex);
		}
	}
	private void setFillColor(int iteration) {
		Color fillColor = colorPreference.getFillColor();
		
		switch(polygonPreference.getFillColorType()) {
		case GRADIENT_FILL:
			fillColor = ColorPreference.getGradientColor(iteration, 
					    iterations, 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			fillColor = rainbowColors[iteration%iterations];
			break;
		case PALETTE_COLR:
			fillColor = paletteColors[iteration%iterations];
			break;
		case RANDOM_COLR:
			fillColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_FILL_COLOR:
			fillColor = colorPreference.getFillColor();
			break;
		case TWO_COLOR:
			fillColor = iteration%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		turtle.setFillColor(fillColor);
	}
	
	private void setPenColor(int iteration) {
		Color penColor = colorPreference.getPenColor();
		
		switch(polygonPreference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(iteration, 
						iterations, 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[iteration%iterations];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[iteration%iterations];
			break;
		case RANDOM_COLR:
			penColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_PEN_COLOR:
			penColor = colorPreference.getPenColor();
			break;
		case TWO_COLOR:
			penColor = iteration%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		turtle.setPenColor(penColor);
	}
	private void calculateIterations() {
		if(polygonPreference.isConnectVertices()) {
			for(int i = 0; i < polygonPreference.getSides();i++) {
				for(int j = 0; j<polygonPreference.getSides();j++) {
					iterations++;
				}
			}
		}else if(polygonPreference.getAnimationType() == AnimationType.SPIRAL || polygonPreference.getAnimationType() == AnimationType.SIZE) {
			double p = 0.0;
			while(true) {
				if(p > 100.0) {
					break;
				}
				p += polygonPreference.getSizeDelta();
				iterations ++;
			}
		}
		if(polygonPreference.getAnimationType() == AnimationType.ROTATION) {
			double tempRotation = 0.0;
			while(true) {
				if(tempRotation > 360.0) {
					return;
				}
				iterations++;
				tempRotation += polygonPreference.getAngleDelta();
			}
		}
	}
}
