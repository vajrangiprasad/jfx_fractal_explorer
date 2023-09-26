package jfx.fractal.explorer.drawing.curvytree;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;
import jfx.fractal.explorer.turtle.TurtleRefreshMode;

public class CurvyTree implements IFractalDrawing ,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer;
	private CurvyTreePreference curvyTreePreference =CurvyTreePreference.getInstance();
	private CurvyTreePreferencePane curvyTreePreferencePane;
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Turtle turtle;
	
	public CurvyTree(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		curvyTreePreferencePane = new CurvyTreePreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		colorPreference.addListener(this);
		curvyTreePreference.addListener(this);
	}
	
	@Override
	public void draw() {
		jfxFractalExplorer.disableControls();
		clearDrawing();
		CurvyTreeRenderTask task = new CurvyTreeRenderTask(jfxFractalExplorer, FractalRenderTaskType.DRAW, turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		jfxFractalExplorer.disableControls();
		clearDrawing();
		CurvyTreeRenderTask task = new CurvyTreeRenderTask(jfxFractalExplorer, FractalRenderTaskType.ANIMATE, turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void stopRendering() {
		curvyTreePreference.setJobCanceled(true);
	}

	@Override
	public Node getControlNode() {
		return curvyTreePreferencePane;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDrawingCanvas() {
		turtle = new Turtle(jfxFractalExplorer);
		turtle.setRefreshMode(TurtleRefreshMode.ON_CHANGE);
		turtle.setWorldCordinates(-1000, 1000, -1000, 1000);
		if(curvyTreePreference.isShowTurtle()) {
			turtle.showTurtle();
		}
		else {
			turtle.hideTurtle();
		}
		turtle.refreshScreen();
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
		curvyTreePreference.removeListener(this);
	}

	@Override
	public void disableControls() {
		curvyTreePreferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		curvyTreePreferencePane.enableControls();
	}
	@Override
	public void invalidated(Observable observable) {
		if("ShowTurtle".equals(curvyTreePreference.getEventId())) {
			if(curvyTreePreference.isShowTurtle()) {
				turtle.showTurtle();
			}
			else {
				turtle.hideTurtle();
			}
			turtle.refreshScreen();
			return;
		}
		clearDrawing();
		draw();
	}

}
