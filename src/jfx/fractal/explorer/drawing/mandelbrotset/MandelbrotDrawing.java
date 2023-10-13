package jfx.fractal.explorer.drawing.mandelbrotset;

import java.util.ArrayList;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.FractalConstants;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.util.ComplexNumber;

public class MandelbrotDrawing implements IFractalDrawing{
	private JFXFractalExplorer jfxFractalExplorer;
	private MandelbrotPreference preference = MandelbrotPreference.getInstance();
	private MandelbrotPreferencePane preferencePane;
	private MandelbrotCanvas mandelbrotCanvas;
	
	public MandelbrotDrawing(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		preferencePane = new MandelbrotPreferencePane(jfxFractalExplorer);
		setupDrawingCanvas();
	}

	@Override
	public void draw() {
		mandelbrotCanvas.draw();
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopRendering() {
		// TODO Auto-generated method stub
		
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
		mandelbrotCanvas = new MandelbrotCanvas(jfxFractalExplorer);
	}

	@Override
	public void clearDrawing() {
		mandelbrotCanvas.clearDisplay();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableControls() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableControls() {
		// TODO Auto-generated method stub
		
	}
}
