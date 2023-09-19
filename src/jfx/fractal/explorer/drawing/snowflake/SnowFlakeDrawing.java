package jfx.fractal.explorer.drawing.snowflake;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class SnowFlakeDrawing implements IFractalDrawing ,InvalidationListener{
	private JFXFractalExplorer jfxFractalExplorer;
	private SnowFlakeDrawingPreferencePane snowFlakeDrawingPreferencePane;
	private SnowFlakeDrawingPreference snowFlakeDrawingPreference = SnowFlakeDrawingPreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private Turtle turtle;

	
	public SnowFlakeDrawing(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer= jfxFractalExplorer;
		snowFlakeDrawingPreferencePane = new SnowFlakeDrawingPreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		snowFlakeDrawingPreference.addListener(this);
		colorPreference.addListener(this);
	}
	
	@Override
	public void draw() {
		jfxFractalExplorer.disableControls();
		SnowFlakeDrawingRenderTask task = new SnowFlakeDrawingRenderTask(jfxFractalExplorer, 
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
	public Node getControlNode() {
		return snowFlakeDrawingPreferencePane;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDrawingCanvas() {
		turtle = new Turtle(jfxFractalExplorer, "Snow Flake");
	}

	@Override
	public void clearDrawing() {
		turtle.clear();
	}

	@Override
	public void dispose() {
		clearDrawing();
		colorPreference.removeListener(this);
		snowFlakeDrawingPreference.removeListener(this);
	}

	@Override
	public void invalidated(Observable observable) {
		clearDrawing();
		draw();
	}

	@Override
	public void disableControls() {
		snowFlakeDrawingPreferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		snowFlakeDrawingPreferencePane.enableControls();
	}

}
