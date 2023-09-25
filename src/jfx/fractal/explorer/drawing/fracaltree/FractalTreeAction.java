package jfx.fractal.explorer.drawing.fracaltree;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class FractalTreeAction implements EventHandler<ActionEvent>{
	private JFXFractalExplorer jfxFractalExplorer;
	public FractalTreeAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	@Override
	public void handle(ActionEvent event) {
		FractalTree drawing = new FractalTree(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}

}
