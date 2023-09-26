package jfx.fractal.explorer.drawing.curvytree;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class CurvyTreeAction implements EventHandler<ActionEvent> {
	private JFXFractalExplorer jfxFractalExplorer;
	
	public CurvyTreeAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		CurvyTree drawing = new CurvyTree(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}

}
