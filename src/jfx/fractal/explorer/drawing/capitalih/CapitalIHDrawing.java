package jfx.fractal.explorer.drawing.capitalih;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class CapitalIHDrawing implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer;
	private CapitalIFPreferencePane preferencePane;
	private CapitalIHPreference capitalIHPreference = CapitalIHPreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Turtle turtle;
	
	public CapitalIHDrawing(JFXFractalExplorer jfxFractalExplore) {
		this.jfxFractalExplorer = jfxFractalExplore;
		preferencePane = new CapitalIFPreferencePane(jfxFractalExplore);
		setupDrawingCanvas();
		capitalIHPreference.addListener(this);
		colorPreference.addListener(this);
	}
	
	@Override
	public void draw() {
		jfxFractalExplorer.disableControls();
		CapitalIHRenderTak task = new CapitalIHRenderTak(jfxFractalExplorer, 
				FractalRenderTaskType.DRAW, 
				turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		jfxFractalExplorer.showErrorMessage("Animation is not supported Capital IH Fractal");
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
		if(capitalIHPreference.isShowTurtle()) {
			turtle.showTurtle();
		}else {
			turtle.hideTurtle();
		}
	}

	@Override
	public void clearDrawing() {
		turtle.clear();
	}

	@Override
	public void dispose() {
		turtle.clear();
		colorPreference.removeListener(this);
		capitalIHPreference.removeListener(this);
	}

	@Override
	public void disableControls() {
		preferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		preferencePane.enableControls();
	}

	@Override
	public void invalidated(Observable observable) {
		if(observable instanceof CapitalIHPreference && 
				"ShowTurtle".equals(capitalIHPreference.getEventId()))  {
			if(capitalIHPreference.isShowTurtle()) {
				turtle.showTurtle();
			}else {
				turtle.hideTurtle();
			}
			return;
		}
		clearDrawing();
		draw();
	}

}
