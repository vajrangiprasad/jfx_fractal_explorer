package jfx.fractal.explorer.drawing.fracaltree;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;
import jfx.fractal.explorer.turtle.TurtleRefreshMode;

public class FractalTree implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer;
	private FracalTreePreference preference =FracalTreePreference.getInstance();
	private FractalTreePreferencePane fractalTreePreferencePane;
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Turtle turtle;
	
	public FractalTree(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		fractalTreePreferencePane = new FractalTreePreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		preference.addListener(this);
		colorPreference.addListener(this);
	}
	@Override
	public void draw() {
		jfxFractalExplorer.disableControls();
		clearDrawing();
		FractalTreeRenderTask task = new FractalTreeRenderTask(jfxFractalExplorer, FractalRenderTaskType.DRAW, turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		jfxFractalExplorer.disableControls();
		clearDrawing();
		FractalTreeRenderTask task = new FractalTreeRenderTask(jfxFractalExplorer, FractalRenderTaskType.ANIMATE, turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void stopRendering() {
		preference.setJobCanceled(true);
	}

	@Override
	public Node getControlNode() {
		return fractalTreePreferencePane;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDrawingCanvas() {
		turtle = new Turtle(jfxFractalExplorer);
		turtle.setRefreshMode(TurtleRefreshMode.ON_DEMAND);
		turtle.hideTurtle();
	}

	@Override
	public void clearDrawing() {
		turtle.clear();
		turtle.refreshScreen();
	}

	@Override
	public void dispose() {
		clearDrawing();
		colorPreference.removeListener(this);
		preference.removeListener(this);
	}

	@Override
	public void disableControls() {
		fractalTreePreferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		fractalTreePreferencePane.enableControls();
	}
	@Override
	public void invalidated(Observable observable) {
		clearDrawing();
		draw();
	}

}
