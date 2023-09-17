package jfx.fractal.explorer.drawing.snowflake;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class SnowFlakeDrawingAction implements EventHandler<ActionEvent>{
	private JFXFractalExplorer jfxFractalExplorer;
	
	public SnowFlakeDrawingAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		SnowFlakeDrawing drawing = new SnowFlakeDrawing(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}

}
