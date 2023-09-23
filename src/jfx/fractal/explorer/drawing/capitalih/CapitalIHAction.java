package jfx.fractal.explorer.drawing.capitalih;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class CapitalIHAction implements EventHandler<ActionEvent>{
private JFXFractalExplorer jfxFractalExplorer;
	
	public CapitalIHAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		CapitalIHDrawing drawing = new CapitalIHDrawing(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}
}
