package jfx.fractal.explorer.drawing.templefractal;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.turtle.Turtle;

public class TempleFractalRenderTask extends FractalDrawingRenderTask {
	private Turtle turtle;
	private TempleFractalPreference templeFractalPreference = TempleFractalPreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private int totalCircles = 0;
	private int circleIndex = 0;
	
	public TempleFractalRenderTask(JFXFractalExplorer fractalExplorer,
			FractalRenderTaskType taskType,
			Turtle turtle) {
		super(fractalExplorer,taskType);
		this.turtle = turtle;
		
	}
	
	@Override
	public void draw() {
		templeFractalPreference.setJobCanceled(false);
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(templeFractalPreference.getNumberOfColors(), 0);
		rainbowColors = ColorPreference.createRainbowColors(templeFractalPreference.getNumberOfColors());
		circleIndex = 0;
		countCircles();
		renderTemple(0, 0, templeFractalPreference.getRadius(), templeFractalPreference.getDirection(),false);
		if(templeFractalPreference.isDrawFull()) {
			renderTemple(0, 0, templeFractalPreference.getRadius(), -templeFractalPreference.getDirection(),false);
		}
		
		turtle.home();
	}

	private void countCircles() {
		renderTemple(0, 0, templeFractalPreference.getRadius(), templeFractalPreference.getDirection(),true);
		if(templeFractalPreference.isDrawFull()) {
			totalCircles *=2;
		}
	}
	@Override
	public void animate() {
		fractalExplorer.showErrorMessage("Animation is not supported in Temple Fractal");
	}
	
	private void renderTemple(double x, double y,double r,double d,boolean countCircles) {
		if(r < 2 || templeFractalPreference.isJobCanceled()) {
			return;
		}
		
		drawCircle(x, y, r,countCircles);
		
		renderTemple(x+r*1.5*Math.cos(Math.toRadians(d)),
				y+r*1.5*Math.sin(Math.toRadians(d)),
				r*0.5,
				d,
				countCircles);
		renderTemple(x+r*1.5*Math.cos(Math.toRadians(d-90)),
				y+r*1.5*Math.sin(Math.toRadians(d-90)),
				r*0.5,
				d-90,
				countCircles);
		renderTemple(x+r*1.5*Math.cos(Math.toRadians(d+90)),
				y+r*1.5*Math.sin(Math.toRadians(d+90)),
				r*0.5,
				d+90,
				countCircles);
	}
	
	private void drawCircle(double x, double y,double r,boolean countCircles) {
		if(countCircles) {
			totalCircles++;
			return;
		}
		updateMessage("Rendering Circle " + (circleIndex+1) + " of " +totalCircles);
		updateProgress((circleIndex+1)/(double)totalCircles*100.0, 100.0);
		turtle.penUp();
		turtle.moveTo(x, y);
		turtle.setDirection(0);
		turtle.penDown();
		setPenColor(circleIndex);
		if(templeFractalPreference.getFillColorType() != FillColorType.NO_FILL) {
			setFillColor(circleIndex);
			turtle.filledCircle(r-1.0);
		}
		
		turtle.circle(r);
		
		circleIndex++;
		
		if(templeFractalPreference.getDelay() > 0.0) {
			try {
				Thread.sleep((int)(templeFractalPreference.getDelay()*1000));
			}catch(Exception ex) {
				
			}
		}
	}
	
	private void setFillColor(int iteration) {
		Color fillColor = colorPreference.getFillColor();
		
		switch(templeFractalPreference.getFillColorType()) {
		case GRADIENT_FILL:
			fillColor = ColorPreference.getGradientColor(iteration, 
						templeFractalPreference.getNumberOfColors(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			fillColor = rainbowColors[iteration%templeFractalPreference.getNumberOfColors()];
			break;
		case PALETTE_COLR:
			fillColor = paletteColors[iteration%templeFractalPreference.getNumberOfColors()];
			break;
		case RANDOM_COLR:
			fillColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_FILL_COLOR:
			fillColor = colorPreference.getPenColor();
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
		
		switch(templeFractalPreference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(iteration, 
						templeFractalPreference.getNumberOfColors(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[iteration%templeFractalPreference.getNumberOfColors()];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[iteration%templeFractalPreference.getNumberOfColors()];
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

}
