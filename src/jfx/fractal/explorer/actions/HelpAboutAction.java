package jfx.fractal.explorer.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jfx.fractal.explorer.JFXFractalExplorer;
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
