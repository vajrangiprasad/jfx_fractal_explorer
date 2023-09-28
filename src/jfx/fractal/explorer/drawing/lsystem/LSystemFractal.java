package jfx.fractal.explorer.drawing.lsystem;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.drawing.gardi.GardiFractalRenderTask;
import jfx.fractal.explorer.turtle.Turtle;

public class LSystemFractal implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer;
	private LSystemPrefereence lSystemPrefereence = LSystemPrefereence.getInstance();
	private LSystemPreferencePane lSystemPreferencePane;
	
	private Turtle turtle;
	
	public LSystemFractal(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		setupDrawingCanvas();
		lSystemPreferencePane = new LSystemPreferencePane(jfxFractalExplorer);
		lSystemPrefereence.addListener(this);
	}
	
	@Override
	public void draw() {
		jfxFractalExplorer.disableControls();
		clearDrawing();
		LSystemRenderTask task = new LSystemRenderTask(jfxFractalExplorer, FractalRenderTaskType.DRAW, turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		jfxFractalExplorer.showErrorMessage("Animation is not supported by L-System Fractal");
	}

	@Override
	public void stopRendering() {
		// TODO Auto-generated method stub

	}

	@Override
	public Node getControlNode() {
		return lSystemPreferencePane;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDrawingCanvas() {
		turtle = new Turtle(jfxFractalExplorer);
		double size = 450;
		turtle.setWorldCordinates(-size,size, -size, size);
	}

	@Override
	public void clearDrawing() {
		turtle.clear();
	}

	@Override
	public void dispose() {
		clearDrawing();
	}

	@Override
	public void disableControls() {
		lSystemPreferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		lSystemPreferencePane.enableControls();
	}

	@Override
	public void invalidated(Observable observable) {
		draw();
	}
}
