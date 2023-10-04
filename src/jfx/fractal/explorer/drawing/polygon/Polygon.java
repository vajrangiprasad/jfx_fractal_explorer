package jfx.fractal.explorer.drawing.polygon;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class Polygon implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer;
	private PolygonPreferencePane polygonPreferencePane;
	private PolygonPreference polygonPreference = PolygonPreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	
	private Turtle turtle;
	
	public Polygon(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		polygonPreferencePane = new PolygonPreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		polygonPreference.addListener(this);
		colorPreference.addListener(this);
	}
	@Override
	public void draw() {
		clearDrawing();
		jfxFractalExplorer.disableControls();
		PolygonRenderTask task = new PolygonRenderTask(jfxFractalExplorer, 
				FractalRenderTaskType.DRAW, 
				turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		clearDrawing();
		jfxFractalExplorer.disableControls();
		PolygonRenderTask task = new PolygonRenderTask(jfxFractalExplorer, 
				FractalRenderTaskType.ANIMATE, 
				turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void stopRendering() {
		polygonPreference.setJobCanceled(true);
	}

	@Override
	public Node getControlNode() {
		return polygonPreferencePane;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDrawingCanvas() {
		turtle = new Turtle(jfxFractalExplorer);
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
		polygonPreference.removeListener(this);
	}

	@Override
	public void disableControls() {
		polygonPreferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		polygonPreferencePane.enableControls();
	}
	@Override
	public void invalidated(Observable observable) {
		clearDrawing();
		draw();
	}

}
