package jfx.fractal.explorer.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;

public class ClearDrawingAction implements EventHandler<ActionEvent>{
	private JFXFractalExplorer jfxFractalExplorer;
	
	public ClearDrawingAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		IFractalDrawing fractalDrawing = jfxFractalExplorer.getFractalDrawing();
		if(fractalDrawing == null) {
			return;
		}
		
		fractalDrawing.clearDrawing();
	}

}
