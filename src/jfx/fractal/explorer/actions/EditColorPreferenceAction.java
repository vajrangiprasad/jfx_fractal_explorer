package jfx.fractal.explorer.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.resources.JFXResourceBundle;
import jfx.fractal.explorer.ui.dialog.EditColorPreferenceDialog;

public class EditColorPreferenceAction implements EventHandler<ActionEvent>{
	private JFXFractalExplorer jfxFractalExplorer;
	
	public EditColorPreferenceAction(JFXFractalExplorer jfxFractalExplore) {
		this.jfxFractalExplorer = jfxFractalExplore;
	}
	
	@Override
	public void handle(ActionEvent event) {
		EditColorPreferenceDialog editColorPreferenceDialog = new EditColorPreferenceDialog(jfxFractalExplorer,
				JFXResourceBundle.getString("jfx.fractal.colorPreferenceDialog.title"));
		editColorPreferenceDialog.showAndWait();
	}

}
