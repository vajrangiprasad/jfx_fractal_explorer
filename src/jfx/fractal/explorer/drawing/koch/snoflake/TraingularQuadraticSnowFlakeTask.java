package jfx.fractal.explorer.drawing.koch.snoflake;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class TraingularQuadraticSnowFlakeTask extends FractalDrawingRenderTask {
	public static double MAX_LENGTH = 1200;
	public static double MAX_LENGTH_ANTI = 1300;
	private Turtle turtle;
	private int numberOfLines = 0;
	private int currentLine = 0;
	private KochSnowFlakePreference preference = KochSnowFlakePreference.getInstance();
	private Color[] paletteColors;
	private Color[] rainbowColors;
	
	public TraingularQuadraticSnowFlakeTask(JFXFractalExplorer fractalExplorer, FractalRenderTaskType taskType,Turtle turtle) {
		super(fractalExplorer, taskType);
		this.turtle = turtle;
	}

	@Override
	public void draw() {
		preference.setJobCanceled(false);
		switch(preference.getType()) {
		case TRAINGULAR_QUDRATIC_SNOWFLAKE :
			_draw(false,true);
			paletteColors = ColorPreference.getInstance().getSelectedColorPalette().makeRGBs(numberOfLines, 0);
			rainbowColors = ColorPreference.createRainbowColors(numberOfLines);
			_draw(false,false);
			break;
		case TRIALNGULAR_ANTI_QUADRATIC_SNOWFLAKE:
			_draw(true,true);
			paletteColors = ColorPreference.getInstance().getSelectedColorPalette().makeRGBs(numberOfLines, 0);
			rainbowColors = ColorPreference.createRainbowColors(numberOfLines);
			_draw(true,false);
		}
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub

	}

	private void _draw(boolean isAntiSnowFlake,boolean count) {
		double length = MAX_LENGTH*preference.getSizePercent()/100.0;
		if(preference.getType() == KochSnowFlakeType.TRIALNGULAR_ANTI_QUADRATIC_SNOWFLAKE) {
			length = MAX_LENGTH_ANTI*preference.getSizePercent()/100.0;
		}
		turtle.penUp();
		if(isAntiSnowFlake) {
			turtle.right(90);
		}else { 
			turtle.left(90);
		}
		turtle.forward((length/3*Math.sqrt(3)/2));
		if(isAntiSnowFlake) {
			turtle.right(90);
		}else { 
			turtle.left(90);
		}
		turtle.forward((length/3)+(length/6));
		turtle.setDirection(0);
		turtle.penDown();
		
		int level = preference.getIterations();
		
		for(int i = 0;i<3;i++) {
			drawKochSnowflake(length, level,count);
			if(isAntiSnowFlake) {
				turtle.left(120);
			}else {
				turtle.right(120);
			}
		}
		
		turtle.penUp();
		turtle.home();
	}
	
	private void drawKochSnowflake(double length,int level,boolean count) {
		if(preference.isJobCanceled()) {
			return;
		}
		if(level == 0) {
			if(count) {
				numberOfLines++;
				return;
			}
			
			currentLine++;
			updateMessage("Rendering "+  preference.getType() + " line " + currentLine + " of " + numberOfLines);
			updateProgress((double)currentLine/(double)numberOfLines*100.0, 100.0);
			setPenColor(currentLine);
			turtle.forward(length);
			
			if(preference.getDealy() > 0.0) {
				try {
					Thread.sleep((long)(preference.getDealy()*1000));
				} catch (InterruptedException e) {
				}
			}
			return;
		}
		
		length /= 3.0;
		drawKochSnowflake(length,level-1,count);
		turtle.left(90);
		drawKochSnowflake(length,level-1,count);
		turtle.left(-90);
		drawKochSnowflake(length,level-1,count);
		turtle.left(-90);
		drawKochSnowflake(length,level-1,count);
		turtle.left(90);
		drawKochSnowflake(length,level-1,count);
	}
	
	private void setPenColor(int currentLine) {
		ColorPreference colorPreference = ColorPreference.getInstance();
		
		Color penColor = colorPreference.getPenColor();
		
		switch(preference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(currentLine, 
						numberOfLines, 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[currentLine%numberOfLines];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[currentLine%numberOfLines];
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
