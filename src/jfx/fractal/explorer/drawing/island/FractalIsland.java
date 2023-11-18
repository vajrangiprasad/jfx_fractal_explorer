package jfx.fractal.explorer.drawing.island;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.drawing.polygon.PolygonRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class FractalIsland implements IFractalDrawing,InvalidationListener{
	private JFXFractalExplorer jfxFractalExplorer;
	private FractalIslandPreferencePane preferencePane;
	private FractalIslandPreference preference = FractalIslandPreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Turtle turtle;
	
	public FractalIsland(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer	= jfxFractalExplorer;
		preferencePane = new FractalIslandPreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		preference.addListener(this);
		colorPreference.addListener(this);
	}
	@Override
	public void invalidated(Observable observable) {
		clearDrawing();
		draw();
	}

	@Override
	public void draw() {
		clearDrawing();
		jfxFractalExplorer.disableControls();
		FractalIslandRenderTask task = new FractalIslandRenderTask(jfxFractalExplorer, 
				FractalRenderTaskType.DRAW, 
				turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopRendering() {
		preference.setJobCanceled(true);
	}

	@Override
	public Node getControlNode() {
		return preferencePane;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupDrawingCanvas() {
		turtle = new Turtle(jfxFractalExplorer);
		turtle.hideTurtle();
	}

	@Override
	public void clearDrawing() {
		turtle.clear();
	}

	@Override
	public void dispose() {
		turtle.clear();
		turtle.dispose();
		colorPreference.removeListener(this);
		preference.removeListener(this);
	}

	@Override
	public void disableControls() {
		preferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		preferencePane.enableControls();
	}

}
