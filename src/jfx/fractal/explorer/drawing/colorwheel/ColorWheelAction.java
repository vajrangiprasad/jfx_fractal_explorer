package jfx.fractal.explorer.drawing.colorwheel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;

public class ColorWheelAction implements EventHandler<ActionEvent> {
	private JFXFractalExplorer jfxFractalExplorer;
	
	public ColorWheelAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		ColorWheel drawing = new ColorWheel(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(drawing);
		drawing.draw();
	}
}