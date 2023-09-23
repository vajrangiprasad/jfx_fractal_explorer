package jfx.fractal.explorer.drawing.capitalih;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class CapitalIHRenderTak extends FractalDrawingRenderTask {
	private int totalSteps ;
	private int drawCount = 0;
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private CapitalIHPreference capitalIHPreference = CapitalIHPreference.getInstance();
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private Turtle turtle;
	
	public CapitalIHRenderTak(JFXFractalExplorer fractalExplorer,
			FractalRenderTaskType taskType,
			Turtle turtle) {
	    	super(fractalExplorer,taskType);
	    	this.turtle = turtle;
	 }
	@Override
	public void draw() {
		capitalIHPreference.setJobCanceled(false);
		countSteps(capitalIHPreference.getIterations());
		if(capitalIHPreference.getType() == CapitalIHType.IH) {
			totalSteps = 2*totalSteps;
		}
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(totalSteps > 4 ?totalSteps/4:4, 0);
		rainbowColors = ColorPreference.createRainbowColors(totalSteps > 4 ?totalSteps/4:4);
		_draw(capitalIHPreference.getIterations());
	}

	@Override
	public void animate() {
		fractalExplorer.showErrorMessage("Animation is not supported Capital IH Fractal");
	}
	
	private void _draw(int iteration) {
		drawFractal(0,0,capitalIHPreference.getSize(),iteration);
	}
	
	private void drawFractal(double x, double y,double size,int n ) {
		if(n == 0 || capitalIHPreference.isJobCanceled()) {
			return;
		}
		
		switch (capitalIHPreference.getType()) {
		case I:
			drawI(x,y,size,n);
			break;
		case H:
			drawH(x,y,size,n);
			break;
		case IH:
			drawI(x,y,size,n);
			drawH(x,y,size,n);
			break;
		}
		
		updateMessage("Rendering Capital IH step " + drawCount + " of " + totalSteps);
		updateProgress((drawCount)/(double)totalSteps*100.0, 100.0);
		
		drawFractal(x-size,y+size,size/2,n-1);
		drawFractal(x+size,y+size,size/2,n-1);
		drawFractal(x+size,y-size,size/2,n-1);
		drawFractal(x-size,y-size,size/2,n-1);
	}
	
	private void drawI(double x, double y,double size,int n) {
		setPenColor();
		turtle.penUp();
		turtle.moveTo(x-size, y+size);
		turtle.penDown();
		turtle.setDirection(0);
		turtle.forward(2*size);
		turtle.penUp();
		turtle.moveTo(x-size, y-size);
		turtle.penDown();
		turtle.forward(2*size);
		turtle.penUp();
		turtle.moveTo(x, y+size);
		turtle.penDown();
		turtle.right(90);
		turtle.forward(2*size);
		drawCount++;
	}
	
	private void drawH(double x, double y,double size,int n) {
		setPenColor();
		turtle.setDirection(0);
		turtle.penUp();
		turtle.moveTo(x-size, y+size);
		turtle.penDown();
		turtle.right(90);
		turtle.forward(2*size);
		turtle.penUp();
		turtle.moveTo(x+size, y+size);
		turtle.penDown();
		turtle.forward(2*size);
		turtle.penUp();
		turtle.moveTo(x-size, y);
		turtle.penDown();
		turtle.left(90);
		turtle.forward(2*size);
		drawCount++;
	}
	
	private void countSteps( int n) {
		if(n == 0) {
			return;
		}
		totalSteps++;
		countSteps(n-1);
		countSteps(n-1);
		countSteps(n-1);
		countSteps(n-1);
	}

	private void setPenColor() {
		Color penColor = colorPreference.getPenColor();
		
		switch(capitalIHPreference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(drawCount, 
						totalSteps > 4 ?totalSteps/4:4, 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[drawCount%(totalSteps > 4 ?totalSteps/4:4)];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[drawCount%(totalSteps > 4 ?totalSteps/4:4)];
			break;
		case RANDOM_COLR:
			penColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_PEN_COLOR:
			penColor = colorPreference.getPenColor();
			break;
		case TWO_COLOR:
			penColor = drawCount%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		
		turtle.setPenColor(penColor);
	}
}
