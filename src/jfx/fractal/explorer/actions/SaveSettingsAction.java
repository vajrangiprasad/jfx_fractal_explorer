package jfx.fractal.explorer.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.IFractalDrawing;
import jfx.fractal.explorer.resources.JFXResourceBundle;

public class SaveSettingsAction implements EventHandler<ActionEvent>{
	private JFXFractalExplorer jfxFractalExplorer ;
	
	public SaveSettingsAction(JFXFractalExplorer jfxFractalExplorer) {
		this.jfxFractalExplorer = jfxFractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		IFractalDrawing fractalDrawing = jfxFractalExplorer.getFractalDrawing();
		if(fractalDrawing == null) {
			jfxFractalExplorer.showErrorMessage(JFXResourceBundle.getString("jfx.fractal.explorer.error.nodrawing"));
			return;
		}
		fractalDrawing.saveSetting();
	}
}
