package jfx.fractal.explorer.drawing.vertexofsquare;

import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.preference.FillColorType;
import jfx.fractal.explorer.turtle.Turtle;

public class VertexOfSquareRenderTask extends FractalDrawingRenderTask {
	private Turtle turtle;
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private VertexOfSquarePreference vertexOfSquarePreference = VertexOfSquarePreference.getInstance();
	private Color[] paletteColors;
	private Color[] rainbowColors;
	private int totalSquares = 0;
	private int currentSquare = 0;
	
	public VertexOfSquareRenderTask(JFXFractalExplorer fractalExplorer,FractalRenderTaskType taskType,Turtle turtle) {
		super(fractalExplorer, taskType);
		this.turtle = turtle;
	}
	
	@Override
	public void draw() {
		try {
			vertexOfSquarePreference.setJobCanceled(false);
			paletteColors = colorPreference.getSelectedColorPalette().makeRGBs(100, 0);
			rainbowColors = ColorPreference.createRainbowColors(100);
			countSquares();
			double angle = 0;
			currentSquare = 0;
			double size = vertexOfSquarePreference.getSize();
			while(size > 0) {
				if(vertexOfSquarePreference.isJobCanceled()) {
					break;
				}
				currentSquare++;
				drawSquare(size, angle);
				size -= vertexOfSquarePreference.getSizeDelta();
				angle += vertexOfSquarePreference.getAngleDelta();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			fractalExplorer.showExceptionMessage(ex);
		}
	}

	@Override
	public void animate() {
		fractalExplorer.showErrorMessage("Animation is not supported in Vertex Of Square");
	}
	
	
	private void countSquares() {
		double size = vertexOfSquarePreference.getSize();
		while(size > 0) {
			totalSquares++;
			size -= vertexOfSquarePreference.getSizeDelta();
		}
	}
	
	private void drawSquare(double size,double angle) {
		updateMessage("Rendering Square " + currentSquare + " of " + totalSquares);
		updateProgress((currentSquare)/(double)totalSquares*100.0, 100.0);
		turtle.penUp();
		turtle.moveTo(0, 0);
		turtle.penDown();
		turtle.setDirection(angle);
		setPenColor(currentSquare);
		if(vertexOfSquarePreference.getFillColorType() != FillColorType.NO_FILL) {
			setFillColor(currentSquare);
			turtle.beginFilling();
		}
		
		for(int i =1; i<=4 ; i++) {
			turtle.forward(size);
			turtle.left(90);
		}
		if(vertexOfSquarePreference.getFillColorType() != FillColorType.NO_FILL) {
			turtle.endFilling();
		}
	}
	
	private void setFillColor(int currentSquare) {
		Color fillColor = colorPreference.getFillColor();
		
		switch(vertexOfSquarePreference.getFillColorType()) {
		case GRADIENT_FILL:
			fillColor = ColorPreference.getGradientColor(currentSquare, 
					vertexOfSquarePreference.getNumberOfColors(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			fillColor = rainbowColors[currentSquare%vertexOfSquarePreference.getNumberOfColors()];
			break;
		case PALETTE_COLR:
			fillColor = paletteColors[currentSquare%vertexOfSquarePreference.getNumberOfColors()];
			break;
		case RANDOM_COLR:
			fillColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_FILL_COLOR:
			fillColor = colorPreference.getPenColor();
			break;
		case TWO_COLOR:
			fillColor = currentSquare%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		turtle.setFillColor(fillColor);
	}
	
	private void setPenColor(int currentSquare) {
		Color penColor = colorPreference.getPenColor();
		
		switch(vertexOfSquarePreference.getPenColorType()) {
		case GRADIENT_COLOR:
				penColor = ColorPreference.getGradientColor(currentSquare, 
						vertexOfSquarePreference.getNumberOfColors(), 
						colorPreference.getAlternateColor1(), 
						colorPreference.getAlternateColor2());
				break;
		case RAINBOW_COLOR:
			penColor = rainbowColors[currentSquare%vertexOfSquarePreference.getNumberOfColors()];
			break;
		case PALETTE_COLR:
			penColor = paletteColors[currentSquare%vertexOfSquarePreference.getNumberOfColors()];
			break;
		case RANDOM_COLR:
			penColor = ColorPreference.getRandomColor();
			break;
		case TURTLE_PEN_COLOR:
			penColor = colorPreference.getPenColor();
			break;
		case TWO_COLOR:
			penColor = currentSquare%2  == 0 ? colorPreference.getAlternateColor1():colorPreference.getAlternateColor2();
			break;
		default:
			break;
			
		}
		double opacity = 1.0-((double)currentSquare/(double)totalSquares);
		
		turtle.setPenColor(new Color(penColor.getRed(), penColor.getGreen(), penColor.getBlue(), opacity));
	}

}
