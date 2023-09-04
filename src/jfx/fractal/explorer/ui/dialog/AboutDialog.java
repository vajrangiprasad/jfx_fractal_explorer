package jfx.fractal.explorer.ui.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.resources.JFXResourceBundle;
import jfx.fractal.explorer.ui.JFXFractalExplorerDialog;

public class AboutDialog extends JFXFractalExplorerDialog {

	public AboutDialog(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer,JFXResourceBundle.getString("jfx.fractal.about.title"));
	}

	@Override
	public void createContentPane() {
		GridPane grid = new GridPane();
		grid.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-alignment:center;-fx-padding:5");
		
		ImageView imageView = new ImageView(jfxFractalExplorer.getFractalIcon());
		grid.add(imageView, 0, 0);
		
		Label aboutText = new Label(JFXResourceBundle.getString("jfx.fractal.about.text"));
		grid.add(aboutText, 1, 0);
		
		getDialogPane().setContent(grid);
	}
}
