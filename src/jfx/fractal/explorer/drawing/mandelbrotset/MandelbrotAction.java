package jfx.fractal.explorer.drawing.mandelbrotset;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.plasma.PlasmaFractal;

public class MandelbrotAction implements EventHandler<ActionEvent>{
private JFXFractalExplorer jfxFractalExplorer;
	
	public MandelbrotAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		MandelbrotDrawing drawing = new MandelbrotDrawing(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}
}
