package jfx.fractal.explorer.drawing.squaretree;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.turtle.Turtle;

public class SquareTreeRenderTask extends FractalDrawingRenderTask {
	private Turtle turtle;
	private JFXFractalExplorer jfxFractalExplorer;
	private SquareTreePreference preference = SquareTreePreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private int currentSquare  = 0;
	private int totalSquares = 0;
	
	public SquareTreeRenderTask(JFXFractalExplorer fractalExplorer,
			FractalRenderTaskType taskType,
			Turtle turtle) {
		super(fractalExplorer,taskType);
		this.turtle = turtle;
	}
	
	@Override
	public void draw() {
		try {
			preference.setJobCanceled(false);
			paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(preference.getIterations(), 0);
			rainbowColors = ColorPreference.createRainbowColors(preference.getIterations());
			countSquares(preference.getIterations());
			squareTree(-150,-600,300,0,preference.getIterations());
			turtle.refreshScreen();
			updateMessage(null);
			updateProgress(0, 100.0);
		}catch(Exception ex) {
			fractalExplorer.showExceptionMessage(ex);
		}
	}
	
	@Override
	public void animate() {
		preference.setJobCanceled(false);
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(preference.getIterations(), 0);
		rainbowColors = ColorPreference.createRainbowColors(preference.getIterations());
		updateMessage("Square Tree Animation Running");
		updateProgress(0, 100.0);
		while(!preference.isJobCanceled()) {
			
			switch (preference.getAnimationType()) {
				case NONE: {
					break;
				}
				
				case ITERATION : {
					animate_iteration_up();
					animate_iteration_down();
					break;
				}
			}
		}
		
		updateMessage(null);
		updateProgress(0, 100.0);
	}
	
	private void countSquares(int n) {
		if(n == 0) {
			return;
		}
		totalSquares++;
		countSquares(n-1);
		countSquares(n-1);
	}
	private void animate_iteration_up() {
		for(int i=1;i<=preference.getIterations();i++) {
			if(preference.isJobCanceled()) {
				return;
			}
			turtle.clear();
			squareTree(-150,-600,300,0,i);
			turtle.refreshScreen();
			try {
				Thread.sleep((long)(preference.getAnimationDelay()*1000));
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	private void animate_iteration_down() {
		for(int i=preference.getIterations();i>=1;i--) {
			if(preference.isJobCanceled()) {
				return;
			}
			turtle.clear();
			squareTree(-150,-600,300,0,i);
			turtle.refreshScreen();
			try {
				Thread.sleep((long)(preference.getAnimationDelay()*1000));
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	private void squareTree(double x, double y, double length,double tilt,int n) {
		if(n == 0 ) {
			return;
		}
		
		if(taskType == FractalRenderTaskType.DRAW) {
			currentSquare++;
			double progress = (double)currentSquare/(double)totalSquares*100.0;
			updateMessage("Rendering Sqaure Tree square  " + currentSquare +" of " + totalSquares);
			updateProgress(progress, 100.0);
		}
		
		turtle.penUp();
		turtle.moveTo(x, y);
		turtle.penDown();
		turtle.setDirection(tilt);
		
		setPenColor(n);
		if(preference.getFillColorType() != FillColorType.NO_FILL) {
			turtle.beginFilling();
			setFillColor(n);
		}
		turtle.forward(length);
		turtle.left(90);
		turtle.forward(length);
		double x1 = turtle.getPosition().getX();
		double y1 = turtle.getPosition().getY();
		turtle.left(90);
		turtle.forward(length);
		double x2 = turtle.getPosition().getX();
		double y2 = turtle.getPosition().getY();
		turtle.left(90);
		turtle.forward(length);
		turtle.left(90);
		if(preference.getFillColorType() != FillColorType.NO_FILL) {
			turtle.endFilling();
		}
		
		squareTree(x2,y2,length/Math.sqrt(2),tilt-45,n-1);
		turtle.penUp();
	    turtle.moveTo(x1, y1);
	    turtle.setDirection(tilt-135);
	    turtle.forward(length/Math.sqrt(2));
	    squareTree(turtle.getPosition().getX(),turtle.getPosition().getY(),length/Math.sqrt(2),tilt+45,n-1);
	}
	
	private void setPenColor(int iteration) {
		Color penColor = colorPreference.getPenColor();
		
		switch(preference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(iteration, 
						preference.getIterations(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[iteration%preference.getIterations()];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[iteration%preference.getIterations()];
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
	
	private void setFillColor(int iteration) {
		Color fillColor = colorPreference.getFillColor();
		
		switch(preference.getFillColorType()) {
		case GRADIENT_FILL:
			fillColor = ColorPreference.getGradientColor(iteration, 
						preference.getIterations(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			fillColor = rainbowColors[iteration%preference.getIterations()];
			break;
		case PALETTE_COLR:
			fillColor = paletteColors[iteration%preference.getIterations()];
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
}
