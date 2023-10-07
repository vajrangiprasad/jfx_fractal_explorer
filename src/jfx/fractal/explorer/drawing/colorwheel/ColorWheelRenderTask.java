package jfx.fractal.explorer.drawing.colorwheel;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.drawing.canvas.FractalCanvas;
import jfx.fractal.explorer.preference.ColorPreference;

public class ColorWheelRenderTask extends FractalDrawingRenderTask {
	private FractalCanvas fractalCanvas;
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private double turn = 0.0;
	private ColorWheelPreference colorWheelPreference = ColorWheelPreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	
	public ColorWheelRenderTask(JFXFractalExplorer fractalExplorer, FractalRenderTaskType taskType,FractalCanvas fractalCanvas) {
		super(fractalExplorer, taskType);
		this.fractalCanvas = fractalCanvas;
	}

	@Override
	public void draw() {
		paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(colorWheelPreference.getNumberOfColors(), 0);
		rainbowColors = ColorPreference.createRainbowColors(colorWheelPreference.getNumberOfColors());
		updateMessage("Color Wheel Animation running .....");
		updateProgress(0, 100);
		colorWheelPreference.setJobCanceled(false);
		while(!colorWheelPreference.isJobCanceled()) {
			drawColorWheel();
			if(colorWheelPreference.getTimerDelay() > 0) {
				try {
					Thread.sleep((long)(colorWheelPreference.getTimerDelay()*1000));
				} catch (InterruptedException e) {
				}
			}
		}
		updateMessage(null);
	}

	@Override
	public void animate() {
		
	}

	private void drawColorWheel() {
		double extent = 360.0/colorWheelPreference.getNumberOfColors();
		double angle = turn;
		
		for(int i = 0 ; i < colorWheelPreference.getNumberOfColors(); i++) {
			setFillColor(i);
			fractalCanvas.fillArc(0, 0, colorWheelPreference.getRadius(), angle, extent);
			angle += extent;
		}
		fractalCanvas.refreshScreen();
		turn -= colorWheelPreference.getRotation();
	}
	
	private void setFillColor(int index) {
		Color fillColor = colorPreference.getFillColor();
		
		switch(colorWheelPreference.getColorType()) {
		case GRADIENT_FILL:
			fillColor = ColorPreference.getGradientColor(index, 
					colorWheelPreference.getNumberOfColors(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			fillColor = rainbowColors[index%colorWheelPreference.getNumberOfColors()];
			break;
		case PALETTE_COLR:
			fillColor = paletteColors[index%colorWheelPreference.getNumberOfColors()];
			break;
		case RANDOM_COLR:
			fillColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_FILL_COLOR:
			fillColor = colorPreference.getPenColor();
			break;
		case TWO_COLOR:
			fillColor = index%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		fractalCanvas.setFillColor(fillColor);
	}
}
