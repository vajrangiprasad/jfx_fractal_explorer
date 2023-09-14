package jfx.fractal.explorer.drawing.gardi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class GardiFracalDrawingAction implements EventHandler<ActionEvent>{
	private JFXFractalExplorer jfxFractalExplorer;
	
	public GardiFracalDrawingAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		GardiFractalDrawing gardiFractalDrawing = new GardiFractalDrawing(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(gardiFractalDrawing);
		gardiFractalDrawing.draw();
	}

}
