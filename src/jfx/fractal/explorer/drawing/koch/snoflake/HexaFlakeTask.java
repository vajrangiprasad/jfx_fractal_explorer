package jfx.fractal.explorer.drawing.koch.snoflake;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class HexaFlakeTask extends FractalDrawingRenderTask {
	public static double MAX_LENGTH = 900;
	private Turtle turtle;
	private int numberOfHecagones = 0;
	private int currentHexagone = 0;
	private KochSnowFlakePreference preference = KochSnowFlakePreference.getInstance();
	private Color[] paletteColors;
	private Color[] rainbowColors;
	
	public HexaFlakeTask(JFXFractalExplorer fractalExplorer, FractalRenderTaskType taskType,Turtle turtle) {
		super(fractalExplorer, taskType);
		this.turtle = turtle;
	}

	@Override
	public void draw() {
		preference.setJobCanceled(false);
		hexaflake(0, 0, MAX_LENGTH*preference.getSizePercent()/100.0, preference.getIterations(),true);
		paletteColors = ColorPreference.getInstance().getSelectedColorPalette().makeRGBs(numberOfHecagones, 0);
		rainbowColors = ColorPreference.createRainbowColors(numberOfHecagones);
		currentHexagone = 0;
		hexaflake(0, 0, MAX_LENGTH*preference.getSizePercent()/100.0, preference.getIterations(),false);
		turtle.penUp();
		turtle.home();
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub

	}

	private void hexaflake(double x, double y, double r, int n,boolean count) {
		if(preference.isJobCanceled()) {
			return;
		}
		if(n == 0) {
			currentHexagone++;
			updateMessage("Rendering "+  preference.getType() + " Hexagone " + currentHexagone + " of " + numberOfHecagones);
			updateProgress((double)currentHexagone/(double)numberOfHecagones*100.0, 100.0);
			drawHexagon(x, y, r,count);
			return;
		}
		
		hexaflake(x, y, r/3, n-1,count);
		
		double direction = 90;
	
		for(int i = 0; i< 6; i++) {
			turtle.penUp();
			turtle.moveTo(x, y);
			turtle.setDirection(direction);
			turtle.forward(r*2/3);
			hexaflake(turtle.getPosition().getX(), turtle.getPosition().getY(), r/3, n-1,count);
			direction += 60;
		}
	}
	
	private void drawHexagon(double x, double y, double r,boolean count) {
		if(count) {
			numberOfHecagones++;
			return;
		}
		setPenColor(currentHexagone);
		turtle.penUp();
		turtle.moveTo(x, y);
		turtle.setDirection(90);
		turtle.forward(r);
		turtle.left(120);
		turtle.penDown();
		
		for(int i = 0;i<6;i++) {
			turtle.forward(r);
			turtle.left(60);
		}
		if(preference.getDealy() > 0.0) {
			try {
				Thread.sleep((long)(preference.getDealy()*1000));
			} catch (InterruptedException e) {
			}
		}
	}
	
	private void setPenColor(int currentLine) {
		ColorPreference colorPreference = ColorPreference.getInstance();
		
		Color penColor = colorPreference.getPenColor();
		
		switch(preference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(currentLine, 
						numberOfHecagones, 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[currentLine%numberOfHecagones];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[currentLine%numberOfHecagones];
			break;
		case RANDOM_COLR:
			penColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_PEN_COLOR:
			penColor = colorPreference.getPenColor();
			break;
		case TWO_COLOR:
			penColor = currentLine%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		turtle.setPenColor(penColor);
	}
	
}
