package jfx.fractal.explorer.drawing.mandelbrotset;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.preference.ColorPreference;

public class MandelbrotDrawing implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer;
	private MandelbrotPreference preference = MandelbrotPreference.getInstance();
	private MandelbrotPreferencePane preferencePane;
	private MandelbrotCanvas mandelbrotCanvas;
	private MandelbrotCanvas mandelbrotPreviewCanvas;
	
	public MandelbrotDrawing(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		preferencePane = new MandelbrotPreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		preference.addListener(this);
		ColorPreference.getInstance().addListener(this);
	}

	@Override
	public void draw() {
		preference.setJobCanceled(false);
		mandelbrotCanvas.draw();
		mandelbrotPreviewCanvas.draw();
	}

	public MandelbrotCanvas getMandelbrotPreviewCanvas() {
		return mandelbrotPreviewCanvas;
	}

	public void setMandelbrotPreviewCanvas(MandelbrotCanvas mandelbrotPreviewCanvas) {
		this.mandelbrotPreviewCanvas = mandelbrotPreviewCanvas;
	}

	@Override
	public void animate() {
		jfxFractalExplorer.showErrorMessage("Animation not suppored in Mandelbrot set");
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
		mandelbrotPreviewCanvas = new MandelbrotCanvas(jfxFractalExplorer,200.0);
		preferencePane.addPreviewCanvas(mandelbrotPreviewCanvas);
		mandelbrotCanvas = new MandelbrotCanvas(jfxFractalExplorer,FractalConstants.FRACTAL_DISPLAY_SIZE);
	}

	@Override
	public void clearDrawing() {
		mandelbrotCanvas.clearDisplay();
	}

	@Override
	public void dispose() {
		preference.removeListener(this);
		ColorPreference.getInstance().removeListener(this);
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
		mandelbrotCanvas.draw();
		mandelbrotPreviewCanvas.draw();
	}
}
