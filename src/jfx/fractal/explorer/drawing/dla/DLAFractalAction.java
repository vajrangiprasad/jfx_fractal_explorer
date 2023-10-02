package jfx.fractal.explorer.drawing.dla;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class DLAFractalAction implements EventHandler<ActionEvent> {
	private JFXFractalExplorer jfxFractalExplorer;
	public DLAFractalAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	@Override
	public void handle(ActionEvent event) {
		DLAFractal drawing = new DLAFractal(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}
}
