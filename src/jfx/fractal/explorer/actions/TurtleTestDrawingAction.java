package jfx.fractal.explorer.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.turtletest.TurtleTestDrawing;

public class TurtleTestDrawingAction implements EventHandler<ActionEvent> {
	private JFXFractalExplorer jfxFractalExplorer;
	
	public TurtleTestDrawingAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		TurtleTestDrawing turtleTestDrawing = new TurtleTestDrawing(jfxFractalExplorer);
		jfxFractalExplorer.setFractalDrawing(turtleTestDrawing);
	}

}
