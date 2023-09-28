package jfx.fractal.explorer.drawing.lsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class LSystemFractalAction implements EventHandler<ActionEvent>{
private JFXFractalExplorer jfxFractalExplorer;
	
	public LSystemFractalAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		LSystemFractal drawing = new LSystemFractal(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}
}
