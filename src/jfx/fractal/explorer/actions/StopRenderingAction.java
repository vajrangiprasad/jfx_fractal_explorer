package jfx.fractal.explorer.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class StopRenderingAction implements EventHandler<ActionEvent>{
	private JFXFractalExplorer jfxFractalExplorer;
	
	public StopRenderingAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(jfxFractalExplorer.getFractalDrawing() == null) {
			return;
		}
		jfxFractalExplorer.getFractalDrawing().stopRendering();
	}

}
