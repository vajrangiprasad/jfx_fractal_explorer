package jfx.fractal.explorer.drawing.koch.snoflake;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class OctaFlakeTask extends FractalDrawingRenderTask {
	public static double MAX_LENGTH = 900;
	private Turtle turtle;
	private int numberOfOctalgones = 0;
	private int currentOctagone = 0;
	private KochSnowFlakePreference preference = KochSnowFlakePreference.getInstance();
	private Color[] paletteColors;
	private Color[] rainbowColors;
	
	public OctaFlakeTask(JFXFractalExplorer fractalExplorer, FractalRenderTaskType taskType,Turtle turtle) {
		super(fractalExplorer, taskType);
		this.turtle = turtle;
	}
	
	@Override
	public void draw() {
		preference.setJobCanceled(false);
		drawOctaflake(0, 0, MAX_LENGTH*preference.getSizePercent()/100.0, preference.getIterations(),true);
		paletteColors = ColorPreference.getInstance().getSelectedColorPalette().makeRGBs(numberOfOctalgones, 0);
		rainbowColors = ColorPreference.createRainbowColors(numberOfOctalgones);
		currentOctagone = 0;
		drawOctaflake(0, 0, MAX_LENGTH*preference.getSizePercent()/100.0, preference.getIterations(),false);
		turtle.penUp();
		turtle.home();

	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub

	}
	
	private void drawOctaflake(double x,double y, double r, int n,boolean count) {
		if(r < 10||n == 0) {
			updateMessage("Rendering "+  preference.getType() + " Hexagone " + currentOctagone + " of " + numberOfOctalgones);
			updateProgress((double)currentOctagone/(double)numberOfOctalgones*100.0, 100.0);
			drawOctagone(x, y, r,count);
			return;
		}
		
		double r2 = r/(1+1/Math.tan(Math.toRadians(45/2)));
		drawOctaflake(x, y, r-2*r2, n-1,count);
		double direction = 90;
		for(int i = 0;i < 8;i++) {
			turtle.penUp();
			turtle.moveTo(x, y);
			turtle.setDirection(direction);
			turtle.forward(r-r2);
			drawOctaflake(turtle.getPosition().getX(), turtle.getPosition().getY(), r2, n-1,count);
			direction += 45;
		}
	}
	
	private void drawOctagone(double x,double y, double r,boolean count) {
		if(preference.isJobCanceled()) {
			return;
		}
		if(count) {
			numberOfOctalgones++;
			return;
		}
		
		currentOctagone++;
		setPenColor();
		turtle.penUp();
		turtle.moveTo(x, y);
		turtle.setDirection(90);
		turtle.forward(r);
		turtle.left(180-135/2);
		turtle.penDown();
		
		for(int i = 0; i< 8;i++) {
			turtle.forward(r);
			turtle.left(45);
		}
		
		if(preference.getDealy() > 0.0) {
			try {
				Thread.sleep((long)(preference.getDealy()*1000));
			} catch (InterruptedException e) {
			}
		}
	}
	
	private void setPenColor() {
		ColorPreference colorPreference = ColorPreference.getInstance();
		
		Color penColor = colorPreference.getPenColor();
		
		switch(preference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(currentOctagone, 
						numberOfOctalgones, 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[currentOctagone%numberOfOctalgones];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[currentOctagone%numberOfOctalgones];
			break;
		case RANDOM_COLR:
			penColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_PEN_COLOR:
			penColor = colorPreference.getPenColor();
			break;
		case TWO_COLOR:
			penColor = currentOctagone%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		turtle.setPenColor(penColor);
	}

}
