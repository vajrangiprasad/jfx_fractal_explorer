package jfx.fractal.explorer.drawing.dla;

import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.canvas.FractalCanvas;
import jfx.fractal.explorer.drawing.canvas.FractalCanvasRefreshMode;
import jfx.fractal.explorer.drawing.canvas.FractalCanvasStroke;
import jfx.fractal.explorer.drawing.canvas.FractalCanvasStrokeType;

public class DLAFractalCanvas extends FractalCanvas {

	public DLAFractalCanvas(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}
	public DLAFractalCanvas(JFXFractalExplorer jfxFractalExplorer, double width, double height,
			FractalCanvasRefreshMode refreshMode) {
		super(jfxFractalExplorer, width, height, refreshMode);
	}

	@Override
	protected FractalCanvasStroke createStroke(FractalCanvasStrokeType type) {
		FractalCanvasStroke stroke = new FractalCanvasStroke();
		stroke.setPenColor(penColor);
		stroke.setFillColor(fillColor);
		stroke.setPenSize(penSize);
		stroke.setBackgroundColor(backgroundColor);
		stroke.setGc(gc);
		stroke.setType(type);
		stroke.setX1(x1);
		stroke.setY1(y1);
		stroke.setX2(x2);
		stroke.setY2(y2);
		stroke.setSize(width);
		return stroke;
	}
}
