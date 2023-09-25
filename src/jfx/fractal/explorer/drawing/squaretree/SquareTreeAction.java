package jfx.fractal.explorer.drawing.squaretree;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class SquareTreeAction implements EventHandler<ActionEvent>{
	private JFXFractalExplorer jfxFractalExplorer;
	public SquareTreeAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	@Override
	public void handle(ActionEvent event) {
		SquareTree drawing = new SquareTree(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}

}
