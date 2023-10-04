package jfx.fractal.explorer.drawing.plasma;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class PlasmaFractalAction implements EventHandler<ActionEvent>{
private JFXFractalExplorer jfxFractalExplorer;
	
	public PlasmaFractalAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		PlasmaFractal drawing = new PlasmaFractal(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}
}
