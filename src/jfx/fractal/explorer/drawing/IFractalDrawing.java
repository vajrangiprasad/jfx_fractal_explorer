package jfx.fractal.explorer.drawing;

import javafx.scene.Node;

public interface IFractalDrawing {
	public void draw();
	public void animate();
	public void stopRendering();
	public Node getControlNode();
	public void saveSetting();
	public void setupDrawingCanvas();
	public void clearDrawing();
	public void dispose();
	public void disableControls();
	public void enableControls();
}
