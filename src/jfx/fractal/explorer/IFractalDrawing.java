package jfx.fractal.explorer;

import javafx.scene.Node;

public interface IFractalDrawing {
	public void draw();
	public void animate();
	public Node getControlNode();
	public void saveSetting();
	public void saveDrawing();
	public void setupDrawingCanvas();
	public void clearDrawing();
	public void dispose();
}
