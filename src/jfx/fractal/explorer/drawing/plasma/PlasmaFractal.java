package jfx.fractal.explorer.drawing.plasma;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.drawing.canvas.FractalCanvas;
import jfx.fractal.explorer.drawing.canvas.FractalCanvasRefreshMode;

public class PlasmaFractal implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer;
	private PlasmaFractalPreference plasmaFractalPreference = PlasmaFractalPreference.getInstance();
	private PlasmaFractalPreferencePane preferencePane ;
	private FractalCanvas fractalCanvas;
	
	public PlasmaFractal(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		preferencePane = new PlasmaFractalPreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		plasmaFractalPreference.addListener(this);
	}
	
	@Override
	public void draw() {
		clearDrawing();
		plasmaFractalPreference.setJobCanceled(false);
		jfxFractalExplorer.disableControls();
		PlasmaFractalRenderTask task = new PlasmaFractalRenderTask(jfxFractalExplorer, 
				FractalRenderTaskType.DRAW, 
				fractalCanvas);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		plasmaFractalPreference.setJobCanceled(false);
		jfxFractalExplorer.disableControls();
		PlasmaFractalRenderTask task = new PlasmaFractalRenderTask(jfxFractalExplorer, 
				FractalRenderTaskType.ANIMATE, 
				fractalCanvas);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void stopRendering() {
		plasmaFractalPreference.setJobCanceled(true);
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
		fractalCanvas = new FractalCanvas(jfxFractalExplorer);
		fractalCanvas.setRefreshMode(FractalCanvasRefreshMode.ON_CHANGE);
		fractalCanvas.setScale(0, 900);
		fractalCanvas.setPenSize(2);
	}

	@Override
	public void clearDrawing() {
		fractalCanvas.clear();
	}

	@Override
	public void dispose() {
		clearDrawing();
		fractalCanvas.dispose();
		plasmaFractalPreference.removeListener(this);
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
		clearDrawing();
		draw();
	}

}
