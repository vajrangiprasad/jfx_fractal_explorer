package jfx.fractal.explorer.drawing.island;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class FractalIslandAction implements EventHandler<ActionEvent>{

private JFXFractalExplorer jfxFractalExplorer;
	
	public FractalIslandAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		FractalIsland drawing = new FractalIsland(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}

}
