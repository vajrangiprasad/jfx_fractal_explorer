package jfx.fractal.explorer.drawing.gardi;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class GardiFractalRenderTask  extends FractalDrawingRenderTask{
    private Turtle turtle;
    private GardiFractalPreference gardiFractalPreference = GardiFractalPreference.getInstance();
    private ColorPreference colorPreference = ColorPreference.getInstance();
    private Color[] paletteColors;
    private Color[] rainbowColors;
    private int currentIteration = 0;
	public GardiFractalRenderTask(JFXFractalExplorer fractalExplorer,
			FractalRenderTaskType taskType,
			Turtle turtle) {
		super(fractalExplorer,taskType);
		this.turtle = turtle;
	}
	
	@Override
	public void draw() {
		gardiFractalPreference.setJobCanceled(false);
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(gardiFractalPreference.getIterations(), 0);
		rainbowColors = ColorPreference.createRainbowColors(gardiFractalPreference.getIterations());
		
		int orientation = 0;
		switch(gardiFractalPreference.getOrientation()) {
		case HORIZONTAL:
			orientation = 0;
			break;
		case VERTICAL :
			orientation = 1;
			break;
		}
		
		drawGardi(0,
				0,
				orientation,
				gardiFractalPreference.getIterations(),
				gardiFractalPreference.getRadius());
		
	}
	
	@Override
	public void animate() {
		fractalExplorer.showErrorMessage("Animation is not supported in GArdi Fractal");
	}
	
	private void drawGardi(double x,double y,int orientation,int iteration,double radius) {
		if(iteration == 0 || gardiFractalPreference.isJobCanceled()) {
			return;
		}
		currentIteration++;
		updateMessage("Rendering Gardi Fractal iteration : " + currentIteration);
		updateProgress(((currentIteration)/(double)gardiFractalPreference.getIterations())*100.0, 100);
		setPenColor(iteration);
		drawTwoCircles(x,y,radius,orientation);
		drawGardi(x,
				y, 
				1-orientation,
				iteration-1,
				Math.abs((4-Math.pow(7, 0.5))/3*radius)
				);
	}
	
	private void setPenColor(int iteration) {
		Color penColor = colorPreference.getPenColor();
		switch(gardiFractalPreference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(iteration, 
						gardiFractalPreference.getIterations(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[iteration%gardiFractalPreference.getIterations()];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[iteration%gardiFractalPreference.getIterations()];
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
	
	private void drawTwoCircles(double x, double y,double r,int orientation) {
		turtle.setPenSize(r/50);
		//turtle.setPenColor(colors[circleIndex%iteration]);
		//circleIndex++;
		if(orientation == 0) {
			drawCircle(x-r/2, y, r);
			drawCircle(x+r/2, y, r);
		}else {
			drawCircle(x, y-r/2, r);
			drawCircle(x, y+r/2, r);
		}
	}
	
	private void drawCircle(double x, double y,double r) {
		turtle.penUp();
		turtle.moveTo(x, y);
		turtle.setDirection(0);
		turtle.penDown();
		turtle.circle(r);
	}

}
