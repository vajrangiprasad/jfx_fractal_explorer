package jfx.fractal.explorer.drawing.lsystem;

import java.util.Stack;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class LSystemRenderTask extends FractalDrawingRenderTask {
	private Turtle turtle;
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private LSystemPrefereence lSystemPrefereence = LSystemPrefereence.getInstance();
			
	public LSystemRenderTask(JFXFractalExplorer fractalExplorer, FractalRenderTaskType taskType,Turtle turtle) {
		super(fractalExplorer, taskType);
		this.turtle = turtle;
	}

	@Override
	public void draw() {
		LSystemPrefereence.getInstance().setJobCanceled(false);
		LSystem lSystem = LSystemPrefereence.getInstance().getSelectedLSystem();
		updateMessage("Calculating L-Systen Generation...");
		updateProgress(0, 100);
		
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(100, 0);
		rainbowColors = ColorPreference.createRainbowColors(100);
		
		String generation = lSystem.getGeneration();
		Stack<LSystemStackVO> lSystemStack = new Stack<LSystemStackVO>();
		turtle.penUp();
		turtle.moveTo(lSystem.getStartX(), lSystem.getStartY());
		turtle.setDirection(-90.0);
		turtle.penDown();
		
		double charsProcesed = 0.0;
		double totalStpes = generation.length();
		int step = 0;
		double length = lSystem.getLength();
		for(char c : generation.toCharArray()) {
			if(LSystemPrefereence.getInstance().isJobCanceled()) {
				break;
			}
			charsProcesed++;
			double percent = (charsProcesed/totalStpes)*100.0;
			updateMessage("Rendering L-Systen " + percent);
			updateProgress(percent, 100.0);
			
			setPenColor(step);
			switch(c) {
			case 'A':
			case 'F':
				turtle.forward(length);
				step++;
				break;
			case 'a':
			case 'f':
				turtle.penUp();
				turtle.forward(length);
				turtle.penDown();
				break;
			case '+':
				turtle.right(lSystem.getAngle());
				break;
			case '-':
				turtle.left(lSystem.getAngle());
				break;
			case '|':
				turtle.right(180);
				break;
			case '[':
				LSystemStackVO stackVo1 = new LSystemStackVO();
				stackVo1.setDirection(turtle.getDirection());
				stackVo1.setPosition(new Point2D(turtle.getPosition().getX(), turtle.getPosition().getY()));
				lSystemStack.push(stackVo1);
				break;
			case ']':
				LSystemStackVO stackVo2 = lSystemStack.pop();
				turtle.penUp();
				turtle.moveTo(stackVo2.getPosition().getX(), stackVo2.getPosition().getY());
				turtle.setDirection(stackVo2.getDirection());
				turtle.penDown();
				break;
			case '{':
				turtle.beginFilling();
				break;
			case '}':
				turtle.endFilling();
				break;
			case '<':
				lSystem.setLength(lSystem.getLength()/lSystem.getLengthFactor());
				break;
			case '>':
				lSystem.setLength(lSystem.getLength()*lSystem.getLengthFactor());
				break;
			}
			
			if(lSystemPrefereence.getDelay() > 0.0) {
				try {
					Thread.sleep((long)(lSystemPrefereence.getDelay()*1000));
				} catch (InterruptedException e) {
				}
			}
		}
		
		updateMessage(null);
		updateProgress(0, 100.0);
	}
	
	
	@Override
	public void animate() {
		fractalExplorer.showErrorMessage("Animation is not supported in L-System Fractal");
	}

	private void setPenColor(int step) {
		Color penColor = colorPreference.getPenColor();
		
		switch(lSystemPrefereence.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(step, 
						100, 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[step%100];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[step%100];
			break;
		case RANDOM_COLR:
			penColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_PEN_COLOR:
			penColor = colorPreference.getPenColor();
			break;
		case TWO_COLOR:
			penColor = step%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		turtle.setPenColor(penColor);
	}
}
