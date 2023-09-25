package jfx.fractal.explorer.drawing.fracaltree;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class FractalTreeRenderTask extends FractalDrawingRenderTask {
	private Turtle turtle;
	private JFXFractalExplorer jfxFractalExplorer;
	private FracalTreePreference preference = FracalTreePreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private int iteration = 0;
	
	public FractalTreeRenderTask(JFXFractalExplorer fractalExplorer,
			FractalRenderTaskType taskType,
			Turtle turtle) {
		super(fractalExplorer,taskType);
		this.turtle = turtle;
	}
	
	@Override
	public void draw() {
		try {
			iteration = 0;
			preference.setJobCanceled(false);
			paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(preference.getIterations(), 0);
			rainbowColors = ColorPreference.createRainbowColors(preference.getIterations());
			
			draw_tree(0, -300, 200, preference.getStemWidth(), 0.0f, 90,preference.getAngle(), preference.getIterations());
			turtle.refreshScreen();
			
			updateMessage(null);
			updateProgress(0, 100.0);
		}catch(Exception ex) {
			ex.printStackTrace();
			fractalExplorer.showExceptionMessage(ex);
		}
	}
	
	@Override
	public void animate() {
		preference.setJobCanceled(false);
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(preference.getIterations(), 0);
		rainbowColors = ColorPreference.createRainbowColors(preference.getIterations());
		while(!preference.isJobCanceled()) {
			switch (preference.getAnimationType()) {
				case NONE: {
					break;
				}
				case ANGLE: {
					animate_angle_up();
					animate_angle_down();
					break;
				}
				case ITERATION : {
					animate_iteration_up();
					animate_iteration_down();
					break;
				}
			}
		}
	}
	
	private void animate_angle_up() {
		for(int i=1;i<=90;i++) {
			if(preference.isJobCanceled()) {
				return;
			}
			turtle.clear();
			draw_tree(0, -300, 200, preference.getStemWidth(), 0.0f, 90,i, preference.getIterations());
			turtle.refreshScreen();
			try {
				Thread.sleep((long)(preference.getAnimationDelay()*1000));
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	private void animate_angle_down() {
		for(int i=90;i>=1;i--) {
			if(preference.isJobCanceled()) {
				return;
			}
			turtle.clear();
			draw_tree(0, -300, 200, preference.getStemWidth(), 0.0f, 90,i, preference.getIterations());
			turtle.refreshScreen();
			try {
				Thread.sleep((long)(preference.getAnimationDelay()*1000));
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	private void animate_iteration_up() {
		for(int i=1;i<=preference.getIterations();i++) {
			if(preference.isJobCanceled()) {
				return;
			}
			turtle.clear();
			draw_tree(0, -300, 200, preference.getStemWidth(), 0.0f, 90,preference.getAngle(), i);
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
			draw_tree(0, -300, 200, preference.getStemWidth(), 0.0f, 90,preference.getAngle(), i);
			turtle.refreshScreen();
			try {
				Thread.sleep((long)(preference.getAnimationDelay()*1000));
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	private void draw_tree(double x, double y, double length, double penSize,float hue,double angle,double fatAngle,int n)  {
		if(n == 0 || preference.isJobCanceled()) {
			return;
		}
		iteration++;
		if(taskType == FractalRenderTaskType.DRAW) {
			updateMessage("Rendering Fractal Tree Iteration "  +preference.getIterations() + " of " + preference.getIterations());
			updateProgress((iteration)/(double)preference.getIterations()*100.0, 100.0);
		}else {
			updateMessage("Fractal Tree Animation running ... " );
			updateProgress(0.0, 100.0);
		}
		draw_stem(x, y, length, penSize, angle,n);
		
		double tx = turtle.getPosition().getX();
		double ty = turtle.getPosition().getY();
		
		draw_tree(tx, ty, length*0.66, penSize*0.75, hue+1/(n+1), angle+fatAngle, fatAngle, n-1);
		draw_tree(tx, ty, length*0.66, penSize*0.75, hue+1/(n+1), angle-fatAngle, fatAngle, n-1);
	}
	
	private void draw_stem(double x, double y, double length, double penSize,double angle,int iteration) {
		turtle.penUp();
		turtle.moveTo(x, y);
		turtle.setDirection(-angle);
		turtle.setPenSize(penSize);
		turtle.penDown();
		setPenColor(iteration);
		turtle.forward(length);
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
}
