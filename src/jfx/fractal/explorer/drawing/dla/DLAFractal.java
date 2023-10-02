package jfx.fractal.explorer.drawing.dla;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.drawing.canvas.FractalCanvas;
import jfx.fractal.explorer.preference.ColorPreference;

public class DLAFractal implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer explorer;
	private DLAPreferencePane dlaPreferencePane;
	private DLAPreference dlaPreference = DLAPreference.getInstance();
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private FractalCanvas fractalCanvas;
	
	public DLAFractal(JFXFractalExplorer explorer) {
		this.explorer = explorer;
		dlaPreferencePane = new DLAPreferencePane(explorer);
		setupDrawingCanvas();
		dlaPreference.addListener(this);
		colorPreference.addListener(this);
	}
	
	@Override
	public void draw() {
		explorer.disableControls();
		clearDrawing();
		DLAFractalRenderTask task = new DLAFractalRenderTask(explorer, FractalRenderTaskType.DRAW, fractalCanvas);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
		
	}

	@Override
	public void animate() {
		explorer.showErrorMessage("Animation not support in Diffusion Limited Agrgation Fractal");
	}

	@Override
	public void stopRendering() {
		dlaPreference.setJobCanceled(true);
	}

	@Override
	public Node getControlNode() {
		return dlaPreferencePane;
	}

	@Override
	public void saveSetting() {
		
	}

	@Override
	public void setupDrawingCanvas() {
		fractalCanvas = new DLAFractalCanvas(explorer);
	}

	@Override
	public void clearDrawing() {
		fractalCanvas.clear();
	}

	@Override
	public void dispose() {
		clearDrawing();
		dlaPreference.removeListener(this);
		colorPreference.removeListener(this);
	}

	@Override
	public void disableControls() {
		dlaPreferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		dlaPreferencePane.enableControls();
	}

	@Override
	public void invalidated(Observable observable) {
		clearDrawing();
		draw();
	}

}
