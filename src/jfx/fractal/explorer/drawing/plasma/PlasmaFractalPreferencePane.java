package jfx.fractal.explorer.drawing.plasma;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jfx.fractal.explorer.JFXFractalExplorer;
import jfx.fractal.explorer.drawing.FractalDrawingPreferencePane;

public class PlasmaFractalPreferencePane extends FractalDrawingPreferencePane {
	private Spinner<Double> spinnerSizePercent;
	private TextField txtMinSize;
	private TextField txtSaturation;
	private TextField txtBrightness;
	private TextField txtStandardDeviation;
	private CheckBox chkGrayScale;
	
	public PlasmaFractalPreferencePane(JFXFractalExplorer jfxFractalExplorer) {
		super(jfxFractalExplorer);
	}

	@Override
	public void createHeaderPane() {
		GridPane labelPane = new GridPane();
		labelPane.setStyle("-fx-padding: 5;-fx-alignment:center");
		setTop(labelPane);
		
		Label lblDrawing = new Label("Plasma Cloud");
		lblDrawing.setStyle("-fx-font-size:14;-fx-font-weight:bold");
		labelPane.add(lblDrawing, 0, 0);
	}

	@Override
	public void createParametersPane() {
		PlasmaFractalPreference preference = PlasmaFractalPreference.getInstance();
		
		GridPane parametersPane = new GridPane();
		parametersPane.setStyle("-fx-hgap:5;-fx-vgap:5;-fx-padding:5;-fx-alignment:top-left;");
		setCenter(parametersPane);
		
		{
			Label lblSizePercent = new Label("Size %");
			parametersPane.add(lblSizePercent, 0, 0);
			
			spinnerSizePercent = new Spinner<>(1.0, 100.0, preference.getSizePercent(), 1.0);
			parametersPane.add(spinnerSizePercent, 1, 0);
			spinnerSizePercent.valueProperty().addListener(listner -> {
				preference.setSizePercent(spinnerSizePercent.getValue());
			});
		}
		
		{
			Label lblMinSize = new Label("Minimum Size");
			parametersPane.add(lblMinSize, 0, 1);
			
			txtMinSize = new TextField(String.valueOf(preference.getMinSize()));
			parametersPane.add(txtMinSize, 1, 1);
			txtMinSize.setOnAction(e-> {
				try {
					double minSize = Double.valueOf(txtMinSize.getText());
					if(minSize < 0.0  || minSize > PlasmaFractalPreference.MAX_SIZE) {
						jfxFractalExplorer.showErrorMessage("Minimum size should be in the range 0.0 to " + PlasmaFractalPreference.MAX_SIZE);
						return;
					}
					preference.setMinSize(minSize);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			Label lblSaturation = new Label("Saturation");
			parametersPane.add(lblSaturation, 0, 2);
			
			txtSaturation = new TextField(String.valueOf(preference.getSaturation()));
			parametersPane.add(txtSaturation, 1, 2);
			txtSaturation.setOnAction(e-> {
				try {
					double saturation = Double.valueOf(txtSaturation.getText());
					if(saturation < 0.0  || saturation > 1.0) {
						jfxFractalExplorer.showErrorMessage("Saturation should be in the range 0.0 to 1.0");
						return;
					}
					preference.setSaturation(saturation);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			Label lblBrightnerss = new Label("Brightness");
			parametersPane.add(lblBrightnerss, 0, 3);
			
			txtBrightness = new TextField(String.valueOf(preference.getBrightness()));
			parametersPane.add(txtBrightness, 1, 3);
			txtBrightness.setOnAction(e-> {
				try {
					double brightness = Double.valueOf(txtBrightness.getText());
					if(brightness < 0.0  || brightness > 1.0) {
						jfxFractalExplorer.showErrorMessage("Brightness should be in the range 0.0 to 1.0");
						return;
					}
					preference.setBrightness(brightness);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			Label lblStandardDeviation = new Label("Stadard Deviation");
			parametersPane.add(lblStandardDeviation, 0, 4);
			
			txtStandardDeviation = new TextField(String.valueOf(preference.getStandardDeviation()));
			parametersPane.add(txtStandardDeviation, 1, 4);
			txtStandardDeviation.setOnAction(e-> {
				try {
					double standardDeviation = Double.valueOf(txtStandardDeviation.getText());
					if(standardDeviation < 0.0  || standardDeviation > 1.0) {
						jfxFractalExplorer.showErrorMessage("Brightness should be in the range 0.0 to 1.0");
						return;
					}
					preference.setStandardDeviation(standardDeviation);
				}catch(Exception ex) {
					jfxFractalExplorer.showExceptionMessage(ex);
				}
			});
		}
		
		{
			chkGrayScale = new CheckBox("Gray Scale");
			parametersPane.add(chkGrayScale, 1, 5);
			chkGrayScale.setSelected(preference.isGrayScale());
			chkGrayScale.setOnAction(e -> {
				preference.setGrayScale(chkGrayScale.isSelected());
			});
		}
	}

	@Override
	public void disableControls() {
		spinnerSizePercent.setDisable(true);
		txtBrightness.setDisable(true);
		txtMinSize.setDisable(true);
		txtSaturation.setDisable(true);
		txtStandardDeviation.setDisable(true);
		chkGrayScale.setDisable(true);
	}

	@Override
	public void enableControls() {
		spinnerSizePercent.setDisable(false);
		txtBrightness.setDisable(false);
		txtMinSize.setDisable(false);
		txtSaturation.setDisable(false);
		txtStandardDeviation.setDisable(false);
		chkGrayScale.setDisable(false);
	}

}
