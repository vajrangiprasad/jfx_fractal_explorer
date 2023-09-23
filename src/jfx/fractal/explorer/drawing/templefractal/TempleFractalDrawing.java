package jfx.fractal.explorer.drawing.templefractal;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class TempleFractalDrawing implements IFractalDrawing,InvalidationListener{
	private JFXFractalExplorer jfxFractalExplorer;
	private TempleFractalPreference templeFractalPreference = TempleFractalPreference.getInstance();
	private TempleFractalPreferencePane templeFractalPreferencePane;
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Turtle turtle;
	
	public TempleFractalDrawing(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		templeFractalPreferencePane = new TempleFractalPreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		templeFractalPreference.addListener(this);
		colorPreference.addListener(this);
	}
	
	@Override
	public void draw() {
		clearDrawing();
		jfxFractalExplorer.disableControls();
		TempleFractalRenderTask task = new TempleFractalRenderTask(jfxFractalExplorer, 
				FractalRenderTaskType.DRAW, 
				turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		jfxFractalExplorer.showErrorMessage("Animation is not supported in Temple Fractal");
	}

	@Override
	public Node getControlNode() {
		return templeFractalPreferencePane;
	}

	@Override
	public void saveSetting() {
		jfxFractalExplorer.showErrorMessage("Save Settings is not supported in Temple Fractal");
	}

	@Override
	public void setupDrawingCanvas() {
		turtle = new Turtle(jfxFractalExplorer, "Temple Fractal");
	}

	@Override
	public void clearDrawing() {
		turtle.clear();
	}

	@Override
	public void dispose() {
		templeFractalPreference.removeListener(this);
		colorPreference.removeListener(this);
	}

	@Override
	public void disableControls() {
		templeFractalPreferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		templeFractalPreferencePane.enableControls();
	}

	@Override
	public void invalidated(Observable observable) {
		clearDrawing();
		draw();
	}

	@Override
	public void stopRendering() {
		templeFractalPreference.setJobCanceled(true);
	}

}
