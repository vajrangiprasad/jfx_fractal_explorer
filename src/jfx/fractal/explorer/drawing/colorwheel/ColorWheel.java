package jfx.fractal.explorer.drawing.colorwheel;

import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.drawing.canvas.FractalCanvas;
import jfx.fractal.explorer.drawing.canvas.FractalCanvasRefreshMode;

public class ColorWheel implements IFractalDrawing {
	private JFXFractalExplorer jfxFractalExplorer;
	private ColorWheelPreferencePane colorWheelPreferencePane;
	private ColorWheelPreference colorWheelPreference = ColorWheelPreference.getInstance();
	private FractalCanvas fractalCanvas;
	
	public ColorWheel(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		colorWheelPreferencePane = new ColorWheelPreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
	}
	@Override
	public void draw() {
		animate();		
	}

	@Override
	public void animate() {
		jfxFractalExplorer.disableControls();
		clearDrawing();
		ColorWheelRenderTask task = new ColorWheelRenderTask(jfxFractalExplorer, FractalRenderTaskType.DRAW, fractalCanvas);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void stopRendering() {
		colorWheelPreference.setJobCanceled(true);
	}

	@Override
	public Node getControlNode() {
		return colorWheelPreferencePane;
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDrawingCanvas() {
		fractalCanvas = new FractalCanvas(jfxFractalExplorer);
		fractalCanvas.setScale(-1, 1);
		fractalCanvas.setRefreshMode(FractalCanvasRefreshMode.ON_DEMAND);
	}

	@Override
	public void clearDrawing() {
		fractalCanvas.clear();
	}

	@Override
	public void dispose() {
		fractalCanvas.dispose();
	}

	@Override
	public void disableControls() {
		colorWheelPreferencePane.disableControls();
	}

	@Override
	public void enableControls() {
		colorWheelPreferencePane.enableControls();
	}

}
