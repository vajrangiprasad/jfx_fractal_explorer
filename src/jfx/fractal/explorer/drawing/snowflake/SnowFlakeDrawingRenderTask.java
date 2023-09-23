package jfx.fractal.explorer.drawing.snowflake;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class SnowFlakeDrawingRenderTask extends FractalDrawingRenderTask {
	private Turtle turtle;
	private SnowFlakeDrawingPreference snowFlakeDrawingPreference = SnowFlakeDrawingPreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private int totalLines = 0;
	private int currentLine = 0;
	private int currentPetal  =0;
	
	public SnowFlakeDrawingRenderTask(JFXFractalExplorer fractalExplorer,
			FractalRenderTaskType taskType,
			Turtle turtle) {
		super(fractalExplorer,taskType);
		this.turtle = turtle;
		
	}
	@Override
	public void draw() {
		try {
			snowFlakeDrawingPreference.setJobCanceled(false);
			paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(snowFlakeDrawingPreference.getIteration(), 0);
			rainbowColors = ColorPreference.createRainbowColors(snowFlakeDrawingPreference.getIteration());
			countLines();
			render();
		}catch(Exception ex) {
			fractalExplorer.showExceptionMessage(ex);
		}finally {
			updateMessage(null);
			updateProgress(0,100);
		}
	}

	private void countLines() {
		int petals = (int)(360.0/snowFlakeDrawingPreference.getAngle());
		for(int i = 0; i<petals;i++) {
			renderSnowFlake(0.0,
					0.0, 
					snowFlakeDrawingPreference.getLength(), 
					i*snowFlakeDrawingPreference.getAngle(), 
					snowFlakeDrawingPreference.getPenSize(), 
					snowFlakeDrawingPreference.getIteration(),
					true);
		}
	}
	
	private void render() {
		int petals = (int)(360.0/snowFlakeDrawingPreference.getAngle());
		for(int i = 0; i<petals;i++) {
			currentPetal = i+1;
			renderSnowFlake(0.0,
					0.0, 
					snowFlakeDrawingPreference.getLength(), 
					i*snowFlakeDrawingPreference.getAngle(), 
					snowFlakeDrawingPreference.getPenSize(), 
					snowFlakeDrawingPreference.getIteration(),
					false);
		}
	}
	@Override
	public void animate() {
		fractalExplorer.showErrorMessage("Animation is not supported in Snow Flake Fractal");
	}
	
	private void renderSnowFlake(double x, double y,double l,double d,double ps,int n,boolean countLines) {
		if(n == 0 || snowFlakeDrawingPreference.isJobCanceled()) {
			return;
		}
		
		setPenColor(n-1);
		drawLine(x, y, l, d, ps,countLines);
	
		turtle.penUp();
		turtle.backward(l*2/5);
		x = turtle.getPosition().getX();
		y = turtle.getPosition().getY();
		renderSnowFlake(x,
				y,
				l*3/5,
				d-25,
				ps-1,
				n-1,
				countLines);

		renderSnowFlake(x,
				y,
				l*3/5,
				d+25,
				ps-1,
				n-1,
				countLines);
	}
	
	private void drawLine(double x, double y,double l,double d,double ps,boolean countLines) {
		if(countLines) {
			totalLines++;
			return;
		}
		currentLine++;
		turtle.penUp();
		turtle.setPenSize(ps);
		turtle.moveTo(x, y);
		turtle.penDown();
		turtle.setDirection(d);
		turtle.forward(l);
		int petals = (int)(360.0/snowFlakeDrawingPreference.getAngle());
		updateMessage("Rendering Snow Flake petal " + currentPetal+" of " + petals + "Line : " + currentLine);
		updateProgress(((currentLine)/(double)totalLines)*100.0, 100);
		
		if(snowFlakeDrawingPreference.getDelay() > 0.0) {
			try {
				Thread.sleep((int)(snowFlakeDrawingPreference.getDelay()*1000));
			}catch(Exception ex) {
				
			}
		}
	}
	
	private void setPenColor(int iteration) {
		Color penColor = colorPreference.getPenColor();
		switch(snowFlakeDrawingPreference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(iteration, 
						snowFlakeDrawingPreference.getIteration(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[iteration%snowFlakeDrawingPreference.getIteration()];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[iteration%snowFlakeDrawingPreference.getIteration()];
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
