package jfx.fractal.explorer.drawing.canvas;

import javafx.scene.canvas.GraphicsContext;
import jfx.fractal.explorer.preference.TurtlePreference;

public class FractalCanvasCommand implements Runnable {
	private FractalCanvasStroke stroke;
	
	public FractalCanvasCommand(FractalCanvasStroke stroke) {
		this.stroke = stroke;
	}
	
	
	public FractalCanvasStroke getStroke() {
		return stroke;
	}


	public void setStroke(FractalCanvasStroke stroke) {
		this.stroke = stroke;
	}


	@Override
	public void run() {
		GraphicsContext gc = stroke.getGc();
		TurtlePreference turtlePreference = TurtlePreference.getInstance();
		try {
			gc.save();
			gc.setStroke(stroke.getPenColor());
			gc.setFill(stroke.getFillColor());
			gc.setLineWidth(stroke.getPenSize());
			gc.setGlobalAlpha(turtlePreference.getAlpha());
			gc.setGlobalBlendMode(turtlePreference.getBlendMode());
			gc.setLineCap(turtlePreference.getStrokeLineCap());
			gc.setLineJoin(turtlePreference.getStrokeLineJoin());
			
			switch (stroke.getType()) {
			case CLEAR:
				gc.clearRect(0, 0, stroke.getSize(), stroke.getSize());
				break;
			case LINE:
				gc.strokeLine(stroke.getX1(), stroke.getY1(), stroke.getX2(), stroke.getY2());
				break;
			case PIXEL:
				gc.strokeOval(stroke.getX1(), stroke.getY1(), 1, 1);
				break;
			case SQUARE:
				gc.strokeRect(stroke.getX1()-stroke.getW(), stroke.getY1()-stroke.getH(), stroke.getW()*2, stroke.getH()*2);
				break;
			case FILLED_SQUARE:
				gc.fillRect(stroke.getX1()-stroke.getW(), stroke.getY1()-stroke.getH(), stroke.getW()*2, stroke.getH()*2);
				break;
			}
		} finally {
			gc.restore();
		}
	}
}
