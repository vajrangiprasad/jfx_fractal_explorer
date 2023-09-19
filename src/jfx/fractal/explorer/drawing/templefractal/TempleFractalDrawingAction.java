package jfx.fractal.explorer.drawing.templefractal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class TempleFractalDrawingAction implements EventHandler<ActionEvent>{
	private JFXFractalExplorer jfxFractalExplorer;
	
	public TempleFractalDrawingAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		TempleFractalDrawing drawing = new TempleFractalDrawing(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}
}
