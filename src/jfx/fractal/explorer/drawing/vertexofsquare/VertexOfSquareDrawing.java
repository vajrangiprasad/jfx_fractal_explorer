package jfx.fractal.explorer.drawing.vertexofsquare;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import jfx.fractal.explorer.FractalRenderTaskType;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.drawing.templefractal.TempleFractalRenderTask;
import jfx.fractal.explorer.preference.ColorPreference;
import jfx.fractal.explorer.turtle.Turtle;

public class VertexOfSquareDrawing implements IFractalDrawing,InvalidationListener {
	private JFXFractalExplorer jfxFractalExplorer;
	private VertexOfSquarePreferencePane preferencePane;
	private ColorPreference colorPreference = ColorPreference.getInstance();
	private VertexOfSquarePreference vertexOfSquarePreference = VertexOfSquarePreference.getInstance();
	
	private Turtle turtle;
	
	public VertexOfSquareDrawing(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		preferencePane = new VertexOfSquarePreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
		colorPreference.addListener(this);
		vertexOfSquarePreference.addListener(this);
	}
	
	@Override
	public void draw() {
		clearDrawing();
		jfxFractalExplorer.disableControls();
		VertexOfSquareRenderTask task = new VertexOfSquareRenderTask(jfxFractalExplorer, 
				FractalRenderTaskType.DRAW, 
				turtle);
		Thread drawThread = new Thread(task);
		drawThread.setDaemon(true);
		drawThread.start();
	}

	@Override
	public void animate() {
		jfxFractalExplorer.showErrorMessage("Animation is not supported in Vertex Of Square");
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
		turtle = new Turtle(jfxFractalExplorer, "VerexOfSquare");
		turtle.hideTurtle();
	}

	@Override
	public void clearDrawing() {
		turtle.clear();
	}

	@Override
	public void dispose() {
		clearDrawing();
		colorPreference.removeListener(this);
		vertexOfSquarePreference.removeListener(this);
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

	@Override
	public void stopRendering() {
		vertexOfSquarePreference.setJobCanceled(true);
	}

}
