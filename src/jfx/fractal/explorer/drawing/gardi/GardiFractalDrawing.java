package jfx.fractal.explorer.drawing.gardi;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class GardiFractalDrawing implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer;
	private GardiFractalPreferencePane controlPane ;
	private GardiFractalPreference gardiFractalPreference = GardiFractalPreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Turtle turtle;
	
	public GardiFractalDrawing(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		controlPane = new GardiFractalPreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		gardiFractalPreference.addListener(this);
		colorPreference.addListener(this);
	}
	
	@Override
	public void draw() {
		GardiFractalRenderTask task = new GardiFractalRenderTask(jfxFractalExplorer, FractalRenderTaskType.DRAW, turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		jfxFractalExplorer.showErrorMessage("Animation is not supported by Gardi Fractal");
	}

	@Override
	public Node getControlNode() {
		return controlPane;
	}

	@Override
	public void saveSetting() {
		jfxFractalExplorer.showErrorMessage("Save Settings is not supported by Gardi Fractal");
	}

	@Override
	public void setupDrawingCanvas() {
		turtle = new Turtle(jfxFractalExplorer, "Gardi");
	}

	@Override
	public void clearDrawing() {
		turtle.clear();
	}

	@Override
	public void dispose() {
		gardiFractalPreference.removeListener(this);
		colorPreference.removeListener(this);
		turtle.dispose();
	}

	@Override
	public void invalidated(Observable observable) {
		clearDrawing();
		draw();
	}

	@Override
	public void disableControls() {
		controlPane.disableControls();
	}

	@Override
	public void enableControls() {
		controlPane.enableControls();
	}

	@Override
	public void stopRendering() {
		gardiFractalPreference.setJobCanceled(true);
	}
}
