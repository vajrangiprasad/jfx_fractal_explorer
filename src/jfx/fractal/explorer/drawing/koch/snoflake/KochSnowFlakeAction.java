package jfx.fractal.explorer.drawing.koch.snoflake;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class KochSnowFlakeAction implements EventHandler<ActionEvent> {
private JFXFractalExplorer jfxFractalExplorer;
	
	public KochSnowFlakeAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		KochSnowFlakeFractal drawing = new KochSnowFlakeFractal(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}
}
