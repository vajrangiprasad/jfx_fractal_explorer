package jfx.fractal.explorer.drawing.turtletest;

import javafx.scene.Node;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.turtle.Turtle;

public class TurtleTestDrawing implements IFractalDrawing {
	private JFXFractalExplorer jfxFractalExplorer;
	private TurtleTestControlPanel turtleTestControlPanel;
	private Turtle turtle;
	
	public TurtleTestDrawing(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		turtle = new Turtle(jfxFractalExplorer, "Test Turtle");
		turtleTestControlPanel = new TurtleTestControlPanel(jfxFractalExplorer, turtle);
	}
	
	@Override
	public void draw() {
		
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub

	}

	@Override
	public Node getControlNode() {
		return turtleTestControlPanel;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDrawingCanvas() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearDrawing() {
		turtle.clear();
	}

	@Override
	public void dispose() {
		turtle.dispose();
	}

	@Override
	public void disableControls() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableControls() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopRendering() {
		// TODO Auto-generated method stub
		
	}	
}
