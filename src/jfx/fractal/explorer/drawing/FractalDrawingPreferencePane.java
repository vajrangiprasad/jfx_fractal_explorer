package jfx.fractal.explorer.drawing;

import javafx.scene.layout.BorderPane;
import jfx.fractal.explorer.JFXFractalExplorer;

public abstract class FractalDrawingPreferencePane extends BorderPane {
	protected JFXFractalExplorer jfxFractalExplorer;
	
	public FractalDrawingPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		initialize();
	}
	
	public void initialize() {
		setStyle("-fx-padding:5;");
		createHeaderPane();
		createParametersPane();
	}
	
	public abstract void createHeaderPane();
	public abstract void createParametersPane();
}
