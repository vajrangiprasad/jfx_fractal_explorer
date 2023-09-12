package jfx.fractal.explorer.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.preference.PreferenceManager;
import jfx.fractal.explorer.resources.JFXResourceBundle;
import jfx.fractal.explorer.turtle.Turtle;
import jfx.fractal.explorer.ui.dialog.AboutDialog;;

public class HelpAboutAction implements EventHandler<ActionEvent> {
	private JFXFractalExplorer fractalExplorer;
		
	public HelpAboutAction(JFXFractalExplorer fractalExplorer) {
		this.fractalExplorer = fractalExplorer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		AboutDialog aboutDialog = new AboutDialog(fractalExplorer);
		aboutDialog.showAndWait();
	}

}
