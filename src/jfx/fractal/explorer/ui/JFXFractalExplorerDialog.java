package jfx.fractal.explorer.ui;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import jfx.fractal.explorer.JFXFractalExplorer;

public abstract class JFXFractalExplorerDialog extends Dialog<ButtonType>{
	protected JFXFractalExplorer jfxFractalExplorer;
	
	
	public JFXFractalExplorerDialog(JFXFractalExplorer jfxFractalExplorer,
			String title) {
		this.jfxFractalExplorer = jfxFractalExplorer;
		setTitle(title);
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(jfxFractalExplorer.getFractalIcon());
		
		createContentPane();
		
		ButtonType okButtonType = new ButtonType("OK",ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().add(okButtonType);
	}
	
	
	public abstract void createContentPane();
	
}
